package com.aurilux.xar.helpers;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.aurilux.xar.lib.Blocks;
import com.aurilux.xar.lib.Items;
import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockXAR extends Block {
	private Icon[] icons;
	private String[] textures;
	
	public BlockXAR(int id, Material mat, String... tex) {
		super(id, mat);
		textures = tex;
		this.setUnlocalizedName(textures[0]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		this.icons = new Icon[textures.length];

        for (int i = 0; i < this.icons.length; i++) {
            this.icons[i] = reg.registerIcon(XAR_Ref.MOD_ID + ":" + textures[i]);
        }
    }

	@Override
	public int damageDropped (int meta) {
		return meta;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public Icon getIcon (int side, int meta) {
		return icons[meta];
	}
	
	/** Used by plugins such as NEI and the Creative inventory to display and recognize sub-blocks i.e. the various types of wood */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks (int id, CreativeTabs tab, List list) {
		for (int i = 0; i < icons.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
	
	/** Returns the ID of the item to be dropped on block destruction.
	 * Works in tandem with quantityDropped, which determines how many of the item is dropped.
	 * 
	 * Although yet to be used, it is set up to be able to change the ID of the dropped item
	 * depending on the fortune level of the tool. Perhaps like an ore that drops increasingly
	 * rare gems the higher the fortune enchantment is?
	 */
	@Override
	public int idDropped(int metadata, Random rand, int fortune) {
		if (this.blockID == Blocks.oreCrystal.blockID) {
			return Items.crystalShard.itemID;
		}
		return this.blockID;
	}

	@Override
	public int quantityDropped(Random rand) {
		if (this.blockID == Blocks.oreCrystal.blockID) {
			return rand.nextInt(2) + 2; //range of 2 - 3
		}
		return 1;
	}
	
	/** Drops the same item as quantityDropped but with a higher chance i.e. with a fortune enchanted tool */
	@Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
		int bonus = 0;
		if (this.blockID == Blocks.oreCrystal.blockID) {
	        bonus = rand.nextInt(fortune + 1); //range of 0 - (1/2/3)
		}
		return this.quantityDropped(rand) + bonus;
    }
	
	/** The name is a little confusing, but only seems to be used to determine the XP drop of the block,
	 * if it's not silk touched that is.
	 */
	@Override
    public void dropBlockAsItemWithChance(World world, int xCoord, int yCoord, int zCoord, int metadata, float par6, int fortune)
    {
        super.dropBlockAsItemWithChance(world, xCoord, yCoord, zCoord, metadata, par6, fortune);

        if (this.idDropped(metadata, world.rand, fortune) != this.blockID)
        {
        	int xpAmount = 0;
    		if (this.blockID == Blocks.oreCrystal.blockID) {
    			//range of 0-2 is equivalent to coal
    			//range of 2-5 is equivalent to nether quartz and lapis
    			//range of 3-7 is equivalent to emerald and diamond
                xpAmount = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
    		}
            this.dropXpOnBlockBreak(world, xCoord, yCoord, zCoord, xpAmount);
        }
    }
}