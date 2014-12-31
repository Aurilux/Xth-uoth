package com.aurilux.xar.block;

import com.aurilux.xar.init.XARBlocks;
import com.aurilux.xar.util.ResourceUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockThanabloom extends BlockBush {
    private IIcon[] icons;
    /** The radius which thanablooms can detect players */
    private int AWARENESS_RADIUS = 10;
    /** The state of this plant which determines the texture displayed and the light level emitted */
    private int state = 0;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        /*icons = new IIcon[5];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = ResourceUtils.getIcon(reg, "thanabloom" + i);
        }*/
        this.blockIcon = ResourceUtils.getIcon(reg, "thanabloom");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        //TODO return the correct icon dependent on the state
        //return icons[state];
        return this.blockIcon;
    }

    @Override
    public int damageDropped(int meta) {
        return 0;
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
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        AxisAlignedBB searchArea = AxisAlignedBB.getBoundingBox(x - AWARENESS_RADIUS, y - AWARENESS_RADIUS,
            z - AWARENESS_RADIUS, x + AWARENESS_RADIUS, y + AWARENESS_RADIUS, z + AWARENESS_RADIUS);
        int closestPlayerDistance = AWARENESS_RADIUS;
        for (EntityPlayer player : (List<EntityPlayer>) world.getEntitiesWithinAABB(EntityPlayer.class, searchArea)) {
            if (player.getDistance(x, y, z) < closestPlayerDistance) {
                closestPlayerDistance = (int) player.getDistance(x, y, z);
            }
        }

        if (closestPlayerDistance < 3) {
            state = 4;
        }
        else if (closestPlayerDistance < 5) {
            state = 3;
        }
        else if (closestPlayerDistance < 7) {
            state = 2;
        }
        else if (closestPlayerDistance < 9) {
            state = 1;
        }
        else {
            state = 0;
        }
    }
}
