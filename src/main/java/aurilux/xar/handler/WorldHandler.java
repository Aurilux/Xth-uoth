package aurilux.xar.handler;

import aurilux.xar.block.BlockIchorFluid;
import aurilux.xar.init.ModItems;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.BlockEvent;

public class WorldHandler {
    /** Fills an empty bucket with ichor */
    @SubscribeEvent
    public void onBucketFill(FillBucketEvent e) {
        ItemStack result = fillCustomBucket(e.world, e.target);

        if (result == null) {
            return;
        }
        e.result = result;
        e.setResult(Event.Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        if (world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0 && block instanceof BlockIchorFluid) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(ModItems.bucketIchor);
        }
        else {
            return null;
        }
    }

    /** Increases the drops of plants/crops if they are within a certain distance of a block of ichor */
    @SubscribeEvent
    public void onPlantHarvest(BlockEvent.HarvestDropsEvent e) {
        // If its not growable (a crop) then we don't need to do anything
        if (!(e.block instanceof IGrowable)) return;

        Block ichorBlock = null;
        for (int x = e.x - BlockIchorFluid.ENRICH_RADIUS; x <= e.x + BlockIchorFluid.ENRICH_RADIUS; x++) {
            for (int y = e.y - 1; y < e.y; y++) {
                for (int z = e.z - BlockIchorFluid.ENRICH_RADIUS; z <= e.z + BlockIchorFluid.ENRICH_RADIUS; z++) {
                    ichorBlock = e.world.getBlock(x, y, z);
                    if (ichorBlock instanceof BlockIchorFluid) {
                        //increase drops
                        for (ItemStack stack : e.drops) {
                            if (e.world.rand.nextFloat() <= .2F) {
                                ItemStack copy = stack.copy();
                                e.drops.add(copy);
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
