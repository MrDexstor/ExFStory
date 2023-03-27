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
        ResourceLocation modelPath = parsePath(path == "" ? "forgestory:geo/npc.geo" : path);
        return modelPath;
    }

    @Override
    public ResourceLocation getTextureLocation(NPCEntity object) {
        return new ResourceLocation("forgestory","textures/entity/npc.png");
    }

    @Override
	public void setCustomAnimations(NPCEntity animatable, int instanceId, AnimationEvent animationEvent) {
		super.setCustomAnimations(animatable, instanceId, animationEvent);
		IBone head = this.getAnimationProcessor().getBone("Head");
        if(animationEvent == null) return;
        if(animationEvent.getController() == null) return;
        Animation anim = animationEvent.getController().getCurrentAnimation();
		EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        
        if(anim == null) return;
        if(head == null) return;
		head.setRotationY(head.getInitialSnapshot().rotationValueY + extraData.netHeadYaw * ((float) Math.PI / 180F));
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
    
}
