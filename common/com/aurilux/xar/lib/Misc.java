package com.aurilux.xar.lib;

import com.aurilux.xar.enchantment.EnchantmentRadiant;
import com.aurilux.xar.helpers.CreativeTabXAR;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;

public class Misc {
	private static final int DEFAULT_RADIANT_ENCH_ID = 15;
    
	public static CreativeTabs tabsXAR;
	public static EnumCreatureAttribute ABERRATION;
	public static Enchantment radiant;
	
	public static void init(Configuration config) {
		tabsXAR = new CreativeTabXAR(CreativeTabs.getNextID(), "tabsXAR");
		
		//enums
		ABERRATION = EnumHelper.addCreatureAttribute("ABERRATION");
		
		//enchantments
		radiant = new EnchantmentRadiant(config.get("Misc", "Radiant Ench ID", DEFAULT_RADIANT_ENCH_ID, null).getInt(), 5, 3);
	}
}