package aurilux.xar.handler;

import aurilux.ardentcore.init.ModAchievements;
import aurilux.xar.Xthuoth;
import aurilux.xar.entity.EntityRift;
import aurilux.xar.init.ModItems;
import aurilux.xar.init.ModPotions;
import aurilux.xar.item.ItemPrysmalArmor;
import aurilux.xar.item.ItemVeilShears;
import aurilux.ardentcore.util.ArmorSetUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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

import java.lang.reflect.Method;

public class EntityHandler {
    private static Method getWarpFromGear;

    @SubscribeEvent
    public void itemPickup(EntityItemPickupEvent e) {
        //ACHIEVEMENT
        //Detects the pickup of a crystal shard
        if (e.item.getEntityItem().getItem() == new ItemStack(ModItems.itemResource, 1, 0).getItem()) {
            ModAchievements.completeAchievement(Xthuoth.MOD_ID, e.entityPlayer, 0);
        }
    }

    @SubscribeEvent
    public void chunkEntered(EntityJoinWorldEvent e) {
        //ACHIEVEMENT
        //Detects the first time the player enters Xth'uoth
        if (e.entity != null && e.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.entity;
            if (e.world.provider.dimensionId == ConfigHandler.DIM_ID) {
                ModAchievements.completeAchievement(Xthuoth.MOD_ID, player, 1);
            }
        }
    }

    @SubscribeEvent
    public void aberrationSlain(LivingDeathEvent e) {
        //ACHIEVEMENT
        //Detects the first aberration slain by this player
        Entity source = e.source.getSourceOfDamage();
        if (e.entity != null && source != null && source instanceof EntityPlayer) {
            ModAchievements.completeAchievement(Xthuoth.MOD_ID, (EntityPlayer) source, 2);
        }
    }

    /**
     * This event allows the player to also destroy a rift while wielding the veil shears
     */
    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent e) {
        if (e.entityPlayer.getCurrentEquippedItem() == null) return;

        if (e.entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemVeilShears && e.target instanceof EntityRift) {
            e.target.setDead();
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent e) {
        //POTION EFFECT
        //For poison immunity poison
        EntityLivingBase entity = e.entityLiving;
        if (entity.isPotionActive(ModPotions.poisonImmunity) && entity.isPotionActive(Potion.poison)) {
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