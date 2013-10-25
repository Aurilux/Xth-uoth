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
		crystalShard = new ItemCrystalShard(nextItemID++)
			.setCreativeTab(CreativeTabs.tabMaterials);
		crystalLamina = new ItemCrystalLamina(nextItemID++)
			.setCreativeTab(CreativeTabs.tabMaterials);
		riftCatalyst = new ItemRiftCatalyst(nextItemID++)
			.setCreativeTab(CreativeTabs.tabMisc);
		
		GameRegistry.registerItem(crystalShard, "Crystal Shard");
		GameRegistry.registerItem(crystalLamina, "Crystal Lamina");
	}
}
