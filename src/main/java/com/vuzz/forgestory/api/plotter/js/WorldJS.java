package com.vuzz.forgestory.api.plotter.js;

import com.vuzz.forgestory.annotations.Documentate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class WorldJS implements JSResource {

    private World world;

    public WorldJS(World world) {
        this.world = world;
    }

    public WorldJS(WorldJS world) {
        this.world = (World) world.getNative();
    }

    @Documentate(desc = "Places a block in the world")
    public void setBlock(BlockState block, BlockPos pos) { world.setBlock(pos, block, Constants.BlockFlags.DEFAULT); }

    @Documentate(desc = "Places a block in the world")
    public void setBlock(Block block, BlockPos pos) { setBlock(ApiJS.createBlockState(block),pos); }

    @Documentate(desc = "Places a block in the world")
    public void setBlock(String blockId, BlockPos pos) { setBlock(ApiJS.createBlockState(blockId),pos); }

    @Documentate(desc = "Gets a block in the world")
    public BlockJS getBlock(BlockPos pos) { return new BlockJS(world.getBlockState(pos),this,pos); }

    @Override public Object getNative() { return world; }
    @Override public String getResourceId() { return "world"; }
    @Override public boolean isClient() { return world.isClientSide; }

}