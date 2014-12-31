package com.aurilux.xar.init;

import com.aurilux.xar.XARModInfo;
import com.aurilux.xar.enchantment.EnchantmentRadiant;
import com.aurilux.xar.handler.ConfigHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class XARMisc {
	public static EnumCreatureAttribute ABERRATION;
    public static ItemArmor.ArmorMaterial CRYSTAL;
	public static Enchantment radiant;

    public static final CreativeTabs tab = new CreativeTabs(XARModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return XARItems.riftCatalyst;
        }
    };
	
	public static void init() {
		//enums
		ABERRATION = EnumHelper.addCreatureAttribute("ABERRATION");
        CRYSTAL = EnumHelper.addArmorMaterial("CRYSTAL", 10, new int[]{2, 5, 4, 1}, 15);
		
		//enchantments
		radiant = new EnchantmentRadiant(ConfigHandler.RADIANT_ENCH_ID, 5, 3);
	}
}