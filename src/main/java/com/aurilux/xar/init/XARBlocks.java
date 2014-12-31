package com.aurilux.xar.init;

import com.aurilux.xar.block.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

//@GameRegistry.ObjectHolder(XARModInfo.MOD_ID)
public class XARBlocks {
	public static Block orePrysmal;
	public static Block blockPrysmal;
	public static Block aberrack;
	public static Block wightbulb;
    public static Block thanabloom;
	public static Block ichor;
    public static Block immixer;
	
	public static void init() {
		/* TODO uncomment on next update
        thanabloom = new BlockThanabloom()
            .setHardness(0.0F)
            .setStepSound(Block.soundTypeGrass);
        setBlock(thanabloom, "thanabloom", "Thanabloom");
        */

		wightbulb = new BlockWightbulb()
			.setHardness(0.0F)
			.setStepSound(Block.soundTypeGrass);
        setBlock(wightbulb, "wightbulb", "Wightbulb");

		aberrack = new BlockAberrack()
			.setHardness(0.4F)
			.setResistance(20.0F)
			.setStepSound(Block.soundTypeStone);
        setBlock(aberrack, "blockAberrack", "Aberrack");

		ichor = new BlockIchorFluid()
			.setHardness(100.0F)
            .setLightOpacity(50);
        setBlock(ichor, "fluidIchor", "Ichor");

		orePrysmal = new BlockPrysmalOre()
			.setHardness(2.0F)
			.setResistance(15.0F)
			.setStepSound(Block.soundTypeStone);
        setBlock(orePrysmal, "orePrysmal", "PrysmalOre");

		blockPrysmal = new BlockPrysmalCompact()
			.setHardness(5.0F)
			.setResistance(30.0F)
			.setStepSound(Block.soundTypeStone);
        setBlock(blockPrysmal, "blockPrysmal", "PrysmalBlock");

        immixer = new BlockImmixer()
            .setHardness(2.0F)
            .setResistance(20.0F)
            .setStepSound(Block.soundTypeStone);
        setBlock(immixer, "immixer", "Immixer");
	}

    public static void setBlock(Block block, String name, String name2) {
        block.setCreativeTab(XARMisc.tab);
        block.setBlockName(name);
        block.setBlockTextureName(name);
        GameRegistry.registerBlock(block, name2);
    }
}