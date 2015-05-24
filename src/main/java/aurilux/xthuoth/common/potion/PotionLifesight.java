package aurilux.xthuoth.common.potion;

import aurilux.ardentcore.common.util.RenderUtils;
import aurilux.xthuoth.common.core.Xthuoth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.potion.Potion;

public class PotionLifesight extends Potion {

	public PotionLifesight(int id, boolean bad, int color) {
		super(id, bad, color);
		this.setIconIndex(0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		RenderUtils.bindTexture(Xthuoth.assets.getGuiRes("potionEffectIcons.png"));
		return 0;
	}

	@Override
	public boolean isReady(int par1, int par2) {
		return par1 >= 1;
	}
}
