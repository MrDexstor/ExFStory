package com.vuzz.forgestory.api.plotter.story;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

import com.vuzz.forgestory.api.plotter.story.data.FSData;
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

        File[] storyFolders = storiesFolder.listFiles(Filters.onlyDir);
        for(File story: storyFolders)
            addStory(story);
        tickingStory = getActiveStory();
    }

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
        Story story = new Story(storyFile.getName(), storyFile);
            createDir(storyFile,"act");
            createDir(storyFile,"lib");
            createDir(storyFile,"js");
            File dataF = createDir(storyFile,"data");
                createDir(dataF,"player");
                createDir(dataF,"script");
            createDir(storyFile,"scene");
        storiesList.put(storyFile.getName(),story);
        return true;
    }

    public static File createDir(File file, String dir) {
        File d = new File(file,dir);
        d.mkdir();
        return d;
    }



}
