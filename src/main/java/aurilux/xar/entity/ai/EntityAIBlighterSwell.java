package aurilux.xar.entity.ai;

import aurilux.xar.entity.monster.EntityBlighter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIBlighterSwell extends EntityAIBase {

    /** The creeper that is swelling. */
    EntityBlighter swellingBlighter;
    /** The creeper's attack target. This is used for the changing of the creeper's state. */
    EntityLivingBase blighterAttackTarget;

    public EntityAIBlighterSwell(EntityBlighter blighter)
    {
        swellingBlighter = blighter;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = swellingBlighter.getAttackTarget();
        return swellingBlighter.getBlighterState() > 0 || entitylivingbase != null &&
        		this.swellingBlighter.getDistanceSqToEntity(entitylivingbase) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        swellingBlighter.getNavigator().clearPathEntity();
        blighterAttackTarget = swellingBlighter.getAttackTarget();
    }

    /**
     * Resets the task
     */
    public void resetTask() {
        blighterAttackTarget = null;
    }

    /**
     * Updates the task
     */
    public void updateTask() {
        if (this.blighterAttackTarget == null) {
            this.swellingBlighter.setBlighterState(-1);
        }
        else if (this.swellingBlighter.getDistanceSqToEntity(this.blighterAttackTarget) > 49.0D) {
            this.swellingBlighter.setBlighterState(-1);
        }
        else if (!this.swellingBlighter.getEntitySenses().canSee(this.blighterAttackTarget)) {
            this.swellingBlighter.setBlighterState(-1);
        }
        else {
            this.swellingBlighter.setBlighterState(1);
        }
    }
}