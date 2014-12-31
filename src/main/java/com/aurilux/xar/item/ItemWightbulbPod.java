package com.aurilux.xar.item;

import com.aurilux.xar.init.XARBlocks;
import com.aurilux.xar.init.XARPotions;
import com.aurilux.xar.util.ResourceUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSeeds;

public class ItemWightbulbPod extends ItemSeeds {
	public ItemWightbulbPod() {
		super(XARBlocks.wightbulb, XARBlocks.aberrack);
		this.setPotionEffect(XARPotions.wightbulbPodEffect);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = ResourceUtils.getIcon(reg, "wightbulbpod");
    }
}