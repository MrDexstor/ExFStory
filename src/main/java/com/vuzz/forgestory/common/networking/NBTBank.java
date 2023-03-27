package com.vuzz.forgestory.common.networking;

import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.PacketDistributor;

public class NBTBank {

    public static enum Type {
        STRING,
        DOUBLE,
        INT,
        UUID
    }

    private final CompoundNBT clientData = new CompoundNBT();

    public void postOnClient(String nbt, Object val, Type type) {
        switch(type) {
            case STRING:
                clientData.putString(nbt, (String) val);
                break;
            case DOUBLE:
                clientData.putDouble(nbt, (double) val);
                break;
            case INT:
                clientData.putInt(nbt, (int) val);
                break;
            case UUID:
                clientData.putUUID(nbt, (UUID) val);
                break;
        }
    }

    public void flush(Entity entity) {
        Set<String> keys = clientData.getAllKeys();
        if(keys.size() == 0) return;
        String[] clientKeys = (String[]) keys.toArray();
        ClientPacket clientPacket = new ClientPacket(clientData,entity.getId());
        Networking.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity),clientPacket);
        for(String cKey : clientKeys) clientData.remove(cKey);
    }

}
