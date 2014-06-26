package com.aurilux.xar.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARWorldgen;

public class TeleporterXthuoth extends Teleporter {
	//FIXME Need to ensure this works as well
	/** World server instance */
	private final WorldServer worldServerInstance;
	/** Random number generator */
	private final Random rand;
	/** The block ID of the portal block */
	private final Block portalBlockID = XARBlocks.portal;
	/** The block ID of the block that makes up the portal's frame */
	private final Block portalFrameID = XARBlocks.blockCrystal;
	/** Stores successful portal placement locations for rapid lookup. */
	private final LongHashMap destinationCoordinateCache = new LongHashMap();
	/** A list of valid keys for the destinationCoordainteCache. These are based on the X & Z of the players initial location.*/
	@SuppressWarnings("rawtypes")
	private final List destinationCoordinateKeys = new ArrayList();

	public TeleporterXthuoth(WorldServer ws) {
		super(ws);
		worldServerInstance = ws;
		rand = new Random(worldServerInstance.getSeed());
	}

	/** Place an entity in a nearby portal, creating one if necessary.*/
	@Override
	public void placeInPortal(Entity entity, double xPos, double yPos, double zPos, float yRot) {
		//check if the target dimension is not the End. However, the dimension is changed before the entity is put into it
		//should never get called from the nether as there are no rifts to make a portal on
		int dimID = this.worldServerInstance.provider.dimensionId;
		//is the target dimension either the Overworld or Xth'uoth?
		if (dimID == 0 || dimID == XARWorldgen.DIM_ID) {
			//try placing the entity in an existing portal. If an appropriate portal doesn't already exist, make one and then try again
			if (!placeInExistingPortal(entity, xPos, yPos, zPos, yRot)) {
				makePortal(entity);
				placeInExistingPortal(entity, xPos, yPos, zPos, yRot);
			}
		}
		/* This has been commented out because it is not used in my mod. However I left it here because I can't comment the base code for the
		 * teleporter and thought other people would find it useful
		 * 
		 * The base Teleporter class is used by both the nether and end portals
		 * 
		 * make the obsidian portal platform the player starts on when they spawn in the end 
		 * else { //the dimension is the end
			//rounds the entity's position to whole numbers
			int i = MathHelper.floor_double(entity.posX);
			int j = MathHelper.floor_double(entity.posY) - 1;
			int k = MathHelper.floor_double(entity.posZ);
			byte b0 = 1; //facing east/west
			byte b1 = 0; //facing north/south
			//creates a 5x5 platform out of the portalFrame block (which, although I initially changed it for my mod, would be obisidian
			for (int l = -2; l <= 2; ++l) {
				for (int i1 = -2; i1 <= 2; ++i1) {
					for (int j1 = -1; j1 < 3; ++j1) {
						//the following calculations are used to make the platform and ensure the three blocks height above the platform is clear
						int k1 = i + i1 * b0 + l * b1;
						int l1 = j + j1;
						int i2 = k + i1 * b1 - l * b0;
						boolean flag = j1 < 0;
						//0 is the ID for an air block
						this.worldServerInstance.setBlock(k1, l1, i2, flag ? portalFrameID : 0);
					}
				}
			}
			entity.setLocationAndAngles(i, j, k, entity.rotationYaw, 0.0F);
			//sets the entity's motion to 0. Most likely what the Portal Gun mod uses to maintain entity motion when going through portals
			entity.motionX = entity.motionY = entity.motionZ = 0.0D;
		}*/
	}

    /** Place an entity in a nearby portal which already exists. */
    @SuppressWarnings("unchecked")
	public boolean placeInExistingPortal(Entity entity, double xPos, double yPos, double zPos, float yRot) {
        short maxHeight = 128;
        double d3 = -1.0D;
        int portalPosX = 0;
        int portalPosY = 0;
        int portalPosZ = 0;
        int entityPosX = MathHelper.floor_double(entity.posX);
        int entityPosZ = MathHelper.floor_double(entity.posZ);
        long chunkCoord = ChunkCoordIntPair.chunkXZ2Int(entityPosX, entityPosZ);
        boolean flag = true;
        double yPosMod;
        int x;

        if (this.destinationCoordinateCache.containsItem(chunkCoord)) {
            PortalPosition portalposition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(chunkCoord);
            d3 = 0.0D;
            portalPosX = portalposition.posX;
            portalPosY = portalposition.posY;
            portalPosZ = portalposition.posZ;
            portalposition.lastUpdateTime = worldServerInstance.getTotalWorldTime();
            flag = false;
        }
        else {
        	//find the bottom-most portal block of the portal
            for (x = entityPosX - maxHeight; x <= entityPosX + maxHeight; x++) {
                double xPosMod = (double)x + 0.5D - entity.posX;

                for (int z = entityPosZ - maxHeight; z <= entityPosZ + maxHeight; z++) {
                    double zPosMod = (double)z + 0.5D - entity.posZ;

                    for (int y = worldServerInstance.getActualHeight() - 1; y >= 0; y--) {
                        if (worldServerInstance.getBlock(x, y, z) == portalBlockID) {
                            while (worldServerInstance.getBlock(x, y - 1, z) == portalBlockID) {
                                y--;
                            }

                            yPosMod = (double)y + 0.5D - entity.posY;
                            double d7 = xPosMod * xPosMod + yPosMod * yPosMod + zPosMod * zPosMod;

                            if (d3 < 0.0D || d7 < d3) {
                                d3 = d7;
                                portalPosX = x;
                                portalPosY = y;
                                portalPosZ = z;
                            }
                        }
                    }
                }
            }
        }

        if (d3 >= 0.0D) {
            if (flag) {
                destinationCoordinateCache.add(chunkCoord, new PortalPosition(portalPosX, portalPosY, portalPosZ, worldServerInstance.getTotalWorldTime()));
                destinationCoordinateKeys.add(Long.valueOf(chunkCoord));
            }

            double d8 = (double)portalPosX + 0.5D;
            double d9 = (double)portalPosY + 0.5D;
            yPosMod = (double)portalPosZ + 0.5D;
            int j2 = -1;

            if (worldServerInstance.getBlock(portalPosX - 1, portalPosY, portalPosZ) == portalBlockID) {
                j2 = 2;
            }

            if (worldServerInstance.getBlock(portalPosX + 1, portalPosY, portalPosZ) == portalBlockID) {
                j2 = 0;
            }

            if (worldServerInstance.getBlock(portalPosX, portalPosY, portalPosZ - 1) == portalBlockID) {
                j2 = 3;
            }

            if (worldServerInstance.getBlock(portalPosX, portalPosY, portalPosZ + 1) == portalBlockID) {
                j2 = 1;
            }

            int teleportDirection = entity.getTeleportDirection();

            if (j2 > -1) {
                int l2 = Direction.rotateLeft[j2];
                int i3 = Direction.offsetX[j2];
                int j3 = Direction.offsetZ[j2];
                int k3 = Direction.offsetX[l2];
                int l3 = Direction.offsetZ[l2];
                boolean flag1 = !worldServerInstance.isAirBlock(portalPosX + i3 + k3, portalPosY, portalPosZ + j3 + l3) || !worldServerInstance.isAirBlock(portalPosX + i3 + k3, portalPosY + 1, portalPosZ + j3 + l3);
                boolean flag2 = !worldServerInstance.isAirBlock(portalPosX + i3, portalPosY, portalPosZ + j3) || !worldServerInstance.isAirBlock(portalPosX + i3, portalPosY + 1, portalPosZ + j3);

                if (flag1 && flag2) {
                    j2 = Direction.rotateOpposite[j2];
                    l2 = Direction.rotateOpposite[l2];
                    i3 = Direction.offsetX[j2];
                    j3 = Direction.offsetZ[j2];
                    k3 = Direction.offsetX[l2];
                    l3 = Direction.offsetZ[l2];
                    x = portalPosX - k3;
                    d8 -= (double)k3;
                    int i4 = portalPosZ - l3;
                    yPosMod -= (double)l3;
                    flag1 = !worldServerInstance.isAirBlock(x + i3 + k3, portalPosY, i4 + j3 + l3) || !worldServerInstance.isAirBlock(x + i3 + k3, portalPosY + 1, i4 + j3 + l3);
                    flag2 = !worldServerInstance.isAirBlock(x + i3, portalPosY, i4 + j3) || !worldServerInstance.isAirBlock(x + i3, portalPosY + 1, i4 + j3);
                }

                float f1 = 0.5F;
                float f2 = 0.5F;

                if (!flag1 && flag2) {
                    f1 = 1.0F;
                }
                else if (flag1 && !flag2) {
                    f1 = 0.0F;
                }
                else if (flag1 && flag2) {
                    f2 = 0.0F;
                }

                d8 += (double)((float)k3 * f1 + f2 * (float)i3);
                yPosMod += (double)((float)l3 * f1 + f2 * (float)j3);
                float f3 = 0.0F;
                float f4 = 0.0F;
                float f5 = 0.0F;
                float f6 = 0.0F;

                if (j2 == teleportDirection) {
                    f3 = 1.0F;
                    f4 = 1.0F;
                }
                else if (j2 == Direction.rotateOpposite[teleportDirection]) {
                    f3 = -1.0F;
                    f4 = -1.0F;
                }
                else if (j2 == Direction.rotateRight[teleportDirection]) {
                    f5 = 1.0F;
                    f6 = -1.0F;
                }
                else {
                    f5 = -1.0F;
                    f6 = 1.0F;
                }

                double motionX = entity.motionX;
                double motionZ = entity.motionZ;
                entity.motionX = motionX * (double)f3 + motionZ * (double)f6;
                entity.motionZ = motionX * (double)f5 + motionZ * (double)f4;
                entity.rotationYaw = yRot - (float)(teleportDirection * 90) + (float)(j2 * 90);
            }
            else {
                entity.motionX = entity.motionY = entity.motionZ = 0.0D;
            }

            entity.setLocationAndAngles(d8, d9, yPosMod, entity.rotationYaw, entity.rotationPitch);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean makePortal(Entity par1Entity)
    {
        byte b0 = 16;
        double d0 = -1.0D;
        int i = MathHelper.floor_double(par1Entity.posX);
        int j = MathHelper.floor_double(par1Entity.posY);
        int k = MathHelper.floor_double(par1Entity.posZ);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = this.rand.nextInt(4);
        int i2;
        double d1;
        double d2;
        int j2;
        int k2;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        double d3;
        double d4;

        for (i2 = i - b0; i2 <= i + b0; ++i2)
        {
            d1 = (double)i2 + 0.5D - par1Entity.posX;

            for (j2 = k - b0; j2 <= k + b0; ++j2)
            {
                d2 = (double)j2 + 0.5D - par1Entity.posZ;
                label274:

                for (k2 = this.worldServerInstance.getActualHeight() - 1; k2 >= 0; --k2)
                {
                    if (this.worldServerInstance.isAirBlock(i2, k2, j2))
                    {
                        while (k2 > 0 && this.worldServerInstance.isAirBlock(i2, k2 - 1, j2))
                        {
                            --k2;
                        }

                        for (i3 = l1; i3 < l1 + 4; ++i3)
                        {
                            l2 = i3 % 2;
                            k3 = 1 - l2;

                            if (i3 % 4 >= 2)
                            {
                                l2 = -l2;
                                k3 = -k3;
                            }

                            for (j3 = 0; j3 < 3; ++j3)
                            {
                                for (i4 = 0; i4 < 4; ++i4)
                                {
                                    for (l3 = -1; l3 < 4; ++l3)
                                    {
                                        k4 = i2 + (i4 - 1) * l2 + j3 * k3;
                                        j4 = k2 + l3;
                                        int l4 = j2 + (i4 - 1) * k3 - j3 * l2;

                                        if (l3 < 0 && !this.worldServerInstance.getBlock(k4, j4, l4).isOpaqueCube() || l3 >= 0 && !this.worldServerInstance.isAirBlock(k4, j4, l4))
                                        {
                                            continue label274;
                                        }
                                    }
                                }
                            }

                            d4 = (double)k2 + 0.5D - par1Entity.posY;
                            d3 = d1 * d1 + d4 * d4 + d2 * d2;

                            if (d0 < 0.0D || d3 < d0)
                            {
                                d0 = d3;
                                l = i2;
                                i1 = k2;
                                j1 = j2;
                                k1 = i3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D)
        {
            for (i2 = i - b0; i2 <= i + b0; ++i2)
            {
                d1 = (double)i2 + 0.5D - par1Entity.posX;

                for (j2 = k - b0; j2 <= k + b0; ++j2)
                {
                    d2 = (double)j2 + 0.5D - par1Entity.posZ;
                    label222:

                    for (k2 = this.worldServerInstance.getActualHeight() - 1; k2 >= 0; --k2)
                    {
                        if (this.worldServerInstance.isAirBlock(i2, k2, j2))
                        {
                            while (k2 > 0 && this.worldServerInstance.isAirBlock(i2, k2 - 1, j2))
                            {
                                --k2;
                            }

                            for (i3 = l1; i3 < l1 + 2; ++i3)
                            {
                                l2 = i3 % 2;
                                k3 = 1 - l2;

                                for (j3 = 0; j3 < 4; ++j3)
                                {
                                    for (i4 = -1; i4 < 4; ++i4)
                                    {
                                        l3 = i2 + (j3 - 1) * l2;
                                        k4 = k2 + i4;
                                        j4 = j2 + (j3 - 1) * k3;

                                        if (i4 < 0 && !this.worldServerInstance.getBlock(l3, k4, j4).isOpaqueCube() || i4 >= 0 && !this.worldServerInstance.isAirBlock(l3, k4, j4))
                                        {
                                            continue label222;
                                        }
                                    }
                                }

                                d4 = (double)k2 + 0.5D - par1Entity.posY;
                                d3 = d1 * d1 + d4 * d4 + d2 * d2;

                                if (d0 < 0.0D || d3 < d0)
                                {
                                    d0 = d3;
                                    l = i2;
                                    i1 = k2;
                                    j1 = j2;
                                    k1 = i3 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i5 = l;
        int j5 = i1;
        j2 = j1;
        int k5 = k1 % 2;
        int l5 = 1 - k5;

        if (k1 % 4 >= 2)
        {
            k5 = -k5;
            l5 = -l5;
        }

        boolean flag;

        if (d0 < 0.0D)
        {
            if (i1 < 70)
            {
                i1 = 70;
            }

            if (i1 > this.worldServerInstance.getActualHeight() - 10)
            {
                i1 = this.worldServerInstance.getActualHeight() - 10;
            }

            j5 = i1;

            for (k2 = -1; k2 <= 1; ++k2)
            {
                for (i3 = 1; i3 < 3; ++i3)
                {
                    for (l2 = -1; l2 < 3; ++l2)
                    {
                        k3 = i5 + (i3 - 1) * k5 + k2 * l5;
                        j3 = j5 + l2;
                        i4 = j2 + (i3 - 1) * l5 - k2 * k5;
                        flag = l2 < 0;
                        this.worldServerInstance.setBlock(k3, j3, i4, (Block) (flag ? portalFrameID : 0));
                    }
                }
            }
        }

        for (k2 = 0; k2 < 4; ++k2)
        {
            for (i3 = 0; i3 < 4; ++i3)
            {
                for (l2 = -1; l2 < 4; ++l2)
                {
                    k3 = i5 + (i3 - 1) * k5;
                    j3 = j5 + l2;
                    i4 = j2 + (i3 - 1) * l5;
                    flag = i3 == 0 || i3 == 3 || l2 == -1 || l2 == 3;
                    this.worldServerInstance.setBlock(k3, j3, i4, flag ? portalFrameID : portalBlockID, 0, 2);
                }
            }

            for (i3 = 0; i3 < 4; ++i3)
            {
                for (l2 = -1; l2 < 4; ++l2)
                {
                    k3 = i5 + (i3 - 1) * k5;
                    j3 = j5 + l2;
                    i4 = j2 + (i3 - 1) * l5;
                    this.worldServerInstance.notifyBlocksOfNeighborChange(k3, j3, i4, this.worldServerInstance.getBlock(k3, j3, i4));
                }
            }
        }

        return true;
    }

    /**
     * called periodically to remove out-of-date portal locations from the cache list. Argument par1 is a
     * WorldServer.getTotalWorldTime() value.
     */
    @SuppressWarnings("rawtypes")
	public void removeStalePortalLocations(long par1)
    {
        if (par1 % 100L == 0L)
        {
            Iterator iterator = this.destinationCoordinateKeys.iterator();
            long j = par1 - 600L;

            while (iterator.hasNext())
            {
                Long olong = (Long)iterator.next();
                PortalPosition portalposition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(olong.longValue());

                if (portalposition == null || portalposition.lastUpdateTime < j)
                {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(olong.longValue());
                }
            }
        }
    }
}
