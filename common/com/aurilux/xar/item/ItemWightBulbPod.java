package com.aurilux.xar.item;

import com.aurilux.xar.lib.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSeeds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWightBulbPod extends ItemSeeds {

	public ItemWightBulbPod() {
		super(XARBlocks.wightBulb, XARBlocks.aberrack);
		this.setPotionEffect(XARPotions.wightBulbPodEffect);
        XARItems.setItem(this, "wightbulbPod", "Wightbulb Pod");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(XARUtils.getTexturePath("wightbulbpod"));
    }
}