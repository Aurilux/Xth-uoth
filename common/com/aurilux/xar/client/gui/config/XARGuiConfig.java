package com.aurilux.xar.client.gui.config;

import com.aurilux.xar.handler.ConfigurationHandler;
import com.aurilux.xar.lib.XARModInfo;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;


public class XARGuiConfig extends GuiConfig {

    public XARGuiConfig(GuiScreen screen) {
        super(screen,
            new ConfigElement(ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            XARModInfo.MOD_ID,
            false,
            false,
            GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString())
        );
    }
}