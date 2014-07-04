package com.aurilux.xar.handlers;

/* TODO uncomment when we upgrade to 1.7.10 (after Thaumcraft does)
import java.io.File;
import java.util.logging.Level;

import com.aurilux.xar.lib.XARModInfo;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLLog;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;


public class ConfigurationHandler {
	public static Configuration config;
	
	public static void init(File configFile) {

        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
	}

    private static void loadConfig() {
        //load variable values here

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
*/