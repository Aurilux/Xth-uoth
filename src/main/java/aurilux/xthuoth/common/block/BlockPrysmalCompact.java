package aurilux.xthuoth.common.block;

import aurilux.xthuoth.common.core.Xthuoth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockPrysmalCompact extends Block {
	public BlockPrysmalCompact() {
		super(Material.iron);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = Xthuoth.assets.getIcon(reg, "blockPrysmal");
    }
}