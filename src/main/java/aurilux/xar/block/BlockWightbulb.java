package aurilux.xar.block;

import aurilux.xar.Xthuoth;
import aurilux.xar.init.ModBlocks;
import aurilux.xar.init.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockWightbulb extends BlockBush {
    public BlockWightbulb() {
		super();
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, .9F, 0.9F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = Xthuoth.getIcon(reg, "wightbulb");
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y - 1, z);
        return block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this) || block == ModBlocks.aberrack;
    }

    @Override
    public int quantityDropped(int metadata, int fortune, Random rand) {
        return 1 + (rand.nextInt(20) == 0 ? 1 : 0);
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune) {
        return ModItems.wightbulbPod;
    }
}