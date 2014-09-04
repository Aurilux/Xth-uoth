package com.aurilux.xar.handler;

import com.aurilux.xar.lib.XARPotions;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class PotionEffectHandler {
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent e) {
		//For poison immunity poison
		EntityLivingBase entity = e.entityLiving;
		if (entity.isPotionActive(XARPotions.poisonImmunity) && entity.isPotionActive(Potion.poison)) {
			entity.removePotionEffect(Potion.poison.getId());
		}
	}

	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent event) {
		//For lifesight potion?
	}
}
