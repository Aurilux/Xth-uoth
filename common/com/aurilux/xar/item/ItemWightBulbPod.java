package com.aurilux.xar.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemSeeds;
import com.aurilux.xar.lib.Potions;
import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWightBulbPod extends ItemSeeds {

	public ItemWightBulbPod(int id, int plantBlockID, int soilBlockID) {
		super(id, plantBlockID, soilBlockID);
		this.setPotionEffect(Potions.wightBulbPodEffect);
		this.setUnlocalizedName("wightbulbPod");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg) {
        this.itemIcon = reg.registerIcon(XAR_Ref.MOD_ID + ":" + "wightbulbpod");
    }
}