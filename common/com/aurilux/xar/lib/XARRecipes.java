package com.aurilux.xar.lib;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class XARRecipes {
	public static void init() {
		//GameRegistry.addShapelessRecipe(null, null);
		//GameRegistry.addRecipe(null, null);
		GameRegistry.addRecipe(new ItemStack(XARItems.crystalLamina, 1), new Object[] {
			"XX",
			"XX",
			'X', XARItems.crystalShard});
		GameRegistry.addRecipe(new ItemStack(XARBlocks.blockCrystal, 1), new Object[] {
			"XXX",
			"XXX",
			"XXX",
			'X', XARItems.crystalLamina});
		GameRegistry.addRecipe(new ItemStack(XARItems.riftCatalyst, 1), new Object[] {
			"XXX",
			"XOX",
			"XXX",
			'X', XARItems.crystalLamina,
			'O', Items.ender_pearl});
	}
}