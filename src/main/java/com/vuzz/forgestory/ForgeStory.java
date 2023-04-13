package com.vuzz.forgestory;

import com.vuzz.forgestory.client.renderer.entity.NPCRenderer;
import com.vuzz.forgestory.common.config.FSCommonConfig;
import com.vuzz.forgestory.common.entity.Entities;
import com.vuzz.forgestory.common.items.ItemsFS;
import com.vuzz.forgestory.common.networking.Networking;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(ForgeStory.MOD_ID)
public class ForgeStory {
    public static final String MOD_ID = "forgestory";

    public static final ItemGroup MOD_TAB = new ItemGroup("forgestory") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemsFS.STORY_REFRESHER.get());
        }
    };

    @SuppressWarnings("unused")
    public ForgeStory() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(Type.COMMON,FSCommonConfig.SPEC,"forgestory.toml");
        
        Networking.register();
        Entities.register(eventBus);
        ItemsFS.register(eventBus);

        GeckoLib.initialize();

        eventBus.addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Entities.NPC.get(), NPCRenderer::new);
        event.enqueueWork(() -> {

        });
    }

}