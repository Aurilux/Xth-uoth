package com.aurilux.xar.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSeeds;
import com.aurilux.xar.lib.XARPotions;
import com.aurilux.xar.lib.XARModInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWightBulbPod extends ItemSeeds {

	public ItemWightBulbPod(int id, Block plantBlock, Block soilBlock) {
		super(plantBlock, soilBlock);
		this.setPotionEffect(XARPotions.wightBulbPodEffect);
		this.setUnlocalizedName("wightbulbPod");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(XARModInfo.MOD_ID + ":" + "wightbulbpod");
    }
}