package com.aurilux.xar.block;

import javax.swing.Icon;

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

public class BlockIchorFluid extends BlockFluidFinite {
	
	public IIcon ichorStill;
	public IIcon ichorFlowing;

	//TODO finish/complete
	public BlockIchorFluid(int id) {
		super(FluidRegistry.getFluid(XARFluids.ichor.getName()), Material.water);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		ichorStill = iconRegister.registerIcon(XARModInfo.MOD_ID + ":liquid_poison_still");
		ichorFlowing = iconRegister.registerIcon(XARModInfo.MOD_ID + ":liquid_poison_flowing");
	}
}
