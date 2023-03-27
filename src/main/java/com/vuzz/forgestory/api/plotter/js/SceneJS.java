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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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

    public void createNpc(World world, NpcBuilder npc, double[] pos) {
        EntityType<NPCEntity> npcReg = Entities.NPC.get();
        NPCEntity npcEntity = (NPCEntity) npcReg.spawn((ServerWorld) world, ItemStack.EMPTY, null, new BlockPos(pos[0],pos[1],pos[2]), 
            SpawnReason.EVENT, false, false);
        npcEntity.setTexturePath(npc.texturePath);
        npcEntity.setModelPath(npc.modelPath);
        npcEntity.setAnimationPath(npc.animationPath);
    }


    @Override public Object getNative() { return sceneInstance; }
    @Override public String getResourceId() { return "scene"; }
    @Override public boolean isClient() { return true; }

}