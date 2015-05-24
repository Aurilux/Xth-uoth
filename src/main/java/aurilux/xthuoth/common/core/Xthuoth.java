package aurilux.xthuoth.common.core;

import aurilux.ardentcore.common.init.ModVersionChecker;
import aurilux.ardentcore.common.mod.AssetWrapper;
import aurilux.ardentcore.common.mod.NetworkWrapper;
import aurilux.xthuoth.common.handler.ConfigHandler;
import aurilux.xthuoth.common.init.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Xthuoth.MOD_ID, name = "Xth'uoth", version = Xthuoth.MOD_VERSION,
    guiFactory = "aurilux.xthuoth.client.gui.config.XARGuiFactory",
    dependencies = "required-after:Thaumcraft@[4.2.3.5,);required-after:ArdentCore@[1.0.0,)")
public class Xthuoth {
    public static final String MOD_ID = "Xthuoth";
    public static final String MOD_VERSION = "1.1.1";
    public static final String versionUrl = "https://raw.githubusercontent.com/Aurilux/Xth-uoth/master/version.xml";

    public static final AssetWrapper assets = new AssetWrapper(MOD_ID.toLowerCase(), new CreativeTabs(Xthuoth.MOD_ID) {
        //This is actually safe to reference here because the icon object will still get instantiated WAY
        //before the icon is called
        @Override
        public Item getTabIconItem() {
            return ModItems.riftCatalyst;
        }
    });
    public static final Logger logger = LogManager.getLogger(MOD_ID.toUpperCase());
    public static final NetworkWrapper network = new NetworkWrapper(MOD_ID);

	@Mod.Instance(MOD_ID)
    public static Xthuoth instance;

    @SidedProxy(
            clientSide = "aurilux.xthuoth.client.core.ClientProxy",
            serverSide = "aurilux.xthuoth.common.core.CommonProxy")
    public static CommonProxy proxy;
    
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
        ConfigHandler.init(e.getSuggestedConfigurationFile());
        //unlike most other event handlers, the 'ConfigHandler' must be initialized here in preInit to work properly
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        ModVersionChecker.registerModToUpdate(MOD_ID, MOD_VERSION, EnumChatFormatting.DARK_PURPLE, versionUrl);

        //pre-initialization
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModPotions.init();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
        //initialization
        ModWorldgen.init();
        ModMisc.init();
		
		//register client-specific(renderers, tile entities, client event handlers, etc) and
        //server-specific(?) objects
		proxy.init();

        //We don't want any Thaumcraft generation to be in Xth'uoth
        FMLInterModComms.sendMessage("Thaumcraft", "dimensionBlacklist", ConfigHandler.DIM_ID + ":0");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
        //post-initialization
        ModAspects.init();
        ModRecipes.init();
        ModResearch.init();
	}
}