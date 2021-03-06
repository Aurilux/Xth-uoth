package aurilux.xar.entity.monster;

import aurilux.ardentcore.world.MutableExplosion;
import aurilux.xar.entity.ai.EntityAIBlighterSwell;
import aurilux.xar.init.ModMisc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.common.config.ConfigItems;

/**
 * Lore-wise the predecessor to the creeper, the Blighter functions in mostly the same way, though it isn't as
 * destructive to the environment. Logically it would have been simpler to extend the EntityCreeper class, however most
 * of its members are private and thus, inaccessible.
 *
 * The only purpose to implementing the 'ITaintedMob' interface is so that taint damage heals the mob instead of
 * damaging it
 */
public class EntityBlighter extends EntityMob implements ITaintedMob {
	/** Time when this blighter was last in an active state */
    private int lastActiveTime;
    /** The amount of time since the blighter was close enough to the player to ignite */
    private int timeSinceIgnited;
    /** Maximum time before a blighter will explode */
    private int fuseTime = 30;
    /** Explosion radius for this blighter */
    private int explosionRadius = 5;
    /** The explosion object */
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
        	.addPotionEffect(new PotionEffect(PotionFluxTaint.instance.id, 200))
        	.setDamage(2F, .6F)
        	.setForce(.50F);
	}

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return ModMisc.ABERRATION;
    }

    /**
     * @return true if the newer Entity AI code should be run. You really shouldn't ever return false
     */
    @Override
    public boolean isAIEnabled() { return true; }

    /** The number of iterations PathFinder.getSafePoint will execute before giving up. */
    public int getMaxSafePointTries() {
        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    /** Called when the mob is falling. Calculates and applies fall damage. */
    protected void fall(float distance) {
        super.fall(distance);
        timeSinceIgnited = (int)((float)timeSinceIgnited + distance * 1.5F);

        if (timeSinceIgnited > fuseTime - 5) {
            timeSinceIgnited = fuseTime - 5;
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) -1);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Fuse")) this.fuseTime = compound.getShort("Fuse");
        if (compound.hasKey("ExplosionRadius")) this.explosionRadius = compound.getByte("ExplosionRadius");
    }

    /** Updates the entity's position/logic/behavior. */
    public void onUpdate() {
        if (this.isEntityAlive()) {
            lastActiveTime = timeSinceIgnited;
            int state = getBlighterState();
            
            if (state > 0 && timeSinceIgnited == 0) this.playSound("creeper.primed", 1.0F, 0.5F);

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

    /**
     * The sound the entity makes when it gets hurt/damaged
     * @return the string of the sound file
     */
    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    /**
     * The sound the entity makes when it dies
     * @return the string of the sound file
     */
    protected String getDeathSound() {
        return "mob.creeper.death";
    }

    public boolean attackEntityAsMob(Entity entity) { return true; }

    protected Item getDropItem()
    {
        return ConfigItems.itemEssence;
    }

    /** Returns the intensity of the creeper's flash when it is ignited. */
    @SideOnly(Side.CLIENT)
    public float getBlighterFlashIntensity(float partialTick) {
        return ((float)lastActiveTime + (float)(timeSinceIgnited - lastActiveTime) * partialTick) / (float)(fuseTime - 2);
    }

    /** Returns the current state of creeper, -1 is idle, 1 is 'in fuse' */
    public int getBlighterState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    /** Sets the state of creeper, -1 to idle and 1 to be 'in fuse' */
    public void setBlighterState(int state) {
        this.dataWatcher.updateObject(16, (byte)state);
    }
}