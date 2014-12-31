package com.aurilux.xar.block;

import com.aurilux.xar.init.XARItems;
import com.aurilux.xar.util.ResourceUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockPrysmalOre extends Block {
	public BlockPrysmalOre() {
		super(Material.rock);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = ResourceUtils.getIcon(reg, "orePrysmal");
    }

    @Override
    public int quantityDropped(int metadata, int fortune, Random rand) {
        return 2 + rand.nextInt(3);
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune) {
        return XARItems.prysmalShard;
    }
}