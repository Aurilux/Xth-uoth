package com.aurilux.xar.lib;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class XARItems {
	private static int nextItemID = 200;
	
	public static Item crystalShard;
	public static Item crystalLamina;
	public static Item riftCatalyst;
	public static Item wightbulbPod;
	
	public static void init(Configuration config) {
		//FIXME Do proper item registration
		//register the items
		/*crystalShard = new ItemCrystalShard(config.getItem("Items", "Crystal Shard ID", nextItemID++, null).getInt())
			.setCreativeTab(XARMisc.tabsXAR);
		crystalLamina = new ItemCrystalLamina(config.getItem("Items", "Crystal Lamina ID", nextItemID++, null).getInt())
			.setCreativeTab(XARMisc.tabsXAR);
		riftCatalyst = new ItemRiftCatalyst(config.getItem("Items", "Rift Catalyst ID", nextItemID++, null).getInt())
			.setCreativeTab(XARMisc.tabsXAR);
		wightbulbPod = new ItemWightBulbPod(config.getItem("Items", "wightbulb Pod ID", nextItemID++, null).getInt(), XARBlocks.wightBulb, XARBlocks.stoneStrange)
			.setCreativeTab(XARMisc.tabsXAR);
		
		GameRegistry.registerItem(crystalShard, "Crystal Shard");
		GameRegistry.registerItem(crystalLamina, "Crystal Lamina");
		GameRegistry.registerItem(riftCatalyst, "Rift Catalyst");
		GameRegistry.registerItem(wightbulbPod, "Wightbulb Pod");*/
	}
}
