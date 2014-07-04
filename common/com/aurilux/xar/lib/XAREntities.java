package com.aurilux.xar.lib;

import com.aurilux.xar.Xthuoth;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.common.config.Configuration;

import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.entity.monster.EntityBlighter;

import cpw.mods.fml.common.registry.EntityRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class XAREntities {
	//FIXME maybe better way to register entities?
	private static int nextEntityID = 100;
	
    public static int blighterID;
    public static int riftID;
    public static int catalystID;

	public static void init(Configuration config) {
		blighterID = config.get("Entities", "Blighter ID", nextEntityID++, null).getInt();
		riftID =     config.get("Entities", "Rift ID",     nextEntityID++, null).getInt();
		catalystID = config.get("Entities", "Catalyst ID", nextEntityID++, null).getInt();
		
		//Mobs
		addMobEntity(EntityBlighter.class,  "Blighter",      blighterID, 80,  3,   true, 0x7043D9, 0);
		
		//Other Entities
		addEntity(EntityRift.class, "Rift", riftID, 160, 3, false);
		addEntity(EntityRiftCatalyst.class, "Rift Catalyst", catalystID, 64,  10,  true);
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