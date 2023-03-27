package com.vuzz.forgestory.api.plotter.js;

import net.minecraft.world.World;

public class WorldJS implements JSResource {

    private World world;

    public WorldJS(World world) {
        this.world = world;
    }

    public WorldJS(WorldJS world) {
        this.world = (World) world.getNative();
    }

    @Override public Object getNative() { return world; }
    @Override public String getResourceId() { return "world"; }
    @Override public boolean isClient() { return world.isClientSide; }

}