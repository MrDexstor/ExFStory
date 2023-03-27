package com.vuzz.forgestory.common.events;

import com.vuzz.forgestory.ForgeStory;
import com.vuzz.forgestory.common.entity.Entities;
import com.vuzz.forgestory.common.entity.NPCEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ForgeStory.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(Entities.NPC.get(),NPCEntity.genAttributes().build());
    }
}
