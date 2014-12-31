package com.aurilux.xar.block;

import com.aurilux.xar.util.ResourceUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockPrysmalCompact extends Block {
	public BlockPrysmalCompact() {
		super(Material.iron);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = ResourceUtils.getIcon(reg, "blockPrysmal");
    }
}