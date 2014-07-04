package com.aurilux.xar.block;

import java.util.Random;

import com.aurilux.xar.lib.XARUtils;
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

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARModInfo;
import com.aurilux.xar.lib.XARWorldgen;
import com.aurilux.xar.world.TeleporterXthuoth;

public class BlockXthuothPortal extends BlockPortal {
	//TODO Update to function like the normal portal
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
		//TODO spawn aberrations and spread corruption
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
     * Checks to see if this location is valid to create a portal and will return True if it does.
     *//*
    @Override
    public boolean tryToCreatePortal(World world, int x, int y, int z) {
    	//TODO let it form a portal so long as a  portal in inside (should already be handled by rift catalyst)
        byte b0 = 0; //facing east/west
        byte b1 = 0; //facing north/south
        
        //check if the portal is facing east/west
        if (world.getBlockId(x - 1, y, z) == portalFrameID
        		|| world.getBlockId(x + 1, y, z) == portalFrameID) {
            b0 = 1;
        }
        
        //check if the portal is facing north/south
        if (world.getBlockId(x, y, z - 1) == portalFrameID
        		|| world.getBlockId(x, y, z + 1) == portalFrameID) {
            b1 = 1;
        }
        if (b0 == b1) { //Is it somehow facing both ways? (makes sure intersecting portals won't activate)
        	System.out.println("GOT HERE 3!");
        	System.out.println(b0 + "  " + b1);
        	System.out.println(portalFrameID + "  " + XARBlocks.blockCrystal.blockID);
        	System.out.println("- Z-axis: " + world.getBlockId(x, y, z - 1));
        	System.out.println("+ Z-axis: " + world.getBlockId(x, y, z + 1));
        	System.out.println("- X-axis: " + world.getBlockId(x - 1, y, z));
        	System.out.println("+ X-axis: " + world.getBlockId(x + 1, y, z));
            return false;
        }
        else {
            if (world.getBlockId(x - b0, y, z - b1) == 0) {
                x -= b0;
                z -= b1;
            }

            int h; //the horizontal space (two-block width) for the portal 'doorway'
            int v; //the vertical space (three-block height) for the portal 'doorway'

            for (h = -1; h <= 2; ++h) {
                for (v = -1; v <= 3; ++v) {
                    boolean flag = (h == -1 || h == 2 || v == -1 || v == 3); //are we at one of the borders of the portal?
                    
                    //are we within the bounds of the portal frame?
                    if (h != -1 && h != 2 || v != -1 && v != 3) {
                        int j1 = world.getBlockId(x + b0 * h, y + v, z + b1 * h);
                    	System.out.println((x + b0 * h) + ", " + (y + v) + ", " +  (z + b1 * h));
                    	System.out.println(j1 + "   " + portalFrameID);

                        if (flag) {
                            if (j1 != portalFrameID) {
                            	System.out.println("GOT HERE 2!");
                            	System.out.println(b0 + "  " + b1);
                                return false;
                            }
                        }
                    }
                }
            }
            
            //fills the portal doorway with portal blocks (2x3 area)
            for (h = 0; h < 2; ++h) {
                for (v = 0; v < 3; ++v) {
                    world.setBlock(x + b0 * h, y + v, z + b1 * h, this.blockID, 0, 2);
                }
            }
            return true;
        }
    }
    
    *//** Determines if the portal frame has been broken, and if so, removes all of the portal blocks *//*
    //TODO should recreate/re-render the rift that was used to create the portal
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID) {
        byte b0 = 0; //facing east/west
        byte b1 = 1; //facing north/south
        
        //determines if the portal is facing east/west
        if (world.getBlockId(x - 1, y, z) == this.blockID || world.getBlockId(x + 1, y, z) == this.blockID) {
            b0 = 1;
            b1 = 0;
        }

        int i1;
        
        //decrements i1 to find the bottom-most portal block?
        for (i1 = y; world.getBlockId(x, i1 - 1, z) == this.blockID; --i1) {
            ;
        }

        if (world.getBlockId(x, i1 - 1, z) != portalFrameID) {
            world.setBlockToAir(x, y, z);
        }
        else {
            int j1;

            for (j1 = 1; j1 < 4 && world.getBlockId(x, i1 + j1, z) == this.blockID; ++j1) {
                ;
            }

            if (j1 == 3 && world.getBlockId(x, i1 + j1, z) == portalFrameID) {
                boolean flag = world.getBlockId(x - 1, y, z) == this.blockID || world.getBlockId(x + 1, y, z) == this.blockID;
                boolean flag1 = world.getBlockId(x, y, z - 1) == this.blockID || world.getBlockId(x, y, z + 1) == this.blockID;

                if (flag && flag1) {
                    world.setBlockToAir(x, y, z);
                }
                else {
                    if ((world.getBlockId(x + b0, y, z + b1) != portalFrameID || world.getBlockId(x - b0, y, z - b1) != this.blockID)
                    		&& (world.getBlockId(x - b0, y, z - b1) != portalFrameID || world.getBlockId(x + b0, y, z + b1) != this.blockID)) {
                        world.setBlockToAir(x, y, z);
                    }
                }
            }
            else {
                world.setBlockToAir(x, y, z);
            }
        }
    }*/

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

