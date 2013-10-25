package com.aurilux.xar.lib;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void init() {
		//GameRegistry.addShapelessRecipe(null, null);
		//GameRegistry.addRecipe(null, null);
		GameRegistry.addRecipe(new ItemStack(Items.crystalLamina, 1), new Object[] {
			"XX",
			"XX",
			'X', Items.crystalShard});
		GameRegistry.addRecipe(new ItemStack(Blocks.blockCrystal, 1), new Object[] {
			"XXX",
			"XXX",
			"XXX",
			'X', Items.crystalLamina});
		GameRegistry.addRecipe(new ItemStack(Items.riftCatalyst, 1), new Object[] {
			"XXX",
			"XOX",
			"XXX",
			'X', Items.crystalLamina,
			'O', Item.enderPearl});
	}
}