package com.vuzz.forgestory.client.model.entity;

import com.vuzz.forgestory.common.entity.NPCEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class NPCModel extends AnimatedTickingGeoModel<NPCEntity> {

    @Override
    public ResourceLocation getAnimationFileLocation(NPCEntity animatable) {
        String path = animatable.getPersistentData().getString("animPath");
        ResourceLocation animPath = parsePath(path == "" ? "forgestory:animations/npc.animation" : path);
        return animPath;
    }

    @Override
    public ResourceLocation getModelLocation(NPCEntity object) {
        String path = object.getPersistentData().getString("modelPath");
        ResourceLocation modelPath = parsePath(path == "" ? "forgestory:geo/steve.geo" : path);
        return modelPath;
    }

    @Override
    public ResourceLocation getTextureLocation(NPCEntity object) {
        return new ResourceLocation("forgestory","textures/entity/npc.png");
    }
    
    public ResourceLocation parsePath(String path) {
        String id = path;
        String modId = "forgestory";
        String name = "animations/npc.animation.json";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        return new ResourceLocation(modId,name+(name.endsWith(".json") ? "" : ".json"));
    }

    @Override
    public void setCustomAnimations(NPCEntity animatable, int instanceId, AnimationEvent animationEvent) {
        try {
            super.setCustomAnimations(animatable, instanceId, animationEvent);
            IBone head = this.getAnimationProcessor().getBone("Head");

            EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
            if (head != null) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
