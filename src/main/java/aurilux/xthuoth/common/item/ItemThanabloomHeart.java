package aurilux.xthuoth.common.item;

import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.init.ModBlocks;
import aurilux.xthuoth.common.init.ModPotions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSeeds;

public class ItemThanabloomHeart extends ItemSeeds {
	public ItemThanabloomHeart() {
		super(ModBlocks.thanabloom, ModBlocks.blockAberrack);
		this.setPotionEffect(ModPotions.thanabloomEffect);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = Xthuoth.assets.getIcon(reg, "thanabloomHeart");
    }
}