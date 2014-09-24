package com.aurilux.xar.item;

import com.aurilux.xar.lib.XARItems;
import com.aurilux.xar.lib.XARUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemCrystalLamina extends Item {
	public ItemCrystalLamina() {
		super();
        XARItems.setItem(this, "crystalLamina", "Crystal Lamina");
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(XARUtils.getTexturePath("crystalLamina"));
    }
}