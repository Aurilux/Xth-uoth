package aurilux.xthuoth.common.block;

import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.init.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAberrack extends Block {

	public BlockAberrack() {
        super(Material.rock);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = Xthuoth.assets.getIcon(reg, "aberrack");
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        Block plant = plantable.getPlant(world, x, y + 1, z);
        return plant == ModBlocks.wightbulb && direction == ForgeDirection.UP;
    }
}