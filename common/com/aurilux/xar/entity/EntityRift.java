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

	public EntityRift(World world) {
		super(world);
		this.preventEntitySpawning = true;
	}
	
	public EntityRift(World world, double x, double y, double z) {
		this(world);
        this.setPosition(x + .5, y, z + .5);
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
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	public void applyEntityCollision(Entity entity) {}
    
    @Override
    public boolean isEntityInvulnerable() {
        return true;
    }

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}
}