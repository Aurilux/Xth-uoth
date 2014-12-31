package com.aurilux.xar.handler;

import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.init.XARAchievements;
import com.aurilux.xar.init.XARItems;
import com.aurilux.xar.init.XARPotions;
import com.aurilux.xar.item.ItemPrysmalArmor;
import com.aurilux.xar.item.ItemVeilShears;
import com.aurilux.xar.util.ArmorSetUtils;
import com.aurilux.xar.util.ReflectionUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.WarpEvents;

public class EntityHandler {
    @SubscribeEvent
    public void itemPickup(EntityItemPickupEvent e) {
        //ACHIEVEMENT
        //Detects the pickup of a crystal shard
        if (e.item.getEntityItem().getItem() == XARItems.prysmalShard) {
            e.entityPlayer.addStat(XARAchievements.getAchievement(0), 1);
        }
    }

    @SubscribeEvent
    public void chunkEntered(EntityJoinWorldEvent e) {
        //ACHIEVEMENT
        //Detects the first time the player enters Xth'uoth
        if (e.entity != null && e.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.entity;
            if (e.world.provider.dimensionId == ConfigHandler.DIM_ID) {
                player.addStat(XARAchievements.getAchievement(1), 1);
            }
        }
    }

    @SubscribeEvent
    public void aberrationSlain(LivingDeathEvent e) {
        //ACHIEVEMENT
        //Detects the first aberration slain by this player
        Entity source = e.source.getSourceOfDamage();
        if (e.entity != null && source != null && source instanceof EntityPlayer) {
            ((EntityPlayer) source).addStat(XARAchievements.getAchievement(2), 1);
        }
    }

    /**
     * This event allows the player to also destroy a rift while wielding the veil shears
     */
    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent e) {
        if (e.entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemVeilShears && e.target instanceof EntityRift) {
            e.target.setDead();
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent e) {
        //POTION EFFECT
        //For poison immunity poison
        EntityLivingBase entity = e.entityLiving;
        if (entity.isPotionActive(XARPotions.poisonImmunity) && entity.isPotionActive(Potion.poison)) {
            entity.removePotionEffect(Potion.poison.getId());
        }

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            World world = player.worldObj;

            //Purifying effect of crystal armor
            if (ArmorSetUtils.isWearingArmorOfType(player, ItemPrysmalArmor.class) && !world.isRemote &&
                world.rand.nextInt(1000) <= ArmorSetUtils.getNumDonnedArmor(player, ItemPrysmalArmor.class, null)) {
                ThaumcraftApiHelper.addStickyWarpToPlayer(player, -1);
            }

            //Warp compounding effect from being in Xth'uoth
            int totalWarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(player.getCommandSenderName());
            totalWarp += (Integer) ReflectionUtils.invokeMethod(WarpEvents.class, null, "getWarpFromGear",
                    new Class[]{EntityPlayer.class}, player);
            if (player.dimension == ConfigHandler.DIM_ID && !world.isRemote && world.rand.nextInt(8000) <= totalWarp) {
                ThaumcraftApiHelper.addStickyWarpToPlayer(player, 1);
            }
        }
    }

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent e) {
        //POTION EFFECT
        //For lifesight potion?
    }
}
