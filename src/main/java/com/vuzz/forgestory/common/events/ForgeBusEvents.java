package com.vuzz.forgestory.common.events;

import java.util.HashMap;
import java.util.UUID;

import com.vuzz.forgestory.ForgeStory;
import com.vuzz.forgestory.client.overlay.FadeScreen;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ForgeStory.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
@SuppressWarnings("unused")
public class ForgeBusEvents {
    
    public static int testTicks = 0;

    public static HashMap<UUID,Integer> fadeScreenTimers = new HashMap<UUID,Integer>();
    public static HashMap<UUID,Integer> fadeScreenColors = new HashMap<UUID,Integer>();

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        UUID playerUuid = mc.player.getUUID();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            FadeScreen render = new FadeScreen(mc);
            int hexColor = fadeScreenColors.containsKey(playerUuid) ? fadeScreenColors.get(playerUuid) : 0xFF000000;
            int playerTicks = fadeScreenTimers.containsKey(playerUuid) ? fadeScreenTimers.get(playerUuid) : -1;
        	if(playerTicks >= 0) {
                render.renderFadeScreen(event.getMatrixStack(),playerTicks, hexColor);
                playerTicks--;
                fadeScreenTimers.put(playerUuid, playerTicks);
            }
        }
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            FadeScreen render = new FadeScreen(mc);
        }
    }
}
