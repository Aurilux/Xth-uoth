package aurilux.xthuoth.common.handler;

import aurilux.xthuoth.common.core.Xthuoth;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class ConfigHandler {
    //config settings
    public static int RADIANT_ENCH_ID;
    public static int DIM_ID;
    public static int ABERRANT_BIOME_ID;

	public static Configuration config;
	
	public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
	}

    private static void loadConfig() {
        //WORLDGEN
        String category = "worldgen";
        ABERRANT_BIOME_ID = loadIntPropClamped(category, "aberrant_biome_id", 40, 40, 255);
        DIM_ID = loadIntProp(category, "xthuoth_dim_id", DimensionManager.getNextFreeDimId());

        //MISC
        category = "misc";
        RADIANT_ENCH_ID = loadIntProp(category, "radiant_enchant_id", 15);

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Xthuoth.MOD_ID)) {
            //resync configs
            loadConfig();
        }
    }

    //// HELPERS
    private static int loadIntProp(String category, String identifier, int defaultValue) {
        Property prop = config.get(category, identifier, defaultValue,
                StatCollector.translateToLocal(identifier + ".desc"));
        return prop.getInt();
    }

    private static int loadIntPropClamped(String category, String identifier, int defaultValue, int minValue, int maxValue) {
        Property prop = config.get(category, identifier, defaultValue,
                StatCollector.translateToLocal(identifier + ".desc"), minValue, maxValue);
        return prop.getInt();
    }
    //// END HELPERS
}