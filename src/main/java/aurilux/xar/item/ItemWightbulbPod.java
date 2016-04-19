package aurilux.xar.item;

import aurilux.xar.Xthuoth;
import aurilux.xar.init.ModBlocks;
import aurilux.xar.init.ModPotions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSeeds;

public class ItemWightbulbPod extends ItemSeeds {
	public ItemWightbulbPod() {
		super(ModBlocks.wightbulb, ModBlocks.aberrack);
		this.setPotionEffect(ModPotions.wightbulbPodEffect);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = Xthuoth.getIcon(reg, "wightbulbpod");
    }
}