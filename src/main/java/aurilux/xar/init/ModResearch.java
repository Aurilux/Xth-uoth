package aurilux.xar.init;

import aurilux.xar.Xthuoth;
import aurilux.xar.research.XARResearchItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchPage;

import java.util.HashMap;
import java.util.List;

public class ModResearch {
    private static final String CATEGORY = "XTHUOTH";
    private static HashMap<String, Object> recipes = new HashMap<String, Object>();

    public static void init() {
        ResearchCategories.registerCategory(CATEGORY, Xthuoth.getGuiRes("swirl.png"),
                new ResourceLocation("thaumcraft", "textures/gui/gui_researchbackeldritch.png"));

        new XARResearchItem("xthenchants", CATEGORY,
            new AspectList(),
            -1, -4, 0, new ResourceLocation("thaumcraft", "textures/misc/r_enchant.png"))
            .setAutoUnlock().setRound()
            .registerResearchItem()
            .setPages(new ResearchPage("0"));

        new XARResearchItem("xthpotions", CATEGORY,
            new AspectList(),
            1, -4, 0, new ItemStack(Items.potionitem, 1, 0))
            .setAutoUnlock().setRound()
            .registerResearchItem()
            .setPages(new ResearchPage("0")); //, new ResearchPage("1"));

        new XARResearchItem("mixingPotions", CATEGORY,
            new AspectList(),
            1, -2, 3, new ItemStack(ModBlocks.immixer, 1, 0))
            .setRound().setParents("xthpotions")
            .registerResearchItem()
            .setPages(new ResearchPage("0"), new ResearchPage("1"), recipePage("potionMixer"));

        new XARResearchItem("prysmal", CATEGORY,
            new AspectList(),
            1, 0, 0, new ItemStack(ModBlocks.orePrysmal))
            .setAutoUnlock().setRound()
            .registerResearchItem()
            .setPages(new ResearchPage("0"), recipePage("prysmalLamina"), recipePage("prysmalBlock"));

        new XARResearchItem("shardInfusion", CATEGORY,
            new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.FIRE, 1).add(Aspect.WATER, 1),
            3, -2, 0, new ItemStack(ModItems.itemResource, 1, 0))
            .setRound().setSecondary().setParents("prysmal")
            .registerResearchItem()
            .setPages(new ResearchPage("0"), arcaneRecipePage("airInfusion", "fireInfusion", "waterInfusion",
                "earthInfusion", "orderInfusion", "entropyInfusion"));

        new XARResearchItem("prysmalWandCaps", CATEGORY,
            new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.MAGIC, 3).add(Aspect.TOOL, 3),
            3, 0, 0, new ItemStack(ModItems.itemWandCap))
            .setRound().setSecondary().setParents("prysmal").setParentsHidden("THAUMIUM", "CAP_gold")
            .registerResearchItem()
            .setPages(new ResearchPage("0"), arcaneRecipePage("prysmalCap"));

        new XARResearchItem("prysmalArmor", CATEGORY,
            new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.MAGIC, 3).add(Aspect.ARMOR, 5).add(Aspect.METAL, 3),
            3, 2, 0, new ItemStack(ModItems.prysmalChest))
            .setRound().setSecondary().setParents("prysmal").setParentsHidden("THAUMIUM")
            .registerResearchItem()
            .setPages(new ResearchPage("0"), arcaneRecipePage("prysmalHelm"), arcaneRecipePage("prysmalChest"),
                arcaneRecipePage("prysmalLegs"), arcaneRecipePage("prysmalBoots"));

        new XARResearchItem("rifts", CATEGORY,
            new AspectList(),
            -1, 0, 0, Xthuoth.getEntityRes("rift2.png"))
            .setAutoUnlock().setRound()
            .registerResearchItem()
            .setPages(new ResearchPage("0"), arcaneRecipePage("riftCatalyst"));

        new XARResearchItem("aberrations", CATEGORY,
            new AspectList(),
            -3, 2, 0, new ItemStack(ModBlocks.aberrack))
            .setAutoUnlock().setRound().setParents("rifts")
            .setPages(new ResearchPage("0"), new ResearchPage("blighter"))
            .registerResearchItem();

        //plants
    }

    public static void addRecipe(String name, Object recipe) {
        recipes.put(name, recipe);
    }

    public static Object getRecipe(String name) {
        return recipes.get(name);
    }

    //// HELPERS
    private static ResearchPage recipePage(String name) {
        return new ResearchPage((IRecipe) getRecipe(name));
    }

    private static ResearchPage arcaneRecipePage(String name) {
        return new ResearchPage((IArcaneRecipe) getRecipe(name));
    }

    private static ResearchPage arcaneRecipePage(String ... names) {
        IArcaneRecipe[] recipes = new IArcaneRecipe[names.length];
        for (int i = 0; i < names.length; i++) {
            recipes[i] = (IArcaneRecipe) getRecipe(names[i]);
        }
        return new ResearchPage(recipes);
    }

    private static ResearchPage crucibleRecipePage(String name) {
        return new ResearchPage((CrucibleRecipe) getRecipe(name));
    }

    private static ResearchPage infusionRecipePage(String name) {
        return new ResearchPage((InfusionRecipe) getRecipe(name));
    }

    private static ResearchPage infusionRecipePage(String ... names) {
        InfusionRecipe[] recipes = new InfusionRecipe[names.length];
        for (int i = 0; i < names.length; i++) {
            recipes[i] = (InfusionRecipe) getRecipe(names[i]);
        }
        return new ResearchPage(recipes);
    }

    private static ResearchPage structurePage(String name) {
        return new ResearchPage((List) getRecipe(name));
    }

    private static ResearchPage enchantRecipePage(String name) {
        return new ResearchPage((InfusionEnchantmentRecipe) getRecipe(name));
    }
    //// END HELPERS
}