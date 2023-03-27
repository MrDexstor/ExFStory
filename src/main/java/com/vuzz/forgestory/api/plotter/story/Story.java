package com.vuzz.forgestory.api.plotter.story;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.vuzz.forgestory.annotations.Documentate;
import com.vuzz.forgestory.api.plotter.js.JSResource;
import com.vuzz.forgestory.api.plotter.story.data.PlayerData;
import com.vuzz.forgestory.api.plotter.story.data.SceneJSON;
import com.vuzz.forgestory.api.plotter.story.instances.SceneInstance;
import com.vuzz.forgestory.api.plotter.util.FileManager;
import com.vuzz.forgestory.api.plotter.util.Filters;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class Story {

    public String storyId = "";
    public File storyFolder;

    public HashMap<String,Scene> scenes = new HashMap<String,Scene>();
    public HashMap<String,Script> scripts = new HashMap<String,Script>();
    public ArrayList<File> libraries = new ArrayList<File>();

    public HashMap<UUID,SceneInstance> activeScenes = new HashMap<UUID,SceneInstance>();

    public HashMap<UUID,PlayerData> playersData = new HashMap<UUID,PlayerData>();

    public Story(String id, File folder) {
        storyId = id;
        storyFolder = folder;
        readFolders();
    }

    public SceneInstance getActiveSceneForPlayer(UUID playerUuid) {
        return activeScenes.get(playerUuid);
    }

    public void tick() {
        MinecraftServer server = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer());
        List<ServerPlayerEntity> players = server.getPlayerList().getPlayers();
        players.forEach((p) -> {
            UUID playerUuid = p.getUUID();
            if(playersData.get(playerUuid) == null) playersData.put(playerUuid,readPlayerData(p));
            SceneInstance activeScene = getActiveSceneForPlayer(playerUuid);
            PlayerData data = playersData.get(playerUuid);
            if(activeScene == null) {
                if(data.queueTimer > 0) data.queueTimer-=1;
                else {
                    Scene scene = scenes.get(data.sceneQueued);
                    if(scene != null)
                        startScene(scene, p);
                }
                playersData.put(playerUuid,data);
                if(data.queueTimer % 10 == 0 && data.queueTimer >= 10) writePlayerData(data, p); 
            } else {
                activeScene.tick();
            }
        });
    }

    @Documentate(desc = "Sets next queued scene for player.")
    public void queueScene(JSResource entity, String scene, double secs) { queueScene( (PlayerEntity) entity.getNative(), scene, secs); }

    @Documentate(desc = "Sets next queued scene for player. NOTE: Timer is ticking only when active scene is ended!")
    public void queueScene(PlayerEntity entity, String scene, double secs) { queueScene(entity, scene, (int) (secs*20));}
    public void queueScene(PlayerEntity player, String scene, int ticks) {
        PlayerData data = playersData.get(player.getUUID());
        data.queueTimer = ticks;
        data.sceneQueued = scene;
        playersData.put(player.getUUID(),data);
        writePlayerData(data, player);
    }


    public void startScene(Scene scene, ServerPlayerEntity playerEntity) {
        endScene(playerEntity);
        SceneInstance instance = new SceneInstance(scene, playerEntity);
        instance.startScene();
        activeScenes.put(playerEntity.getUUID(), instance);
    }

    public void endScene(ServerPlayerEntity playerEntity) { 
        SceneInstance instance = activeScenes.get(playerEntity.getUUID());
        if(instance != null) instance.endScene();
        activeScenes.remove(playerEntity.getUUID()); 
    }

    public void readFolders() {
        readScenes(); readScripts(); readLibraries();
    }

    public void readScenes() {
        scenes.clear(); FileManager.listInDirAndDo(storyFolder, "scene", Filters.onlyJson, this::addScene); }

    public void readScripts() {
        scripts.clear(); FileManager.listInDirAndDo(storyFolder, "js", Filters.onlyJs, this::addScript); }

    public void readLibraries() {
        libraries.clear(); FileManager.listInDirAndDo(storyFolder, "lib", Filters.onlyJs, (f) -> {libraries.add(f);});}

    public void addScene(File f) {
        try {
            SceneJSON json = FileManager.jsonToJava(f, SceneJSON.class);
            Scene scene = new Scene(this,json);
            scenes.put(json.id,scene);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addScript(File f) {
        Script scr = new Script(f);
        String name = f.getName();
        scripts.put(
            name.substring(0,name.length()-3),
            scr
        );
    }



    public PlayerData readPlayerData(PlayerEntity player) {
        UUID playerUuid = player.getUUID();
        File dataFolder = new File(storyFolder,"data");
        File playerDataFolder = new File(dataFolder,"player");
        File playerDataFile = new File(playerDataFolder,playerUuid.toString()+".json");
		try {
			PlayerData playerData = FileManager.jsonToJava(playerDataFile, PlayerData.class);
            return playerData;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new PlayerData();
    }

    public void writePlayerData(PlayerData data, PlayerEntity player) {
        UUID playerUuid = player.getUUID();
        File dataFolder = new File(storyFolder,"data");
        File playerDataFolder = new File(dataFolder,"player");
        File playerDataFile = new File(playerDataFolder,playerUuid.toString()+".json");
        try {
			FileManager.javaToJson(playerDataFile, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
