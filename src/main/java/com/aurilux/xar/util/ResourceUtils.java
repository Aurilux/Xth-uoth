package com.aurilux.xar.util;

import com.aurilux.xar.XARModInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class ResourceUtils {
    public static final String BLOCKS = "textures/blocks/";
    public static final String ENTITY = "textures/entity/";
    public static final String ENVIRON = "textures/environment/";
    public static final String GUI = "textures/gui/";
    public static final String ITEMS = "textures/items/";
    public static final String MODEL = "models/";
    public static final String ARMOR = MODEL + "armor/";

    //Thaumcraft resources
    public static final ResourceLocation CATEG_BG_ELDRITCH = new ResourceLocation("thaumcraft", "textures/gui/gui_researchbackeldritch.png");

    public static final ResourceLocation SKY_TEXTURE = getResource(ENVIRON + "xthuoth_space.png");
    public static final ResourceLocation RIFT_TEXTURE = getResource(ENTITY + "rift.png");
    public static final ResourceLocation POTION_ICONS = getResource(GUI + "potionEffectIcons.png");
    public static final ResourceLocation TEXTURE_GUI_IMMIXER = getResource(GUI + "immixerGui.png");

    /**
     * ResourceLocation does not search the mod's resource directory for resources like the IIconRegister does.
     * So you must ALWAYS provide a near-absolute path (starting with 'textures' is best) for Minecraft to find the
     * resource correctly.
     * @param path the resource's path
     * @return the ResourceLocation
     */
    public static ResourceLocation getResource(String path) {
        return new ResourceLocation(XARModInfo.MOD_ID + ":" + path);
    }

    /**
     * Unlike 'getResource', this method just returns a string with the mod id and resource name. This should only be
     * used with IIconRegister
     * @param name the name of resource file
     * @return the mod resource string in the format MOD_ID:FILE_NAME
     */
    public static String getTexturePath(String name) {
        return XARModInfo.MOD_ID + ":" + name;
    }

    /**
     * @param reg the icon register
     * @param name the texture name
     * @return the icon
     */
    public static IIcon getIcon(IIconRegister reg, String name) { return reg.registerIcon(getTexturePath(name)); }
}