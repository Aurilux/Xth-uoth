package com.aurilux.xar.lib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockPortal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.aurilux.xar.block.BlockCrystalCompact;
import com.aurilux.xar.block.BlockCrystalOre;
import com.aurilux.xar.block.BlockIchorFlowing;
import com.aurilux.xar.block.BlockIchorStill;
import com.aurilux.xar.block.BlockWightBulb;
import com.aurilux.xar.block.BlockStrangeStone;
import com.aurilux.xar.block.BlockXthuothPortal;

import cpw.mods.fml.common.registry.GameRegistry;

public class Blocks {
	//ID trackers, initialized to default values
	private static int nextOtherID = 1000;
	private static int nextTerrainID = 200;
	
	//Block declarations
	public static Block oreCrystal;
	public static Block blockCrystal;
	public static Block stoneStrange;
	public static BlockPortal portal;
	public static BlockFlower wightBulb;
	public static Block ichorStill;
	public static BlockFluid ichorMoving;
	
	public static void init(Configuration config) {
		//terrain block inits. Necessary because their block Id's must be less than 256 to work correctly
		wightBulb = (BlockFlower) new BlockWightBulb(config.getTerrainBlock("Terrain Blocks", "Wight Bulb ID", nextTerrainID++, null).getInt())
        	.setCreativeTab(CreativeTabs.tabDecorations)
			.setHardness(0.0F)
			.setLightValue(.5f)
			.setTextureName("wightbulb")
			.setStepSound(Block.soundGrassFootstep);
		stoneStrange = new BlockStrangeStone(config.getTerrainBlock("Terrain Blocks", "Strange Stone ID", nextTerrainID++, null).getInt())
			.setCreativeTab(CreativeTabs.tabBlock)
			.setHardness(5.0f)
			.setResistance(10.0f)
			.setTextureName("strangeStone")
			.setStepSound(Block.soundStoneFootstep);
		//Make sure your flowing fluid block is above your still fluid block
		//or else block updates will change neighbors into the previous block ID
		ichorMoving = (BlockFluid) new BlockIchorFlowing(config.getTerrainBlock("Terrain Blocks", "Flowing Ichor ID", nextTerrainID++, null).getInt())
			.setHardness(100.0f)
			.setLightOpacity(1);
		ichorStill = new BlockIchorStill(config.getTerrainBlock("Terrain Blocks", "Still Ichor ID", nextTerrainID++, null).getInt())
			.setHardness(100.0f)
			.setLightOpacity(1);
		
		//other block inits
		oreCrystal = new BlockCrystalOre(config.getBlock("Other Blocks", "Crystal Ore ID", nextOtherID++, null).getInt())
    		.setCreativeTab(CreativeTabs.tabBlock)
			.setHardness(2.0f)
			.setResistance(5.0f)
			.setTextureName("oreCrystal")
			.setStepSound(Block.soundStoneFootstep);
		blockCrystal = new BlockCrystalCompact(config.getBlock("Other Blocks", "Crystal Block ID", nextOtherID++, null).getInt())
			.setCreativeTab(CreativeTabs.tabBlock)
			.setHardness(5.0f)
			.setResistance(10.0f)
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
		GameRegistry.registerBlock(ichorStill, "Ichor");
		GameRegistry.registerBlock(ichorMoving, "Ichor Flow");
		
		//for harvest level: 0 = wood, 1 = stone, 2 = iron, 3 = diamond
		MinecraftForge.setBlockHarvestLevel(oreCrystal, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(blockCrystal, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(stoneStrange, "pickaxe", 1);
	}
}