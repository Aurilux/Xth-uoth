package com.aurilux.xar.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARItems;

public class EntityRiftCatalyst extends EntityThrowable {
	//FIXME commented lines and ensure it works appropriately

	public EntityRiftCatalyst(World world) {
		super(world);
	}

	public EntityRiftCatalyst(World world, EntityPlayer player) {
		super(world, player);
	}

    public EntityRiftCatalyst(World world, double xPos, double yPos, double zPos) {
        super(world, xPos, yPos, zPos);
    }

	@Override
	protected void onImpact(MovingObjectPosition object) {
		if (!this.worldObj.isRemote) {
			// if it hits a rift, spawn the portal. If it hits anything else drop it on the ground (will have to be another entity)
			Entity entityHit = object.entityHit;
			if (entityHit != null) {
				//checks if the entity is invulnerable (attackEntityFrom returns false if it is) and if it is a rift
				if (!entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0)
						&& entityHit instanceof EntityRift) {
					//should also destroy the rift when the portal is created
					entityHit.setDead();
					//XARBlocks.portal.tryToCreatePortal(this.worldObj,(int)entityHit.posX, (int)entityHit.posY, (int)entityHit.posZ);
					//this.worldObj.setBlock((int)object.hitVec.xCoord, (int)object.hitVec.yCoord, (int)object.hitVec.zCoord, Block.bedrock.blockID);
				}
	        }
			else {
				ItemStack stack = new ItemStack(XARItems.riftCatalyst, 1, 0);
				EntityItem item = new EntityItem(this.worldObj, object.hitVec.xCoord, object.hitVec.yCoord, object.hitVec.zCoord, stack);
				this.worldObj.spawnEntityInWorld(item);
			}
			this.setDead();
		}
	}
}