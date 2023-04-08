package com.vuzz.forgestory.api.plotter.js;

import com.vuzz.forgestory.annotations.Documentate;
import com.vuzz.forgestory.common.entity.NPCEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import com.vuzz.forgestory.common.networking.NBTBank;

public class NpcJS implements JSResource {

    private NPCEntity npc;

    public NpcJS(NPCEntity npc) {
        this.npc = npc;
    }

    // Animation Handlers
    @Documentate(desc = "Play anim Only Once")
    public void animPlayOnce(String id) { playAnim(id,0); }
    @Documentate(desc = "Play anim in a Loop")
    public void animLoop(String id) { playAnim(id,1); }

    @Documentate(desc = "Stops playing anim.")
    public void stopPlayOnce() { stopAnim(0); }
    @Documentate(desc = "Stops playing anim.")
    public void stopLoop() { stopAnim(1); }

    public void playAnim(String id, int animState) {
        String[] animStrings = new String[] {"a_playonce","a_loop"};
        String animId = animStrings[animState];
        npc.nbtBank.postOnClient(animId,id,NBTBank.Type.STRING);
    }

    public void stopAnim(int animState) {
        String[] animStrings = new String[] {"a_playonce","a_loop"};
        String animId = animStrings[animState];
        npc.nbtBank.postOnClient(animId,"",NBTBank.Type.STRING);
    }

    @Documentate(desc = "Changes default walking animation.")
    public void setWalkAnimation(String id) { npc.setWalkAnim(id); }

    @Documentate(desc = "Changes default idle animation.")
    public void setIdleAnimation(String id) { npc.setIdleAnim(id); }

    // Head controlls
    @Documentate(desc = "Rotates NPC's head by Y/yaw.")
    public void setHeadRotation(float yaw) { npc.yHeadRot = yaw; setEntityFocused(); }

    @Documentate(desc = "Focuses npc on another entity.")
    public void setEntityFocused(Entity entity) { npc.focusedEntity = entity; }

    @Documentate(desc = "Focuses npc on another entity.")
    public void setEntityFocused(JSResource res) {
        Object nativeObj = res.getNative();
        if(nativeObj instanceof Entity) {
            setEntityFocused((Entity) nativeObj);
        };
    }

    @Documentate(desc = "Makes npc don't focus on anything.")
    public void setEntityFocused() { npc.focusedEntity = null; }

    // Positions
    @Documentate(desc = "Gets NPC's position.")
    public double[] getPosition() { return new double[] { getX(),getY(),getZ() };}

    @Documentate(desc = "Gets NPC's X position.")
    public double getX() { return npc.getX(); }

    @Documentate(desc = "Gets NPC's Y position.")
    public double getY() { return npc.getY(); }

    @Documentate(desc = "Gets NPC's Z position.")
    public double getZ() { return npc.getZ(); }

    //Teleports and Movement
    @Documentate(desc = "Sets NPC's position.")
    public void setPosition(double[] pos)  { npc.setPos(pos[0],pos[1],pos[2]); npc.goToPos = pos; };

    @Documentate(desc = "Sets NPC's position.")
    public void setPosition(double x, double y, double z)  { setPosition( new double[] {x,y,z} ); };

    @Documentate(desc = "Sets NPC's X position.")
    public void setX(double x) { setPosition(x,getY(),getZ()); };

    @Documentate(desc = "Sets NPC's Y position.")
    public void setY(double y) { setPosition(getX(),y,getZ()); };

    @Documentate(desc = "Sets NPC's Z position.")
    public void setZ(double z) { setPosition(getX(),getY(),z); };

    @Documentate(desc = "Moves NPC to position with preset speed.")
    public void moveToPosition(double[] pos, double speed) {npc.goToPos = pos; npc.speed = speed; }

    @Documentate(desc = "Moves NPC to position with default speed.")
    public void moveToPosition(double[] pos) { moveToPosition(pos,1D); }

    @Documentate(desc = "Moves NPC to position with preset speed.")
    public void moveToPosition(double x, double y, double z, double speed) { moveToPosition( new double[] {x,y,z} , speed); }

    @Documentate(desc = "Moves NPC to position with default speed.")
    public void moveToPosition(double x, double y, double z) { moveToPosition(x,y,z,1D); }

    //Despawning
    @Documentate(desc = "Despawns/Removes npc from world.")
    public void despawnSelf() { npc.remove(); }

    //JS
    @Override public Object getNative() { return npc; }
    @Override public String getResourceId() { return "npc"; }
    @Override public boolean isClient() { return npc.level.isClientSide; }
    
}
