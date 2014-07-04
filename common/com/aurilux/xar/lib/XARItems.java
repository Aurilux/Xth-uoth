package com.aurilux.xar.lib;

import com.aurilux.xar.item.ItemCrystalLamina;
import com.aurilux.xar.item.ItemCrystalShard;
import com.aurilux.xar.item.ItemRiftCatalyst;
import com.aurilux.xar.item.ItemWightBulbPod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class XARItems {
	public static Item crystalShard;
	public static Item crystalLamina;
	public static Item riftCatalyst;
	public static Item wightbulbPod;
	
	public static void init() {
		crystalShard = new ItemCrystalShard();
		crystalLamina = new ItemCrystalLamina();
		riftCatalyst = new ItemRiftCatalyst();
		wightbulbPod = new ItemWightBulbPod();
	}

    public static void setItem(Item item, String name, String name2) {
        item.setCreativeTab(XARMisc.tabsXAR);
        item.setUnlocalizedName(name);
        item.setTextureName(name);
        GameRegistry.registerItem(item, name2, XARModInfo.MOD_ID);
    }
}
