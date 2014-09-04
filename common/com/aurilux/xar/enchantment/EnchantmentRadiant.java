package com.aurilux.xar.enchantment;

import com.aurilux.xar.lib.XARMisc;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;

public class EnchantmentRadiant extends EnchantmentDamage {

	public EnchantmentRadiant(int id, int weight, int damage) {
		super(id, weight, damage);
	}

    @Override
	public int getMinEnchantability(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return this.getMinEnchantability(level) + 20;
    }

    @Override
    public float func_152376_a(int level, EnumCreatureAttribute attr) {
        return attr == XARMisc.ABERRATION ? (float)level * 2.5F : 0.0F;
    }

    @Override
    public String getName() {
        return "enchantment.damage.aberration";
    }
}