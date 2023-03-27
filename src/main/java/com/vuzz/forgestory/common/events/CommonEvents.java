package com.vuzz.forgestory.common.events;

import com.vuzz.forgestory.ForgeStory;
import com.vuzz.forgestory.api.plotter.story.Root;
import com.vuzz.forgestory.api.plotter.story.data.ActionPacketData;
import com.vuzz.forgestory.client.events.ClientBusEvents;
import com.vuzz.forgestory.common.config.FSCommonConfig;
import com.vuzz.forgestory.common.networking.ActionPacket;
import com.vuzz.forgestory.common.networking.Networking;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ForgeStory.MOD_ID)
public class CommonEvents {
    

    @SubscribeEvent
    public static void clientTick(ClientTickEvent event) {
        if(event.phase != Phase.START) return;
        boolean actBtnDown = ClientBusEvents.keyStory.consumeClick();
        if(actBtnDown) {
            ActionPacketData pack = new ActionPacketData();
            pack.playKeyPressed = actBtnDown;
            sendActionPacket(pack);
        }
    }

    @SubscribeEvent
    public static void serverTick(ServerTickEvent event) { Root.tick(); }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void clientChat(ClientChatEvent event) {
        ActionPacketData pack = new ActionPacketData();
        pack.messageSent = event.getMessage().toLowerCase();
        event.setResult(Result.ALLOW);
        sendActionPacket(pack);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void playerJoined(PlayerLoggedInEvent event) {}

    @SubscribeEvent()
    public static void serverStarted(FMLServerStartedEvent event) {
        Root.reloadStories();
    }

    public static void sendActionPacket(ActionPacketData pack) {
        Minecraft mc = Minecraft.getInstance();

        if(FSCommonConfig.DEBUG_MODE.get())
            mc.player.sendMessage(
                new StringTextComponent(
                    "ActionPacket sent: "+pack.messageSent+" | "+pack.playKeyPressed), 
            Util.NIL_UUID);
        
        Networking.CHANNEL.send(PacketDistributor.SERVER.noArg(), 
            new ActionPacket(pack));
    }

}
