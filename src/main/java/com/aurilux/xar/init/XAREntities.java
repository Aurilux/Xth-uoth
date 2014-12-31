package com.aurilux.xar.init;

import com.aurilux.xar.Xthuoth;
import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.entity.monster.EntityBlighter;
import com.aurilux.xar.handler.ConfigHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class XAREntities {
	public static void init() {
		//mobs
		addMobEntity(EntityBlighter.class,  "Blighter",      ConfigHandler.BLIGHTER_ENTITY_ID, 64,  3,  true, 0x7043D9, 0);

		//other entities
		addEntity(EntityRift.class,         "Rift",          ConfigHandler.RIFT_ENTITY_ID, 160, 3,  false);
		addEntity(EntityRiftCatalyst.class, "Rift Catalyst", ConfigHandler.CATALYST_ENTITY_ID, 64,  10, true);
	}

    //// HELPERS
    private static void addEntity(Class<? extends Entity> cl, String name, int entityId, int trackingRange, int updateFrequency,
    		boolean sendsVelocityUpdates) {
    	EntityRegistry.registerModEntity(cl, name, entityId, Xthuoth.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    @SuppressWarnings("unchecked")
    private static void addMobEntity(Class<? extends EntityLiving> cl, String name, int entityId, int trackingRange, int updateFrequency,
    		boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
        //registering the mob egg
		EntityRegistry.registerGlobalEntityID(cl, name, entityId, primaryColor, secondaryColor);

    	addEntity(cl, name, trackingRange, entityId, updateFrequency, sendsVelocityUpdates);
    }
	//// END HELPERS
}