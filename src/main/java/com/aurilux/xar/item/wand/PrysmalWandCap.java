package com.aurilux.xar.item.wand;

import com.aurilux.xar.init.XARItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.WandCap;

public class PrysmalWandCap extends WandCap {
    ResourceLocation res = new ResourceLocation("thaumcraft", "textures/models/wand_cap_iron.png"); //ResourceUtils.getResource()

    public PrysmalWandCap() {
        super("prysmal", .5F, new ItemStack(XARItems.itemWandCap, 1), 10);
        this.setTexture(res);
    }

    @Override
    public String getResearch() {
        return "prysmalWandCaps";
    }
}
