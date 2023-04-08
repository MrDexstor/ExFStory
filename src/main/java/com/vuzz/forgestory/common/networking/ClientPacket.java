package com.vuzz.forgestory.common.networking;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientPacket {

    public CompoundNBT nbt;
    public int uuid;

    public ClientPacket(CompoundNBT nbt, int uuid) { this.nbt = nbt; this.uuid = uuid; }
    public ClientPacket(PacketBuffer buffer) { this.nbt = buffer.readNbt(); this.uuid = buffer.readInt(); }
    public void encode(PacketBuffer buffer) { buffer.writeNbt(nbt); buffer.writeInt(uuid); }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.level.getEntity(uuid);
            if(entity == null) return;
            String[] nbtKeys = nbt.getAllKeys().toArray(new String[0]);
            for(String key : nbtKeys) {
                entity.getPersistentData().put(key, nbt.get(key));
            }
        });
        context.get().setPacketHandled(true);
    }
    
}