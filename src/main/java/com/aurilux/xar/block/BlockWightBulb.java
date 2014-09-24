package com.aurilux.xar.block;

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARItems;
import com.aurilux.xar.lib.XARUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockWightBulb extends BlockBush {
    public BlockWightBulb() {
		super(Material.plants);
        XARBlocks.setBlock(this, "wightbulb", "Wightbulb");

        this.setTickRandomly(true);
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, .9F, 0.9F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(XARUtils.getTexturePath("wightbulb"));
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y - 1, z);
        return block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this) || block == XARBlocks.aberrack;
    }

    @Override
    public int quantityDropped(int metadata, int fortune, Random rand) {
        return 1 + (rand.nextInt(20) == 0 ? 1 : 0);
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune) {
        return XARItems.wightbulbPod;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        //TODO add particles that float up from the 'bulb'
    }
}