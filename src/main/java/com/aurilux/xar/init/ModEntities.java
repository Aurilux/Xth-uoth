package com.aurilux.xar.init;

import com.aurilux.xar.Xthuoth;
import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.entity.monster.EntityBlighter;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities {
	private static int entityId = 0;
	public static int maxAberrationId = 0;

	public static void init() {
		//aberration mobs
		EntityRegistry.registerModEntity(EntityBlighter.class, "Blighter", entityId++, Xthuoth.instance, 64, 3, true);
		XARItems.entityEgg.addEntityEgg("Blighter", 0x7043D9, 0);
		maxAberrationId = entityId;

		//other mobs
		//none yet

		//other entities
		EntityRegistry.registerModEntity(EntityRift.class, "Rift", entityId++, Xthuoth.instance, 160, 3, false);
		EntityRegistry.registerModEntity(EntityRiftCatalyst.class, "Rift Catalyst", entityId++, Xthuoth.instance, 64, 10, true);
	}

	/**
	 * Registering mod entities requires these parameters, as shown in the example:
	 * EntityRegistry.registerModEntity(Entity.class, "EntityName", entityId, trackingRange, updateFrequency);
	 *
	 * Entity.class = the class of the entity
	 * EntityName = the name of the entity
	 * entityId = the mod-specific id of the entity. Each mod can begin it's entity id's at 0 because they are mod
	 *     independent.
	 * trackingRange =
	 * updateFrequency =
	 */
}