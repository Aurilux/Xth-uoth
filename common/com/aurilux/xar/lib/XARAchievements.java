package com.aurilux.xar.lib;

import com.aurilux.xar.handlers.AchievementListener;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;

public class XARAchievements {
	private static int nextAchID = 30;
	private static AchievementPage page;
	private static Achievement[] achievements;
	private static AchievementInfo[] achievementInfo = {
		new AchievementInfo(0, 0, new ItemStack(XARItems.crystalShard), false, -1),
		new AchievementInfo(3, 0, new ItemStack(XARItems.riftCatalyst), false, 0),
		new AchievementInfo(3, -2, new ItemStack(XARItems.wightbulbPod), false, 1)
	};
	
	public static void init(Configuration config) {
		achievements = new Achievement[achievementInfo.length];
		Achievement achievement;
		Achievement parent;
		int achievementID;
		String achievementKey;
		AchievementInfo info;
		for (int i = 0; i < achievementInfo.length; i++) {
			info = achievementInfo[i];
			achievementKey = "achievement_" + i;
			achievementID = config.get("Achievements", achievementKey, nextAchID++).getInt();
			parent = info.parent > -1 ? achievements[info.parent] : null;
			achievement = new Achievement(achievementKey, achievementKey, info.xPos, info.yPos, info.icon, parent).registerStat();
			if (info.isSpecial)
				achievement = achievement.setSpecial();
			achievements[i] = achievement;
		}
		
		page = new AchievementPage("Xth'uoth", achievements);
		AchievementPage.registerAchievementPage(page);
		
		//Register achievement completion event listener
		MinecraftForge.EVENT_BUS.register(new AchievementListener());
	}

	public static Achievement getAchievement(int index) {
		return achievements[index];
	}
	
	//Just a class to easily store info for achievement registration
	private static class AchievementInfo {
		
		public int xPos = 0;
		public int yPos = 0;
		public ItemStack icon;
		public boolean isSpecial = false;
		public int parent;
		
		public AchievementInfo(int x, int y, ItemStack i, boolean s, int p) {
			xPos = x;
			yPos = y;
			icon = i;
			isSpecial = s;
			parent = p;
		}
	}
}
