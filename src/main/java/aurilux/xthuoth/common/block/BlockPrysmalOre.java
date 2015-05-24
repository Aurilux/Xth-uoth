package aurilux.xthuoth.common.block;

import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.init.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class BlockPrysmalOre extends Block {
	public BlockPrysmalOre() {
		super(Material.rock);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = Xthuoth.assets.getIcon(reg, "orePrysmal");
    }

    @Override
    public int quantityDropped(int metadata, int fortune, Random rand) {
        return 2 + rand.nextInt(3);
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune) {
        return new ItemStack(ModItems.itemResource, 1, 1).getItem();
    }
}