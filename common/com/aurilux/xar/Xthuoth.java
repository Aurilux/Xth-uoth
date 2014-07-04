package com.aurilux.xar;

//import com.aurilux.xar.handlers.ConfigurationHandler; TODO uncomment when we upgrade to 1.7.10 (after Thaumcraft does)
import com.aurilux.xar.handlers.PlayerHandler;
import com.aurilux.xar.handlers.UpdateHandler;
import com.aurilux.xar.lib.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.aurilux.xar.proxy.CommonXARProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

@Mod(modid = XARModInfo.MOD_ID,
        name = XARModInfo.MOD_NAME,
        version = XARModInfo.MOD_VERSION,
        //guiFactory = XARModInfo.GUI_FACTORY,  TODO uncomment when we upgrade to 1.7.10 (after Thaumcraft does)
        dependencies = XARModInfo.MOD_DEPEND)
public class Xthuoth {
	@Instance(XARModInfo.MOD_ID)
    public static Xthuoth instance;

    @SidedProxy(clientSide = XARModInfo.CLIENT_PROXY, serverSide = XARModInfo.SERVER_PROXY)
    public static CommonXARProxy proxy;
    //public static final CreativeTabs creativeTabs = new XARCreativeTabs(XARModInfo.MOD_NAME);
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		//initialize enum additions and load configuration
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
        //ConfigurationHandler.init(e.getSuggestedConfigurationFile());
        //FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        try {
            XARMisc.init(config);
            XAREntities.init(config);
            XARAchievements.init(config);
        }
        finally {
            config.save();
        }

        //register enums, blocks, items, and entities
        XARFluids.init();
        XARBlocks.init();
        XARItems.init();
        XARPotions.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
        XARWorldgen.init();

		//register event handlers
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        FMLCommonHandler.instance().bus().register(new UpdateHandler());
		
		//register tile entities and other rendering
		proxy.initRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
        XARRecipes.init();
        XARResearch.init();
	}
}