package com.aurilux.xar.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityRift extends Entity {
    //TODO Why can't I right-click on this with my handler?
    //TODO make this teleport the player to Xthuoth when he collides
	public EntityRift(World world) {
		super(world);
		this.preventEntitySpawning = true;
	}

    public EntityRift(World world, double x, double y, double z) {
		this(world);
        this.setPosition(x + .5, y, z + .5);
	}

    @Override
    protected void entityInit() {
    }
	
	@Override
    public void onCollideWithPlayer(EntityPlayer player) {
		//100 tick duration is five seconds. There are 20 ticks a second
		PotionEffect effect = new PotionEffect(Potion.confusion.id, 100);
		player.addPotionEffect(effect);
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