package com.aurilux.xar.block;

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARUtils;
import com.aurilux.xar.lib.XARWorldgen;
import com.aurilux.xar.world.TeleporterXthuoth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

import java.util.Random;

/* A creative only portal to help debug */
public class BlockXthuothPortal extends BlockPortal {
	private final Block portalFrameBlock = XARBlocks.blockCrystal;
	
	public BlockXthuothPortal() {
        super();
        XARBlocks.setBlock(this, "tile.portal", "Xth'uoth Portal");
    }
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(XARUtils.getTexturePath("tile.portal"));
	}
	
	public void updateTick(World world, int xCoord, int yCoord, int zCoord, Random ran) {
		super.updateTick(world, xCoord, yCoord, zCoord, ran);
		if (world.provider.isSurfaceWorld() && world.difficultySetting != EnumDifficulty.PEACEFUL) {
			int l;
			for (l = yCoord; !world.doesBlockHaveSolidTopSurface(world, xCoord, l, zCoord) && l > 0; --l) { ; }
			if (l > 0 && !world.isBlockNormalCubeDefault(xCoord, l + 1, zCoord, false)) {
				Entity entity = ItemMonsterPlacer.spawnCreature(world, 57, (double)xCoord + 0.5D, (double)l + 1.1D, (double)zCoord + 0.5D);
				if (entity != null) {
					entity.timeUntilPortal = entity.getPortalCooldown();
				}
			}
		}
    }

	/**
	 * Teleports the player to the appropriate dimension
	 */
    @Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	//Make sure the entity is neither riding nor being ridden
		if (entity.ridingEntity == null && entity.riddenByEntity == null && entity.timeUntilPortal == 0) {
			if (entity instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) entity;
				player.timeUntilPortal = 50;
            	int targetDimension = 0; // 0 = Overworld dimension ID
                if (player.dimension != XARWorldgen.DIM_ID) {
                	targetDimension = XARWorldgen.DIM_ID;
                }
                Teleporter tele = new TeleporterXthuoth(player.mcServer.worldServerForDimension(targetDimension));
                player.mcServer.getConfigurationManager().transferPlayerToDimension(player, targetDimension, tele);
			}
		}
	}
}

