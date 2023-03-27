package com.vuzz.forgestory.common.networking;

import java.util.function.Supplier;

import com.vuzz.forgestory.api.plotter.story.Root;
import com.vuzz.forgestory.api.plotter.story.Story;
import com.vuzz.forgestory.api.plotter.story.data.ActionPacketData;
import com.vuzz.forgestory.api.plotter.story.instances.SceneInstance;
import com.vuzz.forgestory.common.config.FSCommonConfig;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class ActionPacket {
    public boolean isKeyPressed;
    public String messageChatted;

    public ActionPacket(ActionPacketData data) {
        isKeyPressed = data.playKeyPressed;
        messageChatted = data.messageSent;
    }

    public ActionPacket(boolean isKeyPressed, String messageChatted) {
        this.isKeyPressed = isKeyPressed;
        this.messageChatted = messageChatted;
    }

    public ActionPacket(PacketBuffer buffer) {
        isKeyPressed = buffer.readBoolean();
        messageChatted = buffer.readUtf();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(isKeyPressed);
        buffer.writeUtf(messageChatted);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if(context.get().getSender() == null) return;
            PlayerEntity sender = context.get().getSender();
            if(FSCommonConfig.DEBUG_MODE.get())
                sender.sendMessage(
                    new StringTextComponent(
                        "ActionPacket received: "+messageChatted+" | "+isKeyPressed), 
                    Util.NIL_UUID);
            Story story = Root.getActiveStory();
                if(story == null) return;
            SceneInstance playerScene = story.getActiveSceneForPlayer(sender.getUUID());
                if(playerScene == null) return;
            ActionPacketData packetData = new ActionPacketData();
                packetData.messageSent = messageChatted;
                packetData.playKeyPressed = isKeyPressed;
                packetData.scene = playerScene;
            playerScene.applyActPacket(packetData);
        });
        context.get().setPacketHandled(true);
    }
}
