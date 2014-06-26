package com.aurilux.xar;

import java.util.logging.Level;

import net.minecraftforge.common.config.Configuration;

import com.aurilux.xar.handlers.LocalizationHandler;
import com.aurilux.xar.handlers.XARUpdateHandler;
import com.aurilux.xar.lib.XARAchievements;
import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XAREntities;
import com.aurilux.xar.lib.XARFluids;
import com.aurilux.xar.lib.XARItems;
import com.aurilux.xar.lib.XARMisc;
import com.aurilux.xar.lib.XARPotions;
import com.aurilux.xar.lib.XARRecipes;
import com.aurilux.xar.lib.XARWorldgen;
import com.aurilux.xar.lib.XARModInfo;
import com.aurilux.xar.proxy.CommonXARProxy;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = XARModInfo.MOD_ID, name = XARModInfo.MOD_NAME, version = XARModInfo.MOD_VERSION, dependencies = XARModInfo.MOD_DEPEND)
public class Xthuoth_ModBase {
	
	@Instance(XARModInfo.MOD_ID)
    public static Xthuoth_ModBase instance;

    @SidedProxy(clientSide = XARModInfo.CLIENT_PROXY, serverSide = XARModInfo.SERVER_PROXY)
    public static CommonXARProxy proxy;
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		//initialize enum additions and load configuration
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		try {
			config.load();
			
			//register enums, blocks, items, and entities
			XARMisc.init(config);
			XARBlocks.init(config);
			XARFluids.init(config);
			XARItems.init(config);
			XAREntities.init(config);
			XARAchievements.init(config);
			XARPotions.init();
			XARRecipes.init();
			
			//register biomes, dimensions, and other world-gen
			XARWorldgen.init();
		}
        catch (Exception ex) {
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
		//TODO make packet classes and then complete this
        //NetworkRegistry.instance().registerConnectionHandler(new XARUpdateHandler());
		
		//register tile entities and other rendering
		proxy.initRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		//used for working with other mods
	}
}