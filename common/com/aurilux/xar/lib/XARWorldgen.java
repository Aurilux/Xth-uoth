package com.aurilux.xar.lib;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;

import com.aurilux.xar.world.WorldProviderXthuoth;
import com.aurilux.xar.world.biome.BiomeGenAberrant;
import com.aurilux.xar.world.gen.feature.CorruptionGenerator;
import com.aurilux.xar.world.gen.feature.CrystalGenerator;

import cpw.mods.fml.common.registry.GameRegistry;

public class XARWorldgen {
	public static final int DIM_ID = DimensionManager.getNextFreeDimId();
	public static final int ABERRANT_ID = 30;
	
	public static final BiomeGenBase aberrantBiome = new BiomeGenAberrant(ABERRANT_ID);
	
	public static void init() {
        //TODO which weights would be more appropriate
		//GameRegistry.registerWorldGenerator(new CrystalGenerator(), 5);
		//GameRegistry.registerWorldGenerator(new CorruptionGenerator(), 5);
		
		DimensionManager.registerProviderType(DIM_ID, WorldProviderXthuoth.class, true);
		DimensionManager.registerDimension(DIM_ID, DIM_ID);
	}
}