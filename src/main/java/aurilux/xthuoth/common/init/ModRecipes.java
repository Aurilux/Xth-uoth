package aurilux.xthuoth.common.init;

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

public class ModRecipes {
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
        registerCraftingRecipe("prysmalLamina", true, new ItemStack(ModItems.itemResource, 1, 0),
            "XX",
            "XX",
            'X', new ItemStack(ModItems.itemResource, 1, 0));
        registerCraftingRecipe("prysmalBlock", true, new ItemStack(ModBlocks.blockPrysmal, 1),
            "XXX",
            "XXX",
            "XXX",
            'X', new ItemStack(ModItems.itemResource, 1, 1));
        registerCraftingRecipe("potionMixer", true, new ItemStack(ModBlocks.immixer, 1),
            "XZX",
            "YYY",
            'X', Items.brewing_stand,
	        'Y', Blocks.stone,
	        'Z', new ItemStack(ModItems.itemResource, 1, 1));
    }

    private static void initArcaneRecipes() {
        registerArcaneRecipe("riftCatalyst", "rifts", true, new ItemStack(ModItems.riftCatalyst, 1),
            new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20),
            "XXX",
            "XOX",
            "XXX",
            'X', new ItemStack(ModItems.itemResource, 1, 1),
            'O', Items.ender_pearl);
        registerArcaneRecipe("prysmalHelm", "prysmalArmor", true, new ItemStack(ModItems.prysmalHelm, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XXX",
            "XYX",
            'X', new ItemStack(ModItems.itemResource, 1, 1),
            'Y', ConfigItems.itemHelmetThaumium);
        registerArcaneRecipe("prysmalChest", "prysmalArmor", true, new ItemStack(ModItems.prysmalChest, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XYX",
            "XXX",
            "XXX",
            'X', new ItemStack(ModItems.itemResource, 1, 1),
            'Y', ConfigItems.itemChestThaumium);
        registerArcaneRecipe("prysmalLegs", "prysmalArmor", true, new ItemStack(ModItems.prysmalLegs, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XXX",
            "XYX",
            "X X",
            'X', new ItemStack(ModItems.itemResource, 1, 1),
            'Y', ConfigItems.itemLegsThaumium);
        registerArcaneRecipe("prysmalBoots", "prysmalArmor", true, new ItemStack(ModItems.prysmalBoots, 1),
            new AspectList().add(Aspect.ORDER, 10),
            "XYX",
            "X X",
            'X', new ItemStack(ModItems.itemResource, 1, 1),
            'Y', ConfigItems.itemBootsThaumium);

        int wandCost = WandCap.caps.get("prysmal").getCraftCost();
        registerArcaneRecipe("prysmalCap", "prysmalWandCaps", true, new ItemStack(ModItems.itemWandCap, 1),
            new AspectList().add(Aspect.ORDER, wandCost).add(Aspect.FIRE, wandCost).add(Aspect.AIR, wandCost),
            "XXX",
            "XYX",
            'X', new ItemStack(ModItems.itemResource, 1, 1),
            'Y', new ItemStack(ConfigItems.itemWandCap, 1, 2));

        //shard infusion
        ItemStack crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.AIR, 1));
        registerArcaneRecipe("airInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 0),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(ModItems.itemResource, 1, 0), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.FIRE, 1));
        registerArcaneRecipe("fireInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 1),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(ModItems.itemResource, 1, 0), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.WATER, 1));
        registerArcaneRecipe("waterInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 2),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(ModItems.itemResource, 1, 0), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.EARTH, 1));
        registerArcaneRecipe("earthInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 3),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(ModItems.itemResource, 1, 0), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.ORDER, 1));
        registerArcaneRecipe("orderInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 4),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(ModItems.itemResource, 1, 0), crystalEssence);

        crystalEssence = new ItemStack(ConfigItems.itemCrystalEssence, 1);
        ((ItemCrystalEssence)crystalEssence.getItem()).setAspects(crystalEssence, new AspectList().add(Aspect.ENTROPY, 1));
        registerArcaneRecipe("entropyInfusion", "shardInfusion", false, new ItemStack(ConfigItems.itemShard, 1, 5),
            new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2),
            new ItemStack(ModItems.itemResource, 1, 0), crystalEssence);
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
            ModResearch.addRecipe(name, recipeList.get(recipeList.size() - 1));
        }
    }

    private static void registerArcaneRecipe(String name, String research, boolean isShaped, ItemStack output,
                                             AspectList aspects, Object... ingredients) {
        if (isShaped) {
            ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, ingredients);
            ModResearch.addRecipe(name, recipe);
        }
        else {
            ShapelessArcaneRecipe recipe = ThaumcraftApi.addShapelessArcaneCraftingRecipe(research, output, aspects, ingredients);
            ModResearch.addRecipe(name, recipe);
        }
    }

    private static void registerInfusionRecipe(String name, String research, Object output, int instability,
                                               AspectList aspects, ItemStack input, ItemStack... ingredients) {
        InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(research, output, instability, aspects, input, ingredients);
        if (name == null || name.length() <= 0) {
            ModResearch.addRecipe(research, recipe);
        }
        else {
            ModResearch.addRecipe(name, recipe);
        }
    }

    private static void registerInfusionEnchantmentRecipe(String name, String research, Enchantment output,
                                                          int instability, AspectList aspects, ItemStack... stuff) {
        InfusionEnchantmentRecipe recipe = ThaumcraftApi.addInfusionEnchantmentRecipe(name, output, instability, aspects, stuff);
        if (name == null || name.length() <= 0) {
            ModResearch.addRecipe(research, recipe);
        }
        else {
            ModResearch.addRecipe(name, recipe);
        }
    }

    private static void registerStructureRecipe(String name, List<Object> list) {
        ModResearch.addRecipe(name, list);
    }
    //// END HELPERs
}