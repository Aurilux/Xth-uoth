package com.aurilux.xar.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.aurilux.xar.lib.Potions;

public class PotionEffectHandler {
	
	@ForgeSubscribe
	public void onEntityUpdate(LivingUpdateEvent e) {
		//For poison immunity poison
		EntityLivingBase entity = e.entityLiving;
		if (entity.isPotionActive(Potions.poisonImmunity) && entity.isPotionActive(Potion.poison)) {
			entity.removePotionEffect(Potion.poison.getId());
		}
	}
	
	@ForgeSubscribe
	public void onOverlayRender(RenderGameOverlayEvent event) {
		//For lifesight potion?
	}
}
