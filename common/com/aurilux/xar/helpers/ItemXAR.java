package com.aurilux.xar.helpers;

import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public class ItemXAR extends Item {
	public Icon[] icons;
	public String[] textures;

	public ItemXAR(int id, String... tex) {
		super(id);
		textures = tex;
		this.setUnlocalizedName(textures[0]);
	}

	@Override
    @SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return icons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg) {
		this.icons = new Icon[textures.length];

        for (int i = 0; i < this.icons.length; i++) {
            this.icons[i] = reg.registerIcon(XAR_Ref.MOD_ID + ":" + textures[i]);
        }
    }
}