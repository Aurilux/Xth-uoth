package com.aurilux.xar;

import com.aurilux.xar.handler.ConfigurationHandler;
import com.aurilux.xar.handler.PlayerHandler;
import com.aurilux.xar.handler.UpdateHandler;
import com.aurilux.xar.lib.*;
import com.aurilux.xar.proxy.CommonXARProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = XARModInfo.MOD_ID,
    name = XARModInfo.MOD_NAME,
    version = XARModInfo.MOD_VERSION,
    guiFactory = XARModInfo.GUI_FACTORY,
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
        ConfigurationHandler.init(e.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

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
        XARWorldgen.init();

		//register event handlers
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        FMLCommonHandler.instance().bus().register(new UpdateHandler());
		
		//register tile entities and other rendering
		proxy.initRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
        XARAspects.init();
        XARRecipes.init();
        XARResearch.init();
	}
}