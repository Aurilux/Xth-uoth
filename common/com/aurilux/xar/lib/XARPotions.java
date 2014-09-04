package com.aurilux.xar.lib;

import com.aurilux.xar.handler.PotionEffectHandler;
import com.aurilux.xar.potion.PotionLifesight;
import com.aurilux.xar.potion.PotionPoisonImmunity;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class XARPotions {
	private static final int NUM_NEW_POTIONS = 2;
	private static int potionOffset;
	
	public static final String wightBulbPodEffect = "+0+1-2+3&4-4+13";

	public static int nextPotionID = 0;
	public static Potion lifesight;
	public static Potion poisonImmunity;

	@SuppressWarnings("unchecked")
	public static void init() {
		expandPotions();
		lifesight = new PotionLifesight(potionOffset + nextPotionID++, false, 16767262).setPotionName("potion.lifesight");
		poisonImmunity = new PotionPoisonImmunity(potionOffset + nextPotionID++, false, 1280).setPotionName("potion.poisonImmunity");

        expandRecipes();

		//PotionHelper.potionRequirements.put(lifesight.getId(), "0 & !1 & 2 & 3");
		//PotionHelper.potionRequirements.put(poisonImmunity.getId(), "0 & 1 & !2 & 3 & 0+6");
		
		MinecraftForge.EVENT_BUS.register(new PotionEffectHandler());
	}
	
	/*
	 * Potion brewing requires a bit of understanding regarding bits (see what I did there? XD) as PotionHelper uses it to determine final brewing products
	 * For future ease of reference I will write the significant points down here
	 * Bits 0-3 are used to determine the potion effect
	 * Bit 4 determines if it's an awkward potion (brewed using nether wart)
	 * Bit 5 means the potion is Extended
	 * Bit 6 means the potion is Level II (cannot be both Extended and Level II)
	 * Bits 7-12 seem to do nothing
	 * Bit 13 determines if it is drinkable
	 * Bit 14 determines if it is splashable (cannot be both drinkable and splashable)
	 * 
	 * +(x) set bit at position 'x' (makes it 1)
	 * -(x) unset bit at position 'x' (makes it 0)
	 * &(x) check if bit at position 'x' is set (is 1)
	 * !(x) check if bit at position 'x' is unset (is 0)
	 */
	private static void expandPotions() {
		potionOffset = Potion.potionTypes.length;
		
		//Increase the size of the Potion.potionTypes array to have enough to to hold our potion additions
		//by creating a new array with the appropriate size and then copying the original contents
		Potion[] potionTypes = new Potion[potionOffset + NUM_NEW_POTIONS];
		System.arraycopy(Potion.potionTypes, 0, potionTypes, 0, potionOffset);

        //Find the Potion.potionTypes array and remove its 'final' modifier so we can change it to our new array we created above
        Field field = ReflectionHelper.findField(Potion.class,
                ObfuscationReflectionHelper.remapFieldNames(Potion.class.getName(), "potionTypes", "field_76425_a"));
		try {
			field.setAccessible(true);

			Field modfield = Field.class.getDeclaredField("modifiers");
			modfield.setAccessible(true);
			modfield.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(null, potionTypes);
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

    private static void expandRecipes() {
        Field field = ReflectionHelper.findField(PotionHelper.class,
                ObfuscationReflectionHelper.remapFieldNames(PotionHelper.class.getName(), "potionRequirements", "field_77927_l"));

        try {
            field.setAccessible(true);
            HashMap myPotionRequirements = (HashMap) field.get(null);

            myPotionRequirements.put(lifesight.getId(), "0 & !1 & 2 & 3");
            myPotionRequirements.put(poisonImmunity.getId(), "0 & 1 & !2 & 3 & 0+6");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
