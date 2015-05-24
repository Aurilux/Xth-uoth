package aurilux.xthuoth.common.item;

import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.init.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemBucketIchor extends ItemBucket {
    public ItemBucketIchor() {
        super(ModBlocks.blockFluidIchor);
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("ichor",
            FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(this), new ItemStack(Items.bucket));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = Xthuoth.assets.getIcon(reg, "bucketIchor");
    }
}