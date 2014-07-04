package com.aurilux.xar.helpers;


import com.aurilux.xar.lib.XARBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabXAR extends CreativeTabs {
	//FIXME getTabIconIndex and general completion
	public CreativeTabXAR(int id, String name) {
		super(id, name);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(XARBlocks.aberrack, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return null;
	}
}
