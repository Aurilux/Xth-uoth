package com.aurilux.xar.block;

import net.minecraft.block.BlockFlower;
import net.minecraft.world.World;

import com.aurilux.xar.lib.XARBlocks;

public class BlockWightBulb extends BlockFlower {
	//TODO add particles that float up from the 'bulb'

    protected BlockWightBulb(int id) {
		super(id);
        this.setTickRandomly(true);
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, .9F, 0.9F);
        this.setBlockName("wightBulb");
    }
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z) == XARBlocks.stoneStrange;
    }
}