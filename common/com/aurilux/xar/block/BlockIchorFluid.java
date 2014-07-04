package com.aurilux.xar.block;

import javax.swing.Icon;

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.aurilux.xar.lib.XARFluids;
import com.aurilux.xar.lib.XARModInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIchorFluid extends BlockFluidClassic {
	public IIcon ichorStill;
	public IIcon ichorFlowing;

	public BlockIchorFluid() {
        super(FluidRegistry.getFluid(XARFluids.ichor.getName()), Material.water);
        XARBlocks.setBlock(this, "fluidIchor", "Ichor");
        XARFluids.ichor.setBlock(this);
        XARFluids.ichor.setIcons(ichorStill, ichorFlowing);
	}

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1)? ichorStill : ichorFlowing;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		ichorStill = iconRegister.registerIcon(XARUtils.getTexturePath("ichor"));
		ichorFlowing = iconRegister.registerIcon(XARUtils.getTexturePath("ichor_flow"));
	}
}