package com.aurilux.xar.lib;

import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraft.creativetab.CreativeTabs;

import com.aurilux.xar.item.ItemCrystalLamina;
import com.aurilux.xar.item.ItemCrystalShard;
import com.aurilux.xar.item.ItemRiftCatalyst;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items {
	private static int nextItemID = 200;
	
	public static Item crystalShard;
	public static Item crystalLamina;
	public static Item riftCatalyst;
	
	public static void init(Configuration config) {
		//register the items
		crystalShard = new ItemCrystalShard(config.getItem("Items", "Crystal Shard ID", nextItemID++, null).getInt())
			.setCreativeTab(Misc.tabsXAR);
		crystalLamina = new ItemCrystalLamina(config.getItem("Items", "Crystal Lamina ID", nextItemID++, null).getInt())
			.setCreativeTab(Misc.tabsXAR);
		riftCatalyst = new ItemRiftCatalyst(config.getItem("Items", "Rift Catalyst ID", nextItemID++, null).getInt())
			.setCreativeTab(Misc.tabsXAR);
		
		GameRegistry.registerItem(crystalShard, "Crystal Shard");
		GameRegistry.registerItem(crystalLamina, "Crystal Lamina");
	}
}
