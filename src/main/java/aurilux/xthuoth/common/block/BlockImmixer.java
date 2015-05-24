package aurilux.xthuoth.common.block;

import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.tileentity.TileEntityImmixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** This block combines individual potions into one potion with all the potion effects */
public class BlockImmixer extends BlockContainer {
    public BlockImmixer() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityImmixer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = Xthuoth.assets.getIcon(reg, "immixer");
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     * TODO change this to false when I make a custom model
     */
    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }

    /** TODO change this to false when I make a custom model */
    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    /** TODO change this to 25 (same as BlockBrewingStand when I make a custom model */
    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if (!player.isSneaking()) {
            if (world.getTileEntity(x, y, z) instanceof TileEntityImmixer) {
                player.openGui(Xthuoth.instance, Xthuoth.proxy.GUI_ID_IMMIXER, world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
        TileEntity tileentity = world.getTileEntity(x, y, z);

        if (tileentity instanceof TileEntityImmixer) {
            TileEntityImmixer tileEntityImmixer = (TileEntityImmixer)tileentity;

            for (int i = 0; i < tileEntityImmixer.getSizeInventory(); i++) {
                ItemStack itemstack = tileEntityImmixer.getStackInSlot(i);

                if (itemstack != null) {
                    float dirX = world.rand.nextFloat() * 0.8F + 0.1F;
                    float dirY = world.rand.nextFloat() * 0.8F + 0.1F;
                    float dirZ = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int stackAmount = world.rand.nextInt(21) + 10;
                        if (stackAmount > itemstack.stackSize) {
                            stackAmount = itemstack.stackSize;
                        }
                        itemstack.stackSize -= stackAmount;

                        EntityItem entityitem = new EntityItem(world, (double)((float)x + dirX),
                            (double)((float)y + dirY), (double)((float)z + dirZ), new ItemStack(itemstack.getItem(),
                            stackAmount, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }
        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    /**
     * TODO functions from BlockBrewingStand I'll need to complete once I have the custom model
     *

     /**
     * A randomly called display update to be able to add particles or other items for display
     *
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        double d0 = (double)((float)p_149734_2_ + 0.4F + p_149734_5_.nextFloat() * 0.2F);
        double d1 = (double)((float)p_149734_3_ + 0.7F + p_149734_5_.nextFloat() * 0.3F);
        double d2 = (double)((float)p_149734_4_ + 0.4F + p_149734_5_.nextFloat() * 0.2F);
        p_149734_1_.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

     /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     *
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
    {
        this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     *
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

     /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     *
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     *
    public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
    {
        return Container.calcRedstoneFromInventory((IInventory) p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        super.registerBlockIcons(p_149651_1_);
        this.iconBrewingStandBase = p_149651_1_.registerIcon(this.getTextureName() + "_base");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconBrewingStandBase()
    {
        return this.iconBrewingStandBase;
    }
     */
}