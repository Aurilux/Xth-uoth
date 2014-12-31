package com.aurilux.xar.entity;

import com.aurilux.xar.handler.ConfigHandler;
import com.aurilux.xar.init.XARWorldgen;
import com.aurilux.xar.world.TeleporterXthuoth;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import thaumcraft.common.blocks.BlockTaintFibres;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import java.util.List;

public class EntityRift extends Entity {
    private int tickCounter = 0;

	public EntityRift(World world) {
		super(world);
		this.preventEntitySpawning = true;
        this.setSize(0.6F, 1.8F);
	}

    @Override
    protected void entityInit() {
    }

    public EntityRift(World world, double x, double y, double z) {
		this(world);
        this.setPosition(x + .5D, y, z + .5D);
	}

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    /**
     * Deliberately left empty because we don't want anything special happening when something collides
     * though I might use this instead of onCollideWithPlayer to allow other entities to join in the fun >:D
     */
    @Override
    public void applyEntityCollision(Entity entity) {}

	@Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if (player.ridingEntity == null && player.riddenByEntity == null && player.timeUntilPortal == 0) {
            if (player instanceof EntityPlayerMP) { // && !this.worldObj.isRemote) {
                //200 tick duration is ten seconds. There are 20 ticks a second
                PotionEffect effect = new PotionEffect(Potion.confusion.id, 200);
                player.addPotionEffect(effect);

                EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
                mpPlayer.timeUntilPortal = 200;
                int targetDimension = 0; // 0 = Overworld dimension ID
                if (mpPlayer.dimension != ConfigHandler.DIM_ID) {
                    targetDimension = ConfigHandler.DIM_ID;
                }
                Teleporter tele = new TeleporterXthuoth(mpPlayer.mcServer.worldServerForDimension(targetDimension));
                mpPlayer.mcServer.getConfigurationManager().transferPlayerToDimension(mpPlayer, targetDimension, tele);
            }
        }
	}

    @Override
    public void onEntityUpdate() {
        //Spread taint just like tainted nodes, but twice as fast
        tickCounter++;
        if (this.dimension == 0 && tickCounter % 25 == 0) {
            int y = 0;
            int x = MathHelper.floor_double(this.posX - .5D) + worldObj.rand.nextInt(8) - worldObj.rand.nextInt(8);
            int z = MathHelper.floor_double(this.posZ) + worldObj.rand.nextInt(8) - worldObj.rand.nextInt(8);
            BiomeGenBase bg = worldObj.getBiomeGenForCoords(x, z);
            if (bg.biomeID != ThaumcraftWorldGenerator.biomeTaint.biomeID) {
                Utils.setBiomeAt(worldObj, x, z, ThaumcraftWorldGenerator.biomeTaint);
            }
            if ((Config.hardNode) && (worldObj.rand.nextBoolean())) {
                x = MathHelper.floor_double(this.posX - .5D) + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5);
                y = MathHelper.floor_double(this.posY) + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5);
                z = MathHelper.floor_double(this.posZ) + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5);
                if (!BlockTaintFibres.spreadFibres(worldObj, x, y, z)) {}
            }
        }
        
        //Spawn a random aberration
        int spawnChance = 4000; //nether portals spawn chance is 2000, though entities seem to update more often than blocks
        if (!worldObj.isRemote && worldObj.provider.isSurfaceWorld() &&
            worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning") &&
            worldObj.rand.nextInt(spawnChance) < worldObj.difficultySetting.getDifficultyId()) {
            //Get the list of aberrations
            List aberrations = XARWorldgen.aberrantBiome.getSpawnableList(EnumCreatureType.monster);
            //Then get a random entity class from that list
            Class entityClass = ((SpawnListEntry) aberrations.get(rand.nextInt(aberrations.size()))).entityClass;
            //Finally get the entity's id to be spawned
            //TODO change getTrackingRange to getModEntityId when the issue is fixed
            int entityId = EntityRegistry.instance().lookupModSpawn(entityClass, false).getTrackingRange();

            Block blockBelow = worldObj.getBlock(MathHelper.floor_double(this.posX - .5D),
                MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ));
            if (posY > 0 && blockBelow.isNormalCube()) {
                Entity entity = ItemMonsterPlacer.spawnCreature(worldObj, entityId, posX + 0.5D, posY + .5D, posZ + 0.5D);
                if (entity != null) {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }
    }


    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight
     * @param distance the distance the player is from the entity
     */
	@Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return distance < d1 * d1;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
    }
}