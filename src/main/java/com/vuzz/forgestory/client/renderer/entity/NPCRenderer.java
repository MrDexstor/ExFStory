package com.vuzz.forgestory.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.vuzz.forgestory.client.model.entity.NPCModel;
import com.vuzz.forgestory.common.entity.NPCEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NPCRenderer extends GeoEntityRenderer<NPCEntity> {

    public NPCRenderer(EntityRendererManager renderManager) {
        super(renderManager,new NPCModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(NPCEntity instance) {
        String path = instance.getPersistentData().getString("texturePath");
        return parsePath(path == "" ? "forgestory:textures/entity/npc" : path.toLowerCase());
    }

    public ResourceLocation parsePath(String path) {
        String id = path;
        String modId = "forgestory";
        String name = "textures/entity/npc";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        return new ResourceLocation(modId,name+(name.endsWith(".png") ? "" : ".png"));
    }

    @Override
    public void render(NPCEntity entity, float entityYaw, float partialTicks, MatrixStack stack,
            IRenderTypeBuffer bufferIn, int packedLightIn) {
        try {
            stack.pushPose();
            super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
            stack.popPose();   
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
