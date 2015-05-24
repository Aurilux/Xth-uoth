package aurilux.xthuoth.common.item.wand;

import aurilux.xthuoth.common.core.Xthuoth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemXARWandCap extends Item {
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = Xthuoth.assets.getIcon(reg, "prysmalWandCap");
    }
}
