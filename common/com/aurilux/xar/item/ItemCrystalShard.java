package com.aurilux.xar.item;

import com.aurilux.xar.lib.XARItems;
import com.aurilux.xar.lib.XARUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemCrystalShard extends Item {
	public ItemCrystalShard() {
		super();
        XARItems.setItem(this, "crystalShard", "Crystal Shard");
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(XARUtils.getTexturePath("crystalShard"));
    }
}