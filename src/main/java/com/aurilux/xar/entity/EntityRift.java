package com.aurilux.xar.entity;

import com.aurilux.xar.lib.XARWorldgen;
import com.aurilux.xar.world.TeleporterXthuoth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

public class EntityRift extends Entity {
    //TODO add the ability to randomly spawn aberrations similar to how nether portals can spawn Zombie Pigman
	public EntityRift(World world) {
		super(world);
		this.preventEntitySpawning = true;
        this.setSize(0.6F, 1.8F);
	}

    public EntityRift(World world, double x, double y, double z) {
		this(world);
        this.setPosition(x + .5, y, z + .5);
	}

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    /** Deliberately left empty because we don't want anything special happening when something collides
     *  though I might use this instead of onCollideWithPlayer to allow other entities to join in the fun >:D
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
                if (mpPlayer.dimension != XARWorldgen.DIM_ID) {
                    targetDimension = XARWorldgen.DIM_ID;
                }
                Teleporter tele = new TeleporterXthuoth(mpPlayer.mcServer.worldServerForDimension(targetDimension));
                mpPlayer.mcServer.getConfigurationManager().transferPlayerToDimension(mpPlayer, targetDimension, tele);
            }
        }
	}

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
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
    protected void readEntityFromNBT(NBTTagCompound var1) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1) {
    }
}