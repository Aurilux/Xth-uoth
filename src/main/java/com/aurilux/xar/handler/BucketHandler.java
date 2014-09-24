package com.aurilux.xar.handler;

import com.aurilux.xar.block.BlockIchorFluid;
import com.aurilux.xar.lib.XARItems;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler {
    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result == null) {
            return;
        }
        event.result = result;
        event.setResult(Event.Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        if (world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0 && block instanceof BlockIchorFluid) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(XARItems.bucketIchor);
        }
        else {
            return null;
        }
    }
}