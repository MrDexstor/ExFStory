package com.vuzz.forgestory.api.plotter.story.instances;

import java.util.ArrayList;

import com.vuzz.forgestory.api.plotter.story.Action;
import com.vuzz.forgestory.api.plotter.story.ActionEvent;
import com.vuzz.forgestory.api.plotter.story.PlotterEnvironment;
import com.vuzz.forgestory.api.plotter.story.Scene;
import com.vuzz.forgestory.api.plotter.story.Script;
import com.vuzz.forgestory.api.plotter.story.ActionEvent.MessageSentActionEvent;
import com.vuzz.forgestory.api.plotter.story.data.ActionPacketData;

import net.minecraft.entity.player.ServerPlayerEntity;

public class SceneInstance {
    
    private final ServerPlayerEntity player;
    private final Scene sceneReg;

    private final PlotterEnvironment env;

    public final ArrayList<Action> actsJs = new ArrayList<Action>();

    public int curActIndex = 0;

    public SceneInstance(Scene scene, ServerPlayerEntity player) {
        this.player = player;
        this.sceneReg = scene;
        this.env = new PlotterEnvironment(player,this);
    }

    public void tick() {

	}

    public void endScene() {
        
    }

    public void startScene() {
        ActionPacketData data = new ActionPacketData();
        data.playKeyPressed = true;

        if(sceneReg.scriptId != "") {
            Script scr = sceneReg.story.scripts.get(sceneReg.scriptId);
            if(scr != null)
                scr.runWithEnv(env);
        }

        playAction(data);
    }

    public void playAction(ActionPacketData data) {
        ArrayList<Action> usingActs = sceneReg.scriptId != "" ? actsJs : sceneReg.actions;
        if(usingActs.size() <= curActIndex) return;
        data.scene = this;
        Action action = usingActs.get(curActIndex);
        ActionEvent event = action.getEvent();
        boolean canStart = false;
        if(event.type == 1) {
            MessageSentActionEvent mEvent = (MessageSentActionEvent) event;
            for(String keyWord: mEvent.keyWords)
                if(data.messageSent.contains(keyWord.toLowerCase())) canStart = true;
            if(data.messageSent == "") canStart = false;
        }
        else if(event.type == 0) canStart = data.playKeyPressed;
        if(canStart) {
            action.getActionFunc().accept(data);
            curActIndex++;
        }
    }

    public void applyActPacket(ActionPacketData data) { playAction(data); }

    public PlotterEnvironment getEnvironment() { return env; }
    public Scene getScene() { return sceneReg; }
    public ServerPlayerEntity getPlayer() { return player; }

}
