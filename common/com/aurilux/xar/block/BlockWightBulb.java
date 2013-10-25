package com.aurilux.xar.block;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

import com.aurilux.xar.lib.Blocks;
import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWightBulb extends BlockFlower {

    protected BlockWightBulb(int id, Material mat) {
		// TODO work on this
		super(id, mat);
        this.setTickRandomly(true);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
        this.setUnlocalizedName("wightBulb");
    }

	public BlockWightBulb(int id) {
		this(id, Material.plants);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		this.blockIcon = reg.registerIcon(XAR_Ref.MOD_ID + ":wightbulb");
    }
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlockId(x, y - 1, z) == Blocks.stoneStrange.blockID;
    }
}