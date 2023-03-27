package com.vuzz.forgestory.api.plotter.js;

import com.vuzz.forgestory.annotations.Documentate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class NpcJS implements JSResource {

    private LivingEntity npc;

    public NpcJS(LivingEntity npc) {
        this.npc = npc;
    }

    // Animation Handlers
    @Documentate(desc = "Play anim Only Once")
    public void animPlayOnce(String id) { playAnim(id,0); }
    @Documentate(desc = "Play anim in a Loop")
    public void animLoop(String id) { playAnim(id,1); }
    @Documentate(desc = "Play anim once and Hold on Last Frame")
    public void animHold(String id) { playAnim(id,2); }

    @Documentate(desc = "Stops playing anim.")
    public void stopPlayOnce() { stopAnim(0); }
    @Documentate(desc = "Stops playing anim.")
    public void stopLoop() { stopAnim(1); }
    @Documentate(desc = "Stops playing anim.")
    public void stopHold() { stopAnim(2); }

    //TODO:
    public void playAnim(String id, int animState) {
        
    }
    //TODO:
    public void stopAnim(int animState) {

    }
    //TODO:
    @Documentate(desc = "Changes default walking animation.")
    public void setWalkAnimation(String id) {}
    @Documentate(desc = "Changes default idle animation.")
    public void setIdleAnimation(String id) {}

    // Head controlls
    @Documentate(desc = "Rotates NPC's head by Y/yaw.")
    public void setHeadRotation(float yaw) { npc.yHeadRot = yaw; setEntityFocused(); }

    @Documentate(desc = "Focuses npc on another entity.")
    public void setEntityFocused(Entity entity) {

    }
    @Documentate(desc = "Focuses npc on another entity.")
    public void setEntityFocused(JSResource res) {
        Object nativeObj = res.getNative();
        if(nativeObj instanceof Entity) {
            setEntityFocused((Entity) nativeObj);
        };
    }

    @Documentate(desc = "Makes npc don't focus on anything.")
    public void setEntityFocused() {

    }

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
    public void setPosition(double[] pos)  { npc.setPos(pos[0],pos[1],pos[2]); };

    @Documentate(desc = "Sets NPC's position.")
    public void setPosition(double x, double y, double z)  { setPosition( new double[] {x,y,z} ); };

    @Documentate(desc = "Sets NPC's X position.")
    public void setX(double x) { setPosition(x,getY(),getZ()); };

    @Documentate(desc = "Sets NPC's Y position.")
    public void setY(double y) { setPosition(getX(),y,getZ()); };

    @Documentate(desc = "Sets NPC's Z position.")
    public void setZ(double z) { setPosition(getX(),getY(),z); };


    //TODO
    @Documentate(desc = "Moves NPC to position with preset speed.")
    public void moveToPosition(double[] pos, double speed) {}

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
