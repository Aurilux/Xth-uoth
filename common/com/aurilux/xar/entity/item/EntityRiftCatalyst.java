package com.aurilux.xar.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.lib.XARBlocks;
import com.aurilux.xar.lib.XARItems;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.config.ConfigBlocks;

public class EntityRiftCatalyst extends EntityThrowable {
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
			Entity entityHit = object.entityHit;
            TileEntity te = worldObj.getTileEntity(object.blockX, object.blockY, object.blockZ);
			if (entityHit != null) {
                entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
	        }
            else if (te != null && te instanceof INode) {
                if (((INode) te).getNodeType() == NodeType.TAINTED) {
                    worldObj.setBlockToAir(object.blockX, object.blockY, object.blockZ);
                    EntityRift newRift = new EntityRift(this.worldObj, te.xCoord, te.yCoord, te.zCoord);
                    this.worldObj.spawnEntityInWorld(newRift);
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