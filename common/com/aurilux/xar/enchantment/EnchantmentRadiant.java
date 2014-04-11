package com.aurilux.xar.enchantment;

import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.StatCollector;

import com.aurilux.xar.lib.Misc;

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
    public float calcModifierLiving(int level, EntityLivingBase entity) {
        return entity.getCreatureAttribute() == Misc.ABERRATION ? (float)level * 2.5F : 0.0F;
    }

    @Override
    public String getName() {
        return "enchantment.damage.aberration";
    }
    
    /*@Override
    public String getTranslatedName(int level) {
	    return "Radiant " + StatCollector.translateToLocal("enchantment.level." + level);
    }*/
}