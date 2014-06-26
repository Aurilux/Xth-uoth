package com.aurilux.xar.handlers;

import java.io.File;
import java.util.logging.Level;

import com.aurilux.xar.lib.XARModInfo;

import cpw.mods.fml.common.FMLLog;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
	public static Configuration config;
	
	public static void init(File configFile) {
		config = new Configuration(configFile);
		try {
			config.load();
			//other stuff
		}
        catch (Exception e) {
            //FMLLog.log(Level.SEVERE, e, XARModInfo.MOD_NAME + " has had a problem loading its configuration");
        }
        finally {
            config.save();
        }
	}
}