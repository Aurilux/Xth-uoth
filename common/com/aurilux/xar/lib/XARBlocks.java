package com.aurilux.xar.lib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockPortal;
import net.minecraftforge.common.config.Configuration;

import com.aurilux.xar.block.BlockIchorFluid;

public class XARBlocks {
	//ID trackers, initialized to default values
	private static int nextOtherID = 1000;
	private static int nextTerrainID = 200;
	
	//Block declarations
	public static Block oreCrystal;
	public static Block blockCrystal;
	public static Block stoneStrange;
	public static BlockPortal portal;
	public static BlockFlower wightBulb;
	public static BlockIchorFluid ichor;
	
	public static void init(Configuration config) {
		/*//terrain block inits. Necessary because their block Id's must be less than 256 to work correctly
		wightBulb = (BlockFlower) new BlockWightBulb(config.getTerrainBlock("Terrain Blocks", "Wight Bulb ID", nextTerrainID++, null).getInt())
			.setCreativeTab(XARMisc.tabsXAR)
			.setHardness(0.0F)
			.setLightValue(.5F)
			.setTextureName("wightbulb")
			.setStepSound(Block.soundGrassFootstep);
		stoneStrange = new BlockStrangeStone(config.getTerrainBlock("Terrain Blocks", "Strange Stone ID", nextTerrainID++, null).getInt())
			.setCreativeTab(XARMisc.tabsXAR)
			.setHardness(0.4F)
			.setResistance(20.0F)
			.setTextureName("strangeStone")
			.setStepSound(Block.soundStoneFootstep);
		//Make sure your flowing fluid block is declared right before your still fluid block
		//or else block updates will change neighbors into the previous block ID
		ichor = new BlockIchorFluid(config.getTerrainBlock("Terrain Blocks", "Flowing Ichor ID", nextTerrainID++, null).getInt())
			.setHardness(100.0F)
			.setLightOpacity(1);
		
		//other block inits
		oreCrystal = new BlockCrystalOre(config.getBlock("Other Blocks", "Crystal Ore ID", nextOtherID++, null).getInt())
			.setCreativeTab(XARMisc.tabsXAR)
			.setHardness(2.0F)
			.setResistance(15.0F)
			.setTextureName("oreCrystal")
			.setStepSound(Block.soundStoneFootstep);
		blockCrystal = new BlockCrystalCompact(config.getBlock("Other Blocks", "Crystal Block ID", nextOtherID++, null).getInt())
			.setCreativeTab(XARMisc.tabsXAR)
			.setHardness(5.0F)
			.setResistance(30.0F)
			.setTextureName("blockCrystal")
			.setStepSound(Block.soundStoneFootstep);
		portal = (BlockPortal) new BlockXthuothPortal(config.getBlock("Other Blocks", "Xth'uoth Portal ID", nextOtherID++, null).getInt())
        	.setHardness(-1.0F)
        	.setLightValue(0.75F)
			.setTextureName("portalXthuoth")
        	.setStepSound(Block.soundGlassFootstep);
		
		//register all blocks
		GameRegistry.registerBlock(oreCrystal, "Geode");
		GameRegistry.registerBlock(blockCrystal, "Crystal Block");
		GameRegistry.registerBlock(stoneStrange, "Aberrack");
		GameRegistry.registerBlock(portal, "Xthuoth Portal");
		GameRegistry.registerBlock(wightBulb, "Wightbulb");
		GameRegistry.registerBlock(ichor, "Ichor");
		
		//for harvest level: 0 = wood, 1 = stone, 2 = iron, 3 = diamond
		MinecraftForge.setBlockHarvestLevel(oreCrystal, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(blockCrystal, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(stoneStrange, "pickaxe", 1);*/
	}
}