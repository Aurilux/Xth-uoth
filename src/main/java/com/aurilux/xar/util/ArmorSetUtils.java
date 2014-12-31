package com.aurilux.xar.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * A helper class that has common functions that deal with armor sets such as Prysmal armor
 */
public class ArmorSetUtils {
    /** Returns true if the player is wearing a complete set of armor of the type given */
    public static boolean isWearingCompleteSet(EntityPlayer player, Class armorClass) {
        return getNumDonnedArmor(player, armorClass, null) == 4;
    }

    /**
     * Returns how many pieces of a specific type of armor the player is wearing.
     * If the ItemStack parameter is not null, that specific piece of armor will not be included in the count
     */
    public static int getNumDonnedArmor(EntityPlayer player, Class armorClass, ItemStack stack) {
        int donned = 0;
        for (ItemStack armorStack : getPlayerArmor(player)) {
            if (armorStack != null && isType(armorStack, armorClass) && (stack == null || armorStack != stack)) {
                donned++;
            }
        }
        return donned;
    }

    /** Returns true if the player is wearing a specific armor item */
    public static boolean isEquipped(EntityPlayer player, ItemStack stack) {
        for (ItemStack armorStack : getPlayerArmor(player)) {
            if (armorStack != null && armorStack == stack) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWearingArmorOfType(EntityPlayer player, Class armorClass) {
        for (ItemStack armorStack : getPlayerArmor(player)) {
            if (armorStack != null && isType(armorStack, armorClass)) {
                return true;
            }
        }
        return false;
    }

    /** Simple helper method to save a bit on typing */
    public static ItemStack[] getPlayerArmor(EntityPlayer player) {
        return player.inventory.armorInventory;
    }

    /**
     * Simple helper method to test if the supplied object is an instance of the specified class.
     * The 'instanceof' operator does not work with dynamically supplied classes, so you must use the 'isInstance'
     * method from the Class, uh, class.
     */
    public static boolean isType(ItemStack armorStack, Class armorClass) {
        return armorStack.getItem().getClass().isAssignableFrom(armorClass);
    }
}
