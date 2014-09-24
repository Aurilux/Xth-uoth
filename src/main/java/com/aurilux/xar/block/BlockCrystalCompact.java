package com.aurilux.xar.block;

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockCrystalCompact extends Block {
	public BlockCrystalCompact() {
		super(Material.iron);
        XARBlocks.setBlock(this, "blockCrystal", "Crystal Block");
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(XARUtils.getTexturePath("blockCrystal"));
    }
}