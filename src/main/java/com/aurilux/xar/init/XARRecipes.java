package com.aurilux.xar.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.wands.WandCap;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemCrystalEssence;

import java.util.List;

public class XARRecipes {
    /** Used in structure recipes as an empty air block */
    private static final ItemStack empty = new ItemStack(ConfigBlocks.blockHole, 1, 15);

	public static void init() {
        initCraftingRecipes();
        initArcaneRecipes();
        initCrucibleRecipes();
        initInfusionRecipes();
        initStructureRecipes();
	}

    private static void initCraftingRecipes() {
        registerCraftingRecipe("prysmalLamina", true, new ItemStack(XARItems.prysmalLamina, 1),
            "XX",
            "XX",
            'X', XARItems.prysmalShard);
        registerCraftingRecipe("prysmalBlock", true, new ItemStack(XARBlocks.blockPrysmal, 1),
            "XXX",
            "XXX",
            "XXX",
            'X', XARItems.prysmalLamina);
        registerCraftingRecipe("potionMixer", true, new ItemStack(XARBlocks.immixer, 1),
            "XZX",
            "YYY",
            'X', Items.brewing_stand,
	        'Y', Blocks.stone,
	        'Z', XARItems.prysmalLamina);
    }

    private static void initArcaneRecipes() {
        registerArcaneRecipe("riftCatalyst", "rifts", true, new ItemStack(XARItems.riftCatalyst, 1),
            new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20),
            "XXX",
            "XOX",
            "XXX",
            'X', XARItems.prysmalLamina,
            'O', Items.ender_pearl);
        registerArcaneRecipe("prysmalHelm", "prysmalArmor", true, new ItemStack(XARItems.prysmalHelm, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XXX",
            "XYX",
            'X', XARItems.prysmalLamina,
            'Y', ConfigItems.itemHelmetThaumium);
        registerArcaneRecipe("prysmalChest", "prysmalArmor", true, new ItemStack(XARItems.prysmalChest, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XYX",
            "XXX",
            "XXX",
            'X', XARItems.prysmalLamina,
            'Y', ConfigItems.itemChestThaumium);
        registerArcaneRecipe("prysmalLegs", "prysmalArmor", true, new ItemStack(XARItems.prysmalLegs, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XXX",
            "XYX",
            "X X",
            'X', XARItems.prysmalLamina,
            'Y', ConfigItems.itemLegsThaumium);
        registerArcaneRecipe("prysmalBoots", "prysmalArmor", true, new ItemStack(XARItems.prysmalBoots, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XYX",
            "X X",
            'X', XARItems.prysmalLamina,
            'Y', ConfigItems.itemBootsThaumium);

        int wandCost = WandCap.caps.get("prysmal").getCraftCost();
        registerArcaneRecipe("prysmalCap", "prysmalWandCaps", true, new ItemStack(XARItems.itemWandCap, 1),
            new AspectList().add(Aspect.ORDER, wandCost).add(Aspect.FIRE, wandCost).add(Aspect.AIR, wandCost),
            "XXX",
            "XYX",
            'X', XARItems.prysmalShard,
            'Y', new ItemStack(ConfigItems.itemWandCap, 1, 2));

        //shard infusion
        ItemStack crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.AIR, 1));
        registerArcaneRecipe("airInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 0),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(XARItems.prysmalShard), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.FIRE, 1));
        registerArcaneRecipe("fireInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 1),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(XARItems.prysmalShard), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.WATER, 1));
        registerArcaneRecipe("waterInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 2),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(XARItems.prysmalShard), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.EARTH, 1));
        registerArcaneRecipe("earthInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 3),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(XARItems.prysmalShard), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.ORDER, 1));
        registerArcaneRecipe("orderInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 4),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(XARItems.prysmalShard), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.ENTROPY, 1));
        registerArcaneRecipe("entropyInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 5),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(XARItems.prysmalShard), crystalEssence);
    }

    private static void initCrucibleRecipes() {
    }

    private static void initInfusionRecipes() {
    }

    private static void initStructureRecipes() {
        /* Example recipe
        registerStructureRecipe("test", Arrays.asList(new Object[] {
             new AspectList(), //the aspects consumed to create the structure
             Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(3), //These three represent the dimensions
             Arrays.asList(new ItemStack[] {
                 empty, empty, empty,
                 empty, empty, empty,
                 empty, empty, empty,
                 empty, empty, empty,
                 empty, new ItemStack(Items.stick), empty,
                 empty, empty, empty,
                 empty, empty, empty,
                 empty, new ItemStack(Blocks.bookshelf), empty,
                 empty, empty, empty
             })
    	}));
         */
    }

    //// HELPER
    private static void registerCraftingRecipe(String name, boolean isShaped, ItemStack output, Object... ingredients) {
        if (isShaped) {
            GameRegistry.addRecipe(output, ingredients);
        }
        else {
            GameRegistry.addShapelessRecipe(output, ingredients);
        }

        if (name != null && name.length() != 0) {
            List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
            //Get the vanilla crafting recipe we just added above then add it to our list of recipes for use in the Thaumonomicon
            XARResearch.addRecipe(name, recipeList.get(recipeList.size() - 1));
        }
    }

    private static void registerArcaneRecipe(String name, String research, boolean isShaped, ItemStack output,
                                             AspectList aspects, Object... ingredients) {
        if (isShaped) {
            ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, ingredients);
            XARResearch.addRecipe(name, recipe);
        }
        else {
            ShapelessArcaneRecipe recipe = ThaumcraftApi.addShapelessArcaneCraftingRecipe(research, output, aspects, ingredients);
            XARResearch.addRecipe(name, recipe);
        }
    }

    private static void registerInfusionRecipe(String name, String research, Object output, int instability,
                                               AspectList aspects, ItemStack input, ItemStack... ingredients) {
        InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(research, output, instability, aspects, input, ingredients);
        if (name == null || name.length() <= 0) {
            XARResearch.addRecipe(research, recipe);
        }
        else {
            XARResearch.addRecipe(name, recipe);
        }
    }

    private static void registerInfusionEnchantmentRecipe(String name, String research, Enchantment output,
                                                          int instability, AspectList aspects, ItemStack... stuff) {
        InfusionEnchantmentRecipe recipe = ThaumcraftApi.addInfusionEnchantmentRecipe(name, output, instability, aspects, stuff);
        if (name == null || name.length() <= 0) {
            XARResearch.addRecipe(research, recipe);
        }
        else {
            XARResearch.addRecipe(name, recipe);
        }
    }

    private static void registerStructureRecipe(String name, List<Object> list) {
        XARResearch.addRecipe(name, list);
    }
    //// END HELPERs
}