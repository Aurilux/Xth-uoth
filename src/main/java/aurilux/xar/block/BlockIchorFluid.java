package aurilux.xar.block;

import aurilux.xar.Xthuoth;
import aurilux.xar.init.ModFluids;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockIchorFluid extends BlockFluidClassic {
    /**
     * The radius that farmland checks to see if the land is hydrated.
     * Since this block's material is water, it will also hydrate farmland
     */
    public static final int ENRICH_RADIUS = 4;
    //@SideOnly(Side.CLIENT)
	public IIcon ichorStill;
    //@SideOnly(Side.CLIENT)
	public IIcon ichorFlowing;

	public BlockIchorFluid() {
        super(FluidRegistry.getFluid(ModFluids.ichor.getName()), Material.water);
        ModFluids.ichor.setBlock(this);
        ModFluids.ichor.setIcons(ichorStill, ichorFlowing);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? ichorStill : ichorFlowing;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		ichorStill = Xthuoth.getIcon(reg, "ichor");
		ichorFlowing = Xthuoth.getIcon(reg, "ichor_flow");
	}
}