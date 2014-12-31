package com.aurilux.xar.block;

import com.aurilux.xar.init.XARFluids;
import com.aurilux.xar.util.ResourceUtils;
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
        super(FluidRegistry.getFluid(XARFluids.ichor.getName()), Material.water);
        XARFluids.ichor.setBlock(this);
        XARFluids.ichor.setIcons(ichorStill, ichorFlowing);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? ichorStill : ichorFlowing;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		ichorStill = ResourceUtils.getIcon(reg, "ichor");
		ichorFlowing = ResourceUtils.getIcon(reg, "ichor_flow");
	}
}