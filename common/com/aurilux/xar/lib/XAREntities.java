package com.aurilux.xar.lib;

import com.aurilux.xar.Xthuoth;
import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.entity.monster.EntityBlighter;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;

//import net.minecraft.entity.EntityEggInfo;

public class XAREntities {
	private static int nextEntityID = 100;

    public static int blighterID;
    public static int riftID;
    public static int catalystID;

	public static void init() {
		blighterID = nextEntityID++;
		riftID =     nextEntityID++;
		catalystID = nextEntityID++;

		//Mobs
		addMobEntity(EntityBlighter.class,  "Blighter",      blighterID, 80,  3,  true, 0x7043D9, 0);

		//Other Entities
		addEntity(EntityRift.class,         "Rift",          riftID,     160, 3,  false);
		addEntity(EntityRiftCatalyst.class, "Rift Catalyst", catalystID, 64,  10, true);
	}

    //PRIVATE HELPERS
    private static void addEntity(Class<? extends Entity> cl, String name, int entityId, int viewingDist, int updateFrequency,
    		boolean sendsVelocityUpdates) {
    	EntityRegistry.registerModEntity(cl, name, entityId, Xthuoth.instance, viewingDist, updateFrequency, sendsVelocityUpdates);
    }

    @SuppressWarnings("unchecked")
    private static void addMobEntity(Class<? extends EntityLiving> cl, String name, int entityId, int viewingDist, int updateFrequency,
    		boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
    	addEntity(cl, name, viewingDist, entityId, updateFrequency, sendsVelocityUpdates);
    	//registering the mob egg
    	int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityList.IDtoClassMapping.put(id, cl);
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, primaryColor, secondaryColor));
    }
}