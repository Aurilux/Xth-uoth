package aurilux.xar.init;

import aurilux.xar.handler.ConfigHandler;
import aurilux.xar.world.WorldProviderXthuoth;
import aurilux.xar.world.biome.BiomeGenAberrant;
import aurilux.xar.world.gen.feature.CrystalGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;

public class ModWorldgen {
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