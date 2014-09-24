package com.aurilux.xar.potion;

import com.aurilux.xar.lib.XARModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionLifesight extends Potion {

	public PotionLifesight(int id, boolean bad, int color) {
		super(id, bad, color);
		this.setIconIndex(0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(XARModInfo.MOD_ID + ":textures/gui/potionEffectIcons.png"));
		return 0;
	}

	@Override
	public boolean isReady(int par1, int par2) {
		return par1 >= 1;
	}
}
