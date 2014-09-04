package com.aurilux.xar.lib;

import com.aurilux.xar.block.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

//@GameRegistry.ObjectHolder(XARModInfo.MOD_ID)
public class XARBlocks {
	public static Block oreCrystal;
	public static Block blockCrystal;
	public static Block aberrack;
	public static Block portal;
	public static Block wightBulb;
	public static Block ichor;
	
	public static void init() {
		wightBulb = new BlockWightBulb()
			.setHardness(0.0F)
			.setLightLevel(.5F)
			.setStepSound(Block.soundTypeGrass);
		aberrack = new BlockAberrack()
			.setHardness(0.4F)
			.setResistance(20.0F)
			.setStepSound(Block.soundTypeStone);
		ichor = new BlockIchorFluid()
			.setHardness(100.0F)
            .setLightOpacity(50);
		oreCrystal = new BlockCrystalOre()
			.setHardness(2.0F)
			.setResistance(15.0F)
			.setStepSound(Block.soundTypeStone);
		blockCrystal = new BlockCrystalCompact()
			.setHardness(5.0F)
			.setResistance(30.0F)
			.setStepSound(Block.soundTypeStone);
		portal = new BlockXthuothPortal()
        	.setHardness(-1.0F)
        	.setLightLevel(0.75F)
        	.setStepSound(Block.soundTypeGlass);
	}

    public static void setBlock(Block block, String name, String name2) {
        block.setCreativeTab(XARMisc.tabsXAR);
        block.setBlockName(name);
        block.setBlockTextureName(name);
        GameRegistry.registerBlock(block, name2);
    }
}