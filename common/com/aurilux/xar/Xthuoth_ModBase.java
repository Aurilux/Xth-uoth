package com.aurilux.xar;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

import com.aurilux.xar.handlers.LocalizationHandler;
import com.aurilux.xar.handlers.XARUpdateHandler;
import com.aurilux.xar.lib.Achievements;
import com.aurilux.xar.lib.Blocks;
import com.aurilux.xar.lib.Entities;
import com.aurilux.xar.lib.Items;
import com.aurilux.xar.lib.Misc;
import com.aurilux.xar.lib.Potions;
import com.aurilux.xar.lib.Recipes;
import com.aurilux.xar.lib.WorldGen;
import com.aurilux.xar.lib.XAR_Ref;
import com.aurilux.xar.proxy.CommonXARProxy;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = XAR_Ref.MOD_ID, name = XAR_Ref.MOD_NAME, version = XAR_Ref.MOD_VERSION)
@NetworkMod(channels = {XAR_Ref.MOD_ID}, clientSideRequired = true, serverSideRequired = false)
public class Xthuoth_ModBase {
	
	@Instance(XAR_Ref.MOD_ID)
    public static Xthuoth_ModBase instance;

    @SidedProxy(clientSide = XAR_Ref.CLIENT_PROXY, serverSide = XAR_Ref.SERVER_PROXY)
    public static CommonXARProxy proxy;
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		//initialize enum additions and load configuration
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		try {
			config.load();
			
			//register enums, blocks, items, and entities
			Misc.init(config);
			Blocks.init(config);
			Items.init(config);
			Entities.init(config);
			Achievements.init(config);
			Potions.init();
			Recipes.init();
			
			//register biomes, dimensions, and other world-gen
			WorldGen.init();
		}
        catch (Exception ex) {
            FMLLog.log(Level.SEVERE, ex, XAR_Ref.MOD_NAME + " has had a problem loading its configuration");
        }
        finally {
            config.save();
        }
		
		//language initialization is down here to make sure all blocks, items, etc are initialized first
		LocalizationHandler.load();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		//register event handlers
        NetworkRegistry.instance().registerConnectionHandler(new XARUpdateHandler());
		
		//register tile entities and other rendering
		proxy.initRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		//used for working with other mods
	}
}