package com.aurilux.xar.handler;

import com.aurilux.xar.lib.XAREntities;
import com.aurilux.xar.lib.XARMisc;
import com.aurilux.xar.lib.XARModInfo;
import com.aurilux.xar.lib.XARWorldgen;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class ConfigurationHandler {
	public static Configuration config;
	
	public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
	}

    private static void loadConfig() {
        //ENTITY ID'S
        String category = "entities";
        Property currentConfig = config.get(category, "blighter_entity_id", 101);
        currentConfig.comment = "The entity ID for the Blighter";
        XAREntities.BLIGHTER_ENTITY_ID = currentConfig.getInt();

        currentConfig = config.get(category, "rift_entity_id", 102);
        currentConfig.comment = "The entity ID for the Rift";
        XAREntities.RIFT_ENTITY_ID = currentConfig.getInt();

        currentConfig = config.get(category, "catalyst_entity_id", 103);
        currentConfig.comment = "The entity ID for the Catalyst";
        XAREntities.CATALYST_ENTITY_ID = currentConfig.getInt();

        //BIOME ID'S
        category = "biomes";
        currentConfig = config.get(category, "aberrant_biome_id", 30);
        currentConfig.comment = "The biome ID for the biome exclusive to Xth'uoth";
        XARWorldgen.ABERRANT_BIOME_ID = currentConfig.getInt();

        //MISC ID'S
        category = "misc";
        currentConfig = config.get(category, "radiant_enchant_id", 15);
        currentConfig.comment = "The entity ID for the only biome in Xth'uoth";
        XARMisc.RADIANT_ENCH_ID = currentConfig.getInt();

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(XARModInfo.MOD_ID)) {
            //resync configs
            loadConfig();
        }
    }
}