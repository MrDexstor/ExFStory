package com.vuzz.forgestory.api.plotter.js;

import java.util.HashMap;
import java.util.function.Consumer;

import com.vuzz.forgestory.annotations.Documentate;
import com.vuzz.forgestory.api.plotter.js.ApiJS.NpcBuilder;
import com.vuzz.forgestory.api.plotter.story.Action;
import com.vuzz.forgestory.api.plotter.story.ActionEvent;
import com.vuzz.forgestory.api.plotter.story.data.ActionPacketData;
import com.vuzz.forgestory.api.plotter.story.instances.SceneInstance;
import com.vuzz.forgestory.common.entity.Entities;
import com.vuzz.forgestory.common.entity.NPCEntity;
import com.vuzz.forgestory.common.networking.FadeScreenPacket;
import com.vuzz.forgestory.common.networking.Networking;

import javafx.scene.paint.Color;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

public class SceneJS implements JSResource {

    private final SceneInstance sceneInstance;

    private final HashMap<String,NpcJS> localNpcs = new HashMap<>();

    public SceneJS(SceneInstance scInstance) {
        sceneInstance = scInstance;
    }

    @Documentate(desc = "Ends the scene")
    public void endScene() { sceneInstance.getScene().story.endScene(sceneInstance.getPlayer()); }

    @Documentate(desc = "Adds action to the scene.")
    public SceneJS addAction(Consumer<ActionPacketData> cb, ActionEvent ev) {
        Action act = new Action(cb,ev);
        sceneInstance.actsJs.add(act);
        return this;
    }

    @Documentate(desc = "Creates a smooth appearing colored rectangle that lasts for n ticks.")
    public void showFadeScreen(int time, String colorString) {
        int color = (int) Long.parseLong(colorString,16);
        FadeScreenPacket packet = new FadeScreenPacket(sceneInstance.getPlayer().getUUID(),time,color);
        Networking.CHANNEL.send(PacketDistributor.ALL.noArg(),packet);
    }

    @Documentate(desc = "Creates a smooth appearing black rectangle that lasts for n ticks.")
    public void showFadeScreen(int time) { showFadeScreen(time,"FF000000"); }

    @Documentate(desc = "Creates an npc.")
    public void createNpc(World world, NpcBuilder npc, double[] pos) {
        EntityType<NPCEntity> npcReg = Entities.NPC.get();
        NPCEntity npcEntity = (NPCEntity) npcReg.spawn((ServerWorld) world, ItemStack.EMPTY, null, new BlockPos(pos[0],pos[1],pos[2]), 
            SpawnReason.EVENT, false, false);
        npcEntity.setTexturePath(npc.texturePath);
        npcEntity.setModelPath(npc.modelPath);
        npcEntity.setAnimationPath(npc.animationPath);
        npcEntity.focusedEntity = sceneInstance.getPlayer();
        npcEntity.goToPos = pos;
        NpcJS npcJS = new NpcJS(npcEntity);
        localNpcs.put(npc.id, npcJS);
    }

    @Documentate(desc = "Gets npc by its id.")
    public NpcJS getNpc(String id) {
        return localNpcs.get(id);
    }


    @Override public Object getNative() { return sceneInstance; }
    @Override public String getResourceId() { return "scene"; }
    @Override public boolean isClient() { return true; }

}