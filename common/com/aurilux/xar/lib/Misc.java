package com.aurilux.xar.lib;

import com.aurilux.xar.enchantment.EnchantmentRadiant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;

public class Misc {
	
	public static EnumCreatureAttribute ABERRATION;
	public static Enchantment radiant;
	
	public static void init(Configuration config) {
		//enums
		ABERRATION = EnumHelper.addCreatureAttribute("aberration");
		
		//enchantments TODO get radiant enchantment ID
		radiant = new EnchantmentRadiant(15, 5, 3);
	}
}