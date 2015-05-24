package aurilux.xthuoth.common.init;

import aurilux.xthuoth.common.handler.ConfigHandler;
import aurilux.xthuoth.common.world.WorldProviderXthuoth;
import aurilux.xthuoth.common.world.biome.BiomeGenAberrant;
import aurilux.xthuoth.common.world.gen.feature.CrystalGenerator;
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