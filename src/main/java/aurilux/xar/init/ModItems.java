package aurilux.xar.init;

import aurilux.ardentcore.item.ItemSpawnEgg;
import aurilux.xar.Xthuoth;
import aurilux.xar.item.*;
import aurilux.xar.item.wand.ItemXARWandCap;
import aurilux.xar.item.wand.PrysmalWandCap;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class ModItems {
    //creative
    public static Item taintedNode, veilShears;

    //armor
    public static ItemArmor prysmalHelm, prysmalChest, prysmalLegs, prysmalBoots;

    //misc
    /* The item that has subtypes for prysmal shards, prysmal lamina */
	public static Item itemResource;
	public static Item riftCatalyst;
	public static Item wightbulbPod;
	public static Item thanabloomHeart;
    public static Item bucketIchor;
    public static Item mixedPotion;
    public static ItemSpawnEgg entityEgg;

    //thaumcraft
    public static Item itemWandCap;
    public static PrysmalWandCap prysmalWandCap;
	
	public static void init() {
        taintedNode = new ItemTaintedNode();
        setItem(taintedNode, "taintedNode", "TaintedNode");
        veilShears = new ItemVeilShears();
        setItem(veilShears, "veilShears", "VeilShears");

        prysmalHelm = new ItemPrysmalArmor(0);
        setItem(prysmalHelm, "prysmalHelm", "PrysmalHelm");
        prysmalChest = new ItemPrysmalArmor(1);
        setItem(prysmalChest, "prysmalChest", "PrysmalChest");
        prysmalLegs = new ItemPrysmalArmor(2);
        setItem(prysmalLegs, "prysmalLegs", "PrysmalLegs");
        prysmalBoots = new ItemPrysmalArmor(3);
        setItem(prysmalBoots, "prysmalBoots", "PrysmalBoots");

		itemResource = new ItemResource();
        setItem(itemResource, "itemResource", "ItemResource");
        riftCatalyst = new ItemRiftCatalyst();
        setItem(riftCatalyst, "riftCatalyst", "RiftCatalyst");
		wightbulbPod = new ItemWightbulbPod();
        setItem(wightbulbPod, "wightbulbPod", "WightbulbPod");
		//thanabloomHeart = new ItemThanabloomHeart();
        //setItem(thanabloomHeart, "thanabloomHeart", "ThanabloomHeart");
        bucketIchor = new ItemBucketIchor();
        setItem(bucketIchor, "bucketIchor", "IchorBucket");
        mixedPotion = new ItemMixedPotion();
        setItem(mixedPotion, "mixedPotion", "MixedPotion");
        entityEgg = new ItemSpawnEgg();
        setItem(entityEgg, "entityEgg", "EntityEgg");

        itemWandCap = new ItemXARWandCap();
        prysmalWandCap = new PrysmalWandCap();
        setItem(itemWandCap, "prysmalWandCap", "PrysmalWandCap");
	}

    public static void setItem(Item item, String str, String str2) {
        item.setUnlocalizedName(str);
        item.setTextureName(str);
        item.setCreativeTab(ModMisc.tab);
        GameRegistry.registerItem(item, str2, Xthuoth.MOD_ID);
    }
}
