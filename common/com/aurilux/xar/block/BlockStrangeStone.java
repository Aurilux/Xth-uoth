package com.aurilux.xar.block;

import com.aurilux.xar.helpers.BlockXAR;
import com.aurilux.xar.item.ItemWightBulbPod;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockStrangeStone extends BlockXAR {

	public BlockStrangeStone(int id) {
		super(id, Material.rock, "stoneStrange");
	}
	
	@Override
	public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection dir, IPlantable plant) {
		return plant instanceof ItemWightBulbPod;
	}
}