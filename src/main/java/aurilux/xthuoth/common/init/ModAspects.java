package aurilux.xthuoth.common.init;

import aurilux.xthuoth.common.core.Xthuoth;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ModAspects {
    public static void init() {
        //BLOCKS
        registerBlockAspects("wightbulb", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.PLANT, 2));
        registerBlockAspects("aberrack", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.LIFE, 2));
        registerBlockAspects("fluidIchor", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.LIFE, 2));
        registerBlockAspects("orePrysmal", new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 2));
        registerBlockAspects("blockPrysmal", new AspectList().add(Aspect.CRYSTAL, 4));
        registerBlockAspects("immixer", new AspectList().add(Aspect.MAGIC, 2).add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 2));

        //ENTITIES
        registerEntityAspects("Blighter", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.TAINT, 2).add(Aspect.PLANT, 2));
        registerEntityAspects("Rift", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.TRAVEL, 2));

        //ITEMS
        registerItemAspects("wightbulbPod", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.PLANT, 2));
        registerItemAspects("riftCatalyst", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.CRYSTAL, 2));
        registerItemAspects("prysmalShard", new AspectList().add(Aspect.CRYSTAL, 1));
        registerItemAspects("prysmalLamina", new AspectList().add(Aspect.CRYSTAL, 1));
        registerItemAspects("bucketIchor", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.LIFE, 2).add(Aspect.METAL, 2));
        registerItemAspects("prysmalHelm", new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.ARMOR, 2).add(Aspect.METAL, 2));
        registerItemAspects("prysmalChest", new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.ARMOR, 2).add(Aspect.METAL, 2));
        registerItemAspects("prysmalLegs", new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.ARMOR, 2).add(Aspect.METAL, 2));
        registerItemAspects("prysmalBoots", new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.ARMOR, 2).add(Aspect.METAL, 2));
        registerItemAspects("prysmalWandCap", new AspectList().add(Aspect.CRYSTAL, 2));
        registerItemAspects("mixedPotion", new AspectList().add(Aspect.MAGIC, 2).add(Aspect.VOID, 2));
    }

    private static void registerBlockAspects(String blockName, AspectList aspectList) {
        ThaumcraftApi.registerObjectTag(blockName, aspectList);
    }

    private static void registerEntityAspects(String entityName, AspectList aspectList) {
        ThaumcraftApi.registerEntityTag(entityName, aspectList);
    }

    private static void registerItemAspects(String itemName, AspectList aspectList) {
        ItemStack item = GameRegistry.findItemStack(Xthuoth.MOD_ID, itemName, 1);
        ThaumcraftApi.registerObjectTag(item, aspectList);
    }
}