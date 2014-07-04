package com.aurilux.xar.block;

import com.aurilux.xar.item.ItemWightBulbPod;

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAberrack extends Block {

	public BlockAberrack() {
        super(Material.rock);
        XARBlocks.setBlock(this, "blockAberrack", "Aberrack");
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(XARUtils.getTexturePath("aberrack"));
    }
}