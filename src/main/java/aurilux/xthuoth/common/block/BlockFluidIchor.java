package aurilux.xthuoth.common.block;

import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.init.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidIchor extends BlockFluidClassic {
    /**
     * The radius that farmland checks to see if the land is hydrated.
     * Since this block's material is water, it will also hydrate farmland
     */
    public static final int ENRICH_RADIUS = 4;
    @SideOnly(Side.CLIENT)
	public IIcon ichorStill;
    @SideOnly(Side.CLIENT)
	public IIcon ichorFlowing;

	public BlockFluidIchor() {
        super(ModBlocks.ichor, Material.water);
        ModBlocks.ichor.setBlock(this);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? ichorStill : ichorFlowing;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		ichorStill = Xthuoth.assets.getIcon(reg, "ichor");
		ichorFlowing = Xthuoth.assets.getIcon(reg, "ichor_flow");
        ModBlocks.ichor.setIcons(ichorStill, ichorFlowing);
	}
}