package com.aurilux.xar.handlers;

import com.aurilux.xar.lib.XARAchievements;
import com.aurilux.xar.lib.XARItems;
import com.aurilux.xar.lib.XARWorldgen;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class AchievementListener {
	
	@SubscribeEvent
	public void chunkEntered(EntityJoinWorldEvent e) {
		//Detects the first time the player enters Xth'uoth
		if (e.entity != null && e.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.entity;
			if (e.world.provider.dimensionId == XARWorldgen.DIM_ID) {
				player.addStat(XARAchievements.getAchievement(1), 1);
			}
		}
	}

	@SubscribeEvent
	public void aberrationSlain(LivingDeathEvent e) {
		//Detects the first aberration slain
		Entity source = e.source.getSourceOfDamage();
		if (e.entity != null && source != null && source instanceof EntityPlayer) {
			((EntityPlayer) source).addStat(XARAchievements.getAchievement(2), 1);
		}
	}

	@SubscribeEvent
	public void itemPickup(EntityItemPickupEvent e) {
		//Detects the pickup of a crystal shard
		if (e.item.getEntityItem().getItem() == XARItems.crystalShard) {
			e.entityPlayer.addStat(XARAchievements.getAchievement(0), 1);
		}
	}
}
