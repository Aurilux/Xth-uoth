package com.aurilux.xar.handlers;

import java.io.File;
import java.util.logging.Level;

import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.common.FMLLog;

import net.minecraftforge.common.Configuration;

public class ConfigurationHandler {
	//TODO complete configuration settings
	public static Configuration config;
	
	public static void init(File configFile) {
		config = new Configuration(configFile);
		try {
			config.load();
			//other stuff
		}
        catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, XAR_Ref.MOD_NAME + " has had a problem loading its configuration");
        }
        finally {
            config.save();
        }
	}
}