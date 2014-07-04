package com.aurilux.xar.lib;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigResearch;

import java.util.List;

public class XARRecipes {
    /**
     * Used in structure recipes as an empty air block
     */
    private static final ItemStack empty = new ItemStack(ConfigBlocks.blockHole, 1, 15);

	public static void init() {
        initCraftingRecipes();
        initArcaneRecipes();
        initCrucibleRecipes();
        initInfusionRecipes();
        initStructureRecipes();
	}

    private static void initCraftingRecipes() {
        registerCraftingRecipe("crystals0", true, new ItemStack(XARItems.crystalLamina, 1),
                "XX",
                "XX",
                'X', XARItems.crystalShard);
        registerCraftingRecipe("crystals1", true, new ItemStack(XARBlocks.blockCrystal, 1),
                "XXX",
                "XXX",
                "XXX",
                'X', XARItems.crystalLamina);
    }

    private static void initArcaneRecipes() {
        registerArcaneRecipe("riftCatalyst", "rifts", true, new ItemStack(XARItems.riftCatalyst, 1),
                new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20),
                "XXX",
                "XOX",
                "XXX",
                'X', XARItems.crystalLamina,
                'O', Items.ender_pearl);
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

    //HELPER METHODS
    private static void registerCraftingRecipe(String name, boolean isShaped, ItemStack output, Object... ingredients) {
        if (isShaped) {
            GameRegistry.addRecipe(output, ingredients);
        }
        else {
            GameRegistry.addShapelessRecipe(output, ingredients);
        }

        if (name != null && name.length() != 0) {
            List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
            ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
        }
    }

    private static void registerArcaneRecipe(String name, String researchKey, boolean isShaped, ItemStack output,
                                             AspectList aspects, Object... ingredients) {
        if (isShaped) {
            ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(researchKey, output, aspects, ingredients);
            ConfigResearch.recipes.put(name, recipe);
        }
        else {
            ShapelessArcaneRecipe recipe = ThaumcraftApi.addShapelessArcaneCraftingRecipe(researchKey, output, aspects, ingredients);
            ConfigResearch.recipes.put(name, recipe);
        }
    }

    private static void registerInfusionRecipe(String research, String name, Object output, int instability,
                                               AspectList aspects, ItemStack input, ItemStack... ingredients) {
        InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(research, output, instability, aspects, input, ingredients);
        if (research == null || research.length() <= 0) {
            ConfigResearch.recipes.put(name, recipe);
        }
        else {
            ConfigResearch.recipes.put(research, recipe);
        }
    }

    private static void registerInfusionEnchantmentRecipe(String name, String research, Enchantment output, int instability, AspectList aspects, ItemStack... stuff) {
        InfusionEnchantmentRecipe recipe = ThaumcraftApi.addInfusionEnchantmentRecipe(name, output, instability, aspects, stuff);
        if (research == null || research.length() <= 0) {
            ConfigResearch.recipes.put(name, recipe);
        }
        else {
            ConfigResearch.recipes.put(research, recipe);
        }
    }

    private static void registerStructureRecipe(String name, List<Object> list) {
        ConfigResearch.recipes.put(name, list);
    }
}