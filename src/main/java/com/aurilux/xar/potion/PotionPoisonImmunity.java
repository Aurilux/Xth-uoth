package com.aurilux.xar.potion;

import com.aurilux.xar.util.ResourceUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class PotionPoisonImmunity extends Potion {

	public PotionPoisonImmunity(int id, boolean bad, int color) {
		super(id, bad, color);
		this.setIconIndex(0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceUtils.POTION_ICONS);
		return 1;
	}

	@Override
	public boolean isReady(int par1, int par2) {
		return par1 >= 1;
	}
}