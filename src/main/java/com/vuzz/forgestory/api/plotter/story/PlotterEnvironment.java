package com.vuzz.forgestory.api.plotter.story;

import com.vuzz.forgestory.api.Environment;
import com.vuzz.forgestory.api.plotter.js.*;
import com.vuzz.forgestory.api.plotter.js.ApiJS.CameraMode;
import com.vuzz.forgestory.api.plotter.js.event.EventManager;
import com.vuzz.forgestory.api.plotter.story.instances.SceneInstance;

import net.minecraft.entity.player.ServerPlayerEntity;

public class PlotterEnvironment extends Environment {

    public String envId = "plotter";
    public String version = "2";
    public Environment env = this;

    public PlayerJS player;

    public Root root;
    public Story story;
    public SceneJS scene;
    public WorldJS world;

    public ActionEvent e = ActionEvent.DEF();
    public CameraMode cam = CameraMode.NIL();
    public EventManager evManager;

    public ApiJS api;

    public PlotterEnvironment(ServerPlayerEntity player, SceneInstance inst) {
        this.player = new PlayerJS(player);
        this.scene = new SceneJS(inst);
        this.story = inst.getScene().story;
        this.evManager = inst.eventManager;
        this.root = new Root();
        this.api = new ApiJS();
        this.world = new WorldJS(player.level);
    }

}
