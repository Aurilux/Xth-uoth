package com.aurilux.xar.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.aurilux.xar.entity.ai.EntityAIBlighterSwell;
import com.aurilux.xar.lib.Misc;
import com.aurilux.xar.world.MutableExplosion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBlighter extends EntityMob {
	//Time when this creeper was last in an active state
    private int lastActiveTime;
    //The amount of time since the blighter was close enough to the player to ignite
    private int timeSinceIgnited;
    //Maximum time before a blighter will explode
    private int fuseTime = 30;
    // Explosion radius for this blighter
    private int explosionRadius = 5;
    //The explosion object
    private MutableExplosion exp;
    
    public EntityBlighter(World world) {
        super(world);
        //AI tasks
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIBlighterSwell(this));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        
        //Explosion info
        exp = new MutableExplosion(world, this)
        	.setPotionEffect(new PotionEffect(Potion.wither.id, 200))
        	.setDamage(true, 2)
        	.setForce(true, .25F);
	}

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return Misc.ABERRATION;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
    public boolean isAIEnabled() { return true; }

    /**
     * The number of iterations PathFinder.getSafePoint will execute before giving up.
     */
    public int getMaxSafePointTries() {
        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float distance) {
        super.fall(distance);
        timeSinceIgnited = (int)((float)timeSinceIgnited + distance * 1.5F);

        if (timeSinceIgnited > fuseTime - 5) {
            timeSinceIgnited = fuseTime - 5;
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) -1));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Fuse")) this.fuseTime = compound.getShort("Fuse");
        if (compound.hasKey("ExplosionRadius")) this.explosionRadius = compound.getByte("ExplosionRadius");
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        if (this.isEntityAlive()) {
            lastActiveTime = timeSinceIgnited;
            int state = getBlighterState();
            
            if (state > 0 && timeSinceIgnited == 0) this.playSound("random.fuse", 1.0F, 0.5F);

            timeSinceIgnited += state;

            if (timeSinceIgnited < 0) timeSinceIgnited = 0;

            if (timeSinceIgnited >= fuseTime) {
                timeSinceIgnited = fuseTime;

                if (!this.worldObj.isRemote) {
                    exp.createExplosion(this.posX, this.posY, this.posZ, (float)explosionRadius);
                    this.setDead();
                }
            }
        }

        super.onUpdate();
    }
    
    protected String getHurtSound() {
        return "mob.creeper.say";
    }
    
    protected String getDeathSound() {
        return "mob.creeper.death";
    }
    
    public void onDeath(DamageSource source) {
        super.onDeath(source);
    }

    public boolean attackEntityAsMob(Entity entity) { return true; }

    @SideOnly(Side.CLIENT)
    /**
     * Returns the intensity of the creeper's flash when it is ignited.
     */
    public float getBlighterFlashIntensity(float partialTick) {
        return ((float)lastActiveTime + (float)(timeSinceIgnited - lastActiveTime) * partialTick) / (float)(fuseTime - 2);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId() {
    	//TODO change this
        return Item.gunpowder.itemID;
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getBlighterState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setBlighterState(int state) {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)state));
    }
}