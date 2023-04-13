package com.vuzz.forgestory.api.plotter.story;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import com.vuzz.forgestory.FSC;
import com.vuzz.forgestory.api.plotter.story.data.FSData;
import com.vuzz.forgestory.api.plotter.story.data.PackedLibData;
import com.vuzz.forgestory.api.plotter.story.data.PackedScriptData;
import com.vuzz.forgestory.api.plotter.story.data.PackedStoryData;
import com.vuzz.forgestory.api.plotter.story.data.SceneJSON;
import com.vuzz.forgestory.api.plotter.util.FileManager;
import com.vuzz.forgestory.api.plotter.util.Filters;

import net.minecraft.world.storage.FolderName;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class Root {

    public static HashMap<String,Story> storiesList = new HashMap<String,Story>();

    public static Story tickingStory;
    public static int ticks = 0;

    @SuppressWarnings("all")
    public static void reloadStories() {

        storiesList.clear();

        Path worldPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File worldFolder = worldPath.toFile();
        File storiesFolder = new File(worldFolder,"fs_stories");
             storiesFolder.mkdir();

        File buildFolder = new File(worldFolder,"fs_builds");
             buildFolder.mkdir();

        FSC.sout(FSC.udcRombus+"Reloading stories...");

        File[] storyFolders = storiesFolder.listFiles(Filters.onlyDir);
        for(File story: storyFolders)
            addStory(story);

        File[] buildsFolders = buildFolder.listFiles(Filters.onlyStory);
        for(File build: buildsFolders)
            unbuildStory(build);
        
        tickingStory = getActiveStory();
    }

    public static void buildStory(Story story) {
        Path worldPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File worldFolder = worldPath.toFile();
        File buildFolder = new File(worldFolder,"fs_builds");
             buildFolder.mkdir();

        ArrayList<SceneJSON> scenes = new ArrayList<SceneJSON>();
        ArrayList<PackedScriptData> scripts = new ArrayList<PackedScriptData>();
        ArrayList<PackedLibData> libs = new ArrayList<PackedLibData>();

        story.scenes.forEach((key, sc) -> scenes.add(sc.json));
        story.scripts.forEach((key,sc) -> scripts.add(sc.toPacked()));
        story.libraries.forEach((sc) -> libs.add(story.packLib(sc)));

        PackedStoryData data = new PackedStoryData();
            data.id = story.storyId;
            data.scenes = scenes;
            data.scripts = scripts;
            data.libs = libs;
        FileManager.javaToJson(new File(buildFolder,story.storyId+".build"), data);
    }

    public static void unbuildStory(File f) {try {
        Path worldPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File worldFolder = worldPath.toFile();
        File storiesFolder = new File(worldFolder,"fs_stories");
             storiesFolder.mkdir();

        PackedStoryData data = FileManager.jsonToJava(f, PackedStoryData.class);
        String storyId = f.getName();
        File storyFolder = new File(storiesFolder,storyId);
            storyFolder.mkdir();
        File scenesFolder = new File(storyFolder,"scene");
            scenesFolder.mkdir();
        File scriptsFolder = new File(storyFolder,"js");
            scriptsFolder.mkdir();
        File libsFolder = new File(storyFolder,"lib");
            libsFolder.mkdir();

        data.scenes.forEach((json) -> FileManager.javaToJson(new File(scenesFolder,json.id+".json"), json));
        data.scripts.forEach((scr) -> {
            try {
                File fscr = new File(scriptsFolder,scr.id);
                fscr.createNewFile();
                OutputStreamWriter writer = FileManager.createOutput(fscr);
                writer.write(scr.content);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        data.libs.forEach((lib) -> {
            try {
                File fscr = new File(libsFolder,lib.id);
                fscr.createNewFile();
                OutputStreamWriter writer = FileManager.createOutput(fscr);
                writer.write(lib.content);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Story story = new Story(storyId,storyFolder);
        story.isBuilded = true;
        addStory(story);
    } catch (InstantiationException | IllegalAccessException e) {e.printStackTrace();}}

    public static void tick() {
        if(ticks % 100 == 0) tickingStory = getActiveStory();
        if(tickingStory != null) tickingStory.tick();
        ticks++;
    }

    public static void setActiveStory(String id) {
        FSData root = getRootData();
        root.selectedStory = id;
        writeRootData(root);
    }

    public static Story getActiveStory() {
        FSData root = getRootData();
        return storiesList.get(root.selectedStory);
    }

    public static FSData getRootData() {
        Path worldPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File worldFolder = worldPath.toFile();
        File fsDataFile = new File(worldFolder, "forgestory.json");
        try {
			return FileManager.jsonToJava(fsDataFile, FSData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new FSData();
    }

    public static void writeRootData(FSData data) {
        Path worldPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File worldFolder = worldPath.toFile();
        File fsDataFile = new File(worldFolder, "forgestory.json");
        FileManager.javaToJson(fsDataFile, data);
    } 

    public static boolean addStory(File storyFile) {
        if(storyFile.getName().endsWith(".build")) return false;
        Story story = new Story(storyFile.getName(), storyFile);
            createDir(storyFile,"act");
            createDir(storyFile,"lib");
            createDir(storyFile,"js");
            File dataF = createDir(storyFile,"data");
                createDir(dataF,"player");
                createDir(dataF,"script");
            createDir(storyFile,"scene");
        storiesList.put(storyFile.getName(),story);
        buildStory(story);
        FSC.sout(FSC.udcRFaceArrow+FSC.udcRFaceArrow+"Loaded story: "+story.storyId.toUpperCase());
        return true;
    }

    public static boolean addStory(Story story) {
        File storyFile = story.storyFolder;
        createDir(storyFile,"act");
        createDir(storyFile,"lib");
        createDir(storyFile,"js");
        File dataF = createDir(storyFile,"data");
            createDir(dataF,"player");
            createDir(dataF,"script");
        createDir(storyFile,"scene");
        storiesList.put(storyFile.getName(),story);
        FSC.sout(FSC.udcRFaceArrow+FSC.udcRFaceArrow+"Loaded story: "+story.storyId.toUpperCase());
        return true;
    }

    public static File createDir(File file, String dir) {
        File d = new File(file,dir);
        d.mkdir();
        return d;
    }



}
