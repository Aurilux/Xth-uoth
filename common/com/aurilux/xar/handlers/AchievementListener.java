package com.aurilux.xar.handlers;

import com.aurilux.xar.lib.Achievements;
import com.aurilux.xar.lib.Items;
import com.aurilux.xar.lib.WorldGen;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class AchievementListener {
	
	@ForgeSubscribe
	public void chunkEntered(EntityJoinWorldEvent e) {
		//Detects the first time the player enters Xth'uoth
		if (e.entity != null && e.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.entity;
			if (e.world.provider.dimensionId == WorldGen.DIM_ID) {
				player.addStat(Achievements.getAchievement(1), 1);
			}
		}
	}

	@ForgeSubscribe
	public void aberrationSlain(LivingDeathEvent e) {
		//Detects the first aberration slain
		Entity source = e.source.getSourceOfDamage();
		if (e.entity != null && source != null && source instanceof EntityPlayer) {
			((EntityPlayer) source).addStat(Achievements.getAchievement(2), 1);
		}
	}

	@ForgeSubscribe
	public void itemPickup(EntityItemPickupEvent e) {
		//Detects the pickup of a crystal shard
		if (e.item.getEntityItem().getItem().itemID == Items.crystalShard.itemID) {
			e.entityPlayer.addStat(Achievements.getAchievement(0), 1);
		}
	}
}
