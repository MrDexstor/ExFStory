package com.vuzz.forgestory.api.plotter.story.instances;

import java.util.ArrayList;

import com.vuzz.forgestory.api.plotter.js.ApiJS;
import com.vuzz.forgestory.api.plotter.js.JSResource;
import com.vuzz.forgestory.api.plotter.js.ApiJS.CameraMode;
import com.vuzz.forgestory.api.plotter.js.event.*;
import com.vuzz.forgestory.api.plotter.story.Action;
import com.vuzz.forgestory.api.plotter.story.ActionEvent;
import com.vuzz.forgestory.api.plotter.story.PlotterEnvironment;
import com.vuzz.forgestory.api.plotter.story.Scene;
import com.vuzz.forgestory.api.plotter.story.Script;
import com.vuzz.forgestory.api.plotter.story.ActionEvent.DelayActionEvent;
import com.vuzz.forgestory.api.plotter.story.ActionEvent.MessageSentActionEvent;
import com.vuzz.forgestory.api.plotter.story.ActionEvent.PositionedActionEvent;
import com.vuzz.forgestory.api.plotter.story.data.ActionPacketData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameType;

public class SceneInstance {
    
    private final ServerPlayerEntity player;
    private final Scene sceneReg;
    public final EventManager eventManager = new EventManager();

    private final PlotterEnvironment env;

    public final ArrayList<Action> actsJs = new ArrayList<Action>();

    public int curActIndex = 0;

    public boolean inCutscene = false;
    public CameraMode cutsceneCam = CameraMode.NIL();

    public SceneInstance(Scene scene, ServerPlayerEntity player) {
        this.player = player;
        this.sceneReg = scene;
        this.env = new PlotterEnvironment(player,this);
    }

    public int ticks;
    public void tick() {
        eventManager.runEvent("tick",new TickEvent(ticks));
        if(inCutscene && cutsceneCam.type != "undef") {
            player.setGameMode(GameType.SPECTATOR);
            if(cutsceneCam.type == "full" || cutsceneCam.type == "pos_only") {
                player.teleportToWithTicket(cutsceneCam.posX,cutsceneCam.posY,cutsceneCam.posZ);
            }
            if(cutsceneCam.type == "full" || cutsceneCam.type == "rot_only") {
                player.xRot = (float) cutsceneCam.rotX;
                player.yHeadRot = (float) 0;
                player.yBodyRot = (float) cutsceneCam.rotY;
            }
        }
        playAction(new ActionPacketData());
        ticks++;
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

    int ticksTimer = 0;

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
        else if(event.type == 2) {
            DelayActionEvent dEvent = (DelayActionEvent) event;
            if(ticksTimer >= dEvent.ticks) {
                canStart = true;
                ticksTimer = 0;
            }
            ticksTimer++;
        }
        else if(event.type == 3) {
            PositionedActionEvent pEvent = (PositionedActionEvent) event;
            Entity entity;
            if(pEvent.entity.get() instanceof JSResource)
                entity = (Entity) ((JSResource) pEvent.entity.get()).getNative();
            else entity = pEvent.entity.get();
            double[] initCords = new double[] {entity.getX(),entity.getY(),entity.getZ()};
            double[] targetCoords = pEvent.position.get();
            double distance = distance(initCords,targetCoords);
            if(distance <= pEvent.radius) canStart = true;
        }
        if(canStart) {
            try {
                action.getActionFunc().accept(data);
            } catch (Exception e) {
                e.printStackTrace();
                getPlayer().sendMessage(new StringTextComponent(e.getMessage()), Util.NIL_UUID);
            }
            curActIndex++;
        }
    }

    public static double distance(double[] start, double[] end) {
        double deltaX = end[0] - start[0];
        double deltaY = end[1] - start[1];
        double deltaZ = end[2] - start[2];
        return Math.sqrt(deltaX*deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    public void signalMessageEvent(String message) {
        MessageEvent event = new MessageEvent(message);
        eventManager.runEvent("message", event);
    }

    public void applyActPacket(ActionPacketData data) { 
        if(data.messageSent != "") signalMessageEvent(data.messageSent);
        playAction(data); 
    }

    public PlotterEnvironment getEnvironment() { return env; }
    public Scene getScene() { return sceneReg; }
    public ServerPlayerEntity getPlayer() { return player; }

}
