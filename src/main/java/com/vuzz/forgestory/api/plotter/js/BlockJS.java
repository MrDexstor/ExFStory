package com.vuzz.forgestory.api.plotter.js;

import com.vuzz.forgestory.annotations.Documentate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockJS implements JSResource {

    public BlockState state;
    public Block block;
    public WorldJS world;
    public World nativeWorld;
    public BlockPos blockPos;

    public BlockJS(BlockState state, WorldJS world, BlockPos pos) {
        this.state = state;
        this.block = state.getBlock();
        this.world = world;
        this.nativeWorld = (World) world.getNative();
        this.blockPos = pos;
    }

    @Documentate(desc = "Gets Block id (minecraft:stone)")
    public String getId() { return block.getRegistryName().toString(); }

    @Documentate(desc = "Returns block's BlockPos")
    public BlockPos getPosition() {return blockPos;}
    
    @Documentate(desc = "Replaces block with another and returns the new one.")
    public BlockJS replaceWith(String blockId) {
        world.setBlock(blockId,blockPos);
        return world.getBlock(blockPos);
    }

    @Documentate(desc = "Destroys the block")
    public void destroy() { world.setBlock("minecraft:air",blockPos); }


    @Override
    public Object getNative() {return (Object) state;}

    @Override
    public String getResourceId() {return "block";}

    @Override
    public boolean isClient() {return false;}
    
}
