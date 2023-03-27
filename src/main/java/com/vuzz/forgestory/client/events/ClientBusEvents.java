package com.vuzz.forgestory.client.events;

import com.vuzz.forgestory.ForgeStory;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ForgeStory.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientBusEvents {
    
    public static KeyBinding keyStory;

    @SubscribeEvent
    public static void register(final FMLClientSetupEvent event) {
        keyStory = new KeyBinding("key.forgestory.play_act", InputMappings.Type.KEYSYM, 72, "keylist.forgestory");
        ClientRegistry.registerKeyBinding(keyStory);
    }



}
