package aurilux.xar.world;

import aurilux.xar.entity.EntityRift;
import aurilux.xar.handler.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TeleporterXthuoth extends Teleporter {
	/** World server instance */
	private final WorldServer server;
	
	/** Random number generator */
	private final Random rand;
	
    /** The maximum distance between rifts */
    private final int searchDistance = 64;
    
	/** Stores successful portal placement locations for rapid lookup. */
	private final LongHashMap destinationCoordinateCache = new LongHashMap();
	
	/** A list of valid keys for the destinationCoordinateCache. These are based on the X & Z of the players initial location.*/
	private final List<Long> destinationCoordinateKeys = new ArrayList<Long>();

	public TeleporterXthuoth(WorldServer ws) {
		super(ws);
		server = ws;
		rand = new Random(server.getSeed());
	}

	/** Place an entity in a nearby rift, creating one if necessary.*/
	@Override
	public void placeInPortal(Entity entity, double xPos, double yPos, double zPos, float yRot) {
		int dimID = this.server.provider.dimensionId;
        World worldRef = this.server.provider.worldObj == this.server ? this.server : this.server.provider.worldObj;

        //is the target dimension either the Overworld or Xth'uoth?
		if (dimID == 0 || dimID == ConfigHandler.DIM_ID) {
            int portalPosX = 0;
            int portalPosY = 0;
            int portalPosZ = 0;
            int entityPosX = MathHelper.floor_double(entity.posX);
            int entityPosZ = MathHelper.floor_double(entity.posZ);
            long chunkCoord = ChunkCoordIntPair.chunkXZ2Int(entityPosX, entityPosZ);

            //If the coordinates have been used recently, use those...
            if (this.destinationCoordinateCache.containsItem(chunkCoord)) {
                PortalPosition pp = (PortalPosition)this.destinationCoordinateCache.getValueByKey(chunkCoord);
                portalPosX = pp.posX;
                portalPosY = pp.posY;
                portalPosZ = pp.posZ;
                pp.lastUpdateTime = worldRef.getTotalWorldTime();
            }
            else {
                //...otherwise find the closest rift and spawn there
                ArrayList<?> riftLocations = new ArrayList<Object>();
                AxisAlignedBB searchArea = AxisAlignedBB.getBoundingBox(entityPosX - searchDistance, 0, entityPosZ - searchDistance,
                        entityPosX + searchDistance, worldRef.getActualHeight(), entityPosZ + searchDistance);

                int i = MathHelper.floor_double((searchArea.minX - World.MAX_ENTITY_RADIUS) / 16.0D);
                int j = MathHelper.floor_double((searchArea.maxX + World.MAX_ENTITY_RADIUS) / 16.0D);
                int k = MathHelper.floor_double((searchArea.minZ - World.MAX_ENTITY_RADIUS) / 16.0D);
                int l = MathHelper.floor_double((searchArea.maxZ + World.MAX_ENTITY_RADIUS) / 16.0D);

                for (int i1 = i; i1 <= j; ++i1) {
                    for (int j1 = k; j1 <= l; ++j1) {
                        worldRef.getChunkFromChunkCoords(i1, j1).getEntitiesOfTypeWithinAAAB(EntityRift.class,
                            searchArea, riftLocations, null);
                    }
                }

                //If there are rifts within the search area, choose one randomly...
                if (riftLocations.size() > 0) {
                    EntityRift foundRift = (EntityRift)riftLocations.get(rand.nextInt(riftLocations.size()));
                    portalPosX = MathHelper.floor_double(foundRift.posX);
                    portalPosY = MathHelper.floor_double(foundRift.posY);
                    portalPosZ = MathHelper.floor_double(foundRift.posZ);
                }
                else { //...otherwise spawn a new rift
                    int chunkSize = 16;
                    //This line of code is known as a label. It is an easier way to break out of multiple nested loops.
                    portalLoop:
                    for (int xLoc = entityPosX - chunkSize; xLoc <= entityPosX + chunkSize; ++xLoc) {
                        for (int zLoc = entityPosZ - chunkSize; zLoc <= entityPosZ + chunkSize; ++zLoc) {
                            for (int yLoc = worldRef.getActualHeight() - 1; yLoc >= 0; --yLoc) {
                                //If the current location is an air block, move down until we find a solid block
                                //then spawn a portal in the space above it
                                if (worldRef.isAirBlock(xLoc, yLoc, zLoc)) {
                                    while (yLoc > 0 && worldRef.isAirBlock(xLoc, yLoc - 1, zLoc)) {
                                        --yLoc;
                                    }
                                    if (worldRef.isAirBlock(xLoc, yLoc, zLoc) && worldRef.isAirBlock(xLoc, yLoc + 1, zLoc)) {
                                        portalPosX = xLoc;
                                        portalPosY = yLoc;
                                        portalPosZ = zLoc;
                                        
                                        worldRef.spawnEntityInWorld(new EntityRift(worldRef, portalPosX, portalPosY, portalPosZ));
                                        break portalLoop; //break out of the entire label defined above
                                    }
                                }
                            }
                        }
                    }
                }
            }
            destinationCoordinateCache.add(chunkCoord, new PortalPosition(portalPosX, portalPosY, portalPosZ, worldRef.getTotalWorldTime()));
            destinationCoordinateKeys.add(Long.valueOf(chunkCoord));
            entity.setLocationAndAngles(portalPosX + .5D, portalPosY + .5D, portalPosZ + .5D, entity.rotationYaw, entity.rotationPitch);
		}
	}

    public void removeStalePortalLocations(long par1) {
        if (par1 % 100L == 0L) {
            Iterator<Long> iterator = this.destinationCoordinateKeys.iterator();
            long j = par1 - 600L;

            while (iterator.hasNext()) {
                Long olong = (Long)iterator.next();
                Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition)this.destinationCoordinateCache.getValueByKey(olong.longValue());

                if (portalposition == null || portalposition.lastUpdateTime < j) {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(olong.longValue());
                }
            }
        }
    }
}