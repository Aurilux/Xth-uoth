package com.aurilux.xar.helpers;


import com.aurilux.xar.lib.Blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabXAR extends CreativeTabs {
	
	public CreativeTabXAR(int id, String name) {
		super(id, name);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Blocks.stoneStrange, 1);
	}
}
