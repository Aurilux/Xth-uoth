package com.aurilux.xar.lib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;

import com.aurilux.xar.Xthuoth_ModBase;
import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.entity.monster.EntityBlighter;

import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities {
    public static int blighterID = 101;
    public static int riftID = 102;
    public static int catalystID = 103;

	public static void init() {
		//Mobs
		addMobEntity(EntityBlighter.class, "Blighter", blighterID, 80, 10, false, 0x7043D9, 0);
		
		//Other Entities
		addEntity(EntityRift.class, "Rift", riftID, 80, 3, false);
		addEntity(EntityRiftCatalyst.class, "Rift Catalyst", catalystID, 80, 3, true);
	}
    
    //PRIVATE HELPERS
    private static void addEntity(Class<? extends Entity> cl, String name, int entityId, int trackingRange, int updateFrequency,
    		boolean sendsVelocityUpdates) {
    	EntityRegistry.registerModEntity(cl, name, entityId, Xthuoth_ModBase.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    @SuppressWarnings("unchecked")
    private static void addMobEntity(Class<? extends EntityLiving> cl, String name, int entityId, int trackingRange, int updateFrequency,
    		boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
    	addEntity(cl, name, trackingRange, entityId, updateFrequency, sendsVelocityUpdates);
    	//registering the mob egg
    	int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityList.IDtoClassMapping.put(id, cl);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
    }
}