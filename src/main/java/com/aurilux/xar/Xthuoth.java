package com.aurilux.xar;

import com.aurilux.xar.handler.ConfigHandler;
import com.aurilux.xar.init.*;
import com.aurilux.xar.proxy.XARServerProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = XARModInfo.MOD_ID,
    name = XARModInfo.MOD_NAME,
    version = XARModInfo.MOD_VERSION,
    guiFactory = XARModInfo.GUI_FACTORY,
    dependencies = XARModInfo.MOD_DEPEND)
public class Xthuoth {
	@Instance(XARModInfo.MOD_ID)
    public static Xthuoth instance;

    @SidedProxy(clientSide = XARModInfo.CLIENT_PROXY, serverSide = XARModInfo.SERVER_PROXY)
    public static XARServerProxy proxy;

    public static final Logger log = LogManager.getLogger(XARModInfo.MOD_NAME);
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
        ConfigHandler.init(e.getSuggestedConfigurationFile());
        //unlike most other event handlers, the 'ConfigHandler' must be initialized here in preInit to work properly
        FMLCommonHandler.instance().bus().register(new ConfigHandler());

        //pre-initialization
        XARMisc.init();
        XAREntities.init();
        XARFluids.init();
        XARBlocks.init();
        XARItems.init();
        XARPotions.init();
        XARAchievements.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
        //initialization
        XARWorldgen.init();
		
		//register client-specific(renderers, tile entities, client event handlers, etc) and
        //server-specific(?) objects
		proxy.init();

        //We don't want any Thaumcraft generation to be in Xth'uoth
        FMLInterModComms.sendMessage("Thaumcraft", "dimensionBlacklist", ConfigHandler.DIM_ID + ":0");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
        //post-initialization
        XARAspects.init();
        XARRecipes.init();
        XARResearch.init();
	}
}