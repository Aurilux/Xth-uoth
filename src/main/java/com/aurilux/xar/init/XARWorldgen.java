package com.aurilux.xar.init;

import com.aurilux.xar.handler.ConfigHandler;
import com.aurilux.xar.world.WorldProviderXthuoth;
import com.aurilux.xar.world.biome.BiomeGenAberrant;
import com.aurilux.xar.world.gen.feature.CrystalGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;

public class XARWorldgen {
	public static BiomeGenBase aberrantBiome;
	
	public static void init() {
        GameRegistry.registerWorldGenerator(new CrystalGenerator(), 5);

        //Biome init
        aberrantBiome = new BiomeGenAberrant(ConfigHandler.ABERRANT_BIOME_ID);

        //Dimension init
		DimensionManager.registerProviderType(ConfigHandler.DIM_ID, WorldProviderXthuoth.class, true);
		DimensionManager.registerDimension(ConfigHandler.DIM_ID, ConfigHandler.DIM_ID);
	}
}