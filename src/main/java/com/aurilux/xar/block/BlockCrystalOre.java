package com.aurilux.xar.block;

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARItems;
import com.aurilux.xar.lib.XARUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockCrystalOre extends Block {
	public BlockCrystalOre() {
		super(Material.rock);
        XARBlocks.setBlock(this, "oreCrystal", "Crystal Ore");
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(XARUtils.getTexturePath("oreCrystal"));
    }

    @Override
    public int quantityDropped(int metadata, int fortune, Random rand) {
        return 2 + rand.nextInt(3);
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune) {
        return XARItems.crystalShard;
    }
}