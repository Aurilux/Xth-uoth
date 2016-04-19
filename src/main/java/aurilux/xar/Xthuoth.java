package aurilux.xar;

import aurilux.ardentcore.ModBase;
import aurilux.ardentcore.init.ModAchievements;
import aurilux.ardentcore.init.ModVersionChecker;
import aurilux.xar.handler.ConfigHandler;
import aurilux.xar.init.*;
import aurilux.xar.network.PacketDispatcher;
import aurilux.xar.network.ServerProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Xthuoth.MOD_ID, name = "Xth'uoth", version = Xthuoth.MOD_VERSION,
    guiFactory = "aurilux.xar.client.gui.config.XARGuiFactory",
    dependencies = "required-after:Thaumcraft@[4.2.3.2,);required-after:ArdentCore@[1.0.0,)")
public class Xthuoth extends ModBase {
    public static final String MOD_ID = "Xthuoth";
    public static final String MOD_VERSION = "1.1.1";
    public static final String versionUrl = "https://raw.githubusercontent.com/Aurilux/Xth-uoth/master/version.xml";

	@Mod.Instance(MOD_ID)
    public static Xthuoth instance;

    @SidedProxy(
            clientSide = "aurilux.xar.network.ClientProxy",
            serverSide = "aurilux.xar.network.ServerProxy")
    public static ServerProxy proxy;
    public static final Logger log = LogManager.getLogger("XTHUOTH");
    
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
        domain = MOD_ID.toLowerCase();
        ConfigHandler.init(e.getSuggestedConfigurationFile());
        //unlike most other event handlers, the 'ConfigHandler' must be initialized here in preInit to work properly
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        ModVersionChecker.registerModToUpdate(MOD_ID, MOD_VERSION, "dark_purple", versionUrl);

        //pre-initialization
        ModMisc.init();
        ModFluids.init();
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModPotions.init();
        PacketDispatcher.init();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
        //initialization
        ModWorldgen.init();

        ModAchievements.registerAchievements(MOD_ID,
                new ModAchievements.AchievementInfo(0, 0, new ItemStack(ModItems.itemResource, 1, 0), false, -1),
                new ModAchievements.AchievementInfo(3, 0, new ItemStack(ModItems.riftCatalyst), false, 0),
                new ModAchievements.AchievementInfo(3, -2, new ItemStack(ModItems.wightbulbPod), false, 1));
		
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

    //// ASSET WRAPPERS
    public static IIcon getIcon(IIconRegister reg, String textureName) {
        return reg.registerIcon(MOD_ID + ":" + textureName);
    }

    public static ResourceLocation getEntityRes(String textureName) {
        return new ResourceLocation(MOD_ID.toLowerCase(), "textures/entity/" + textureName);
    }

    public static ResourceLocation getEnviroRes(String textureName) {
        return new ResourceLocation(MOD_ID.toLowerCase(), "textures/environment/" + textureName);
    }

    public static ResourceLocation getGuiRes(String textureName) {
        return new ResourceLocation(MOD_ID.toLowerCase(), "textures/gui/" + textureName);
    }

    public static ResourceLocation getModelRes(String textureName) {
        return new ResourceLocation(MOD_ID.toLowerCase(), "models/" + textureName);
    }

    public static ResourceLocation getArmorRes(String textureName) {
        return new ResourceLocation(MOD_ID.toLowerCase(), "models/armor/" + textureName);
    }
    //// END ASSET WRAPPERS
}