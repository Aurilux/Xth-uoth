package com.aurilux.xar.block;

import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockIchorStill extends BlockStationary {

	public BlockIchorStill(int id) {
		super(id, Material.water);
		this.disableStats();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg) {
        this.theIcon = new Icon[] {reg.registerIcon(XAR_Ref.MOD_ID + ":ichor"), reg.registerIcon(XAR_Ref.MOD_ID + ":ichor_flow")};
    }
}
