package com.aurilux.xar.lib;

import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

import com.aurilux.xar.item.ItemCrystalLamina;
import com.aurilux.xar.item.ItemCrystalShard;
import com.aurilux.xar.item.ItemRiftCatalyst;
import com.aurilux.xar.item.ItemWightBulbPod;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items {
	private static int nextItemID = 200;
	
	public static Item crystalShard;
	public static Item crystalLamina;
	public static Item riftCatalyst;
	public static Item wightbulbPod;
	
	public static void init(Configuration config) {
		//register the items
		crystalShard = new ItemCrystalShard(config.getItem("Items", "Crystal Shard ID", nextItemID++, null).getInt())
			.setCreativeTab(Misc.tabsXAR);
		crystalLamina = new ItemCrystalLamina(config.getItem("Items", "Crystal Lamina ID", nextItemID++, null).getInt())
			.setCreativeTab(Misc.tabsXAR);
		riftCatalyst = new ItemRiftCatalyst(config.getItem("Items", "Rift Catalyst ID", nextItemID++, null).getInt())
			.setCreativeTab(Misc.tabsXAR);
		wightbulbPod = new ItemWightBulbPod(config.getItem("Items", "wightbulb Pod ID", nextItemID++, null).getInt(), Blocks.wightBulb.blockID, Blocks.stoneStrange.blockID)
			.setCreativeTab(Misc.tabsXAR);
		
		GameRegistry.registerItem(crystalShard, "Crystal Shard");
		GameRegistry.registerItem(crystalLamina, "Crystal Lamina");
		GameRegistry.registerItem(riftCatalyst, "Rift Catalyst");
		GameRegistry.registerItem(wightbulbPod, "Wightbulb Pod");
	}
}
