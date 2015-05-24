package aurilux.xthuoth.common.init;

import aurilux.ardentcore.common.item.ItemSpawnEgg;
import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.item.*;
import aurilux.xthuoth.common.item.wand.ItemXARWandCap;
import aurilux.xthuoth.common.item.wand.PrysmalWandCap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

//@GameRegistry.ObjectHolder(Xthuoth.MOD_ID)
public class ModItems {
    //creative
    public static final Item taintedNode = new ItemTaintedNode(), veilShears = new ItemVeilShears();

    //armor
    public static final ItemArmor prysmalHelm = new ItemPrysmalArmor(0);
    public static final ItemArmor prysmalChest = new ItemPrysmalArmor(1);
    public static final ItemArmor prysmalLegs = new ItemPrysmalArmor(2);
    public static final ItemArmor prysmalBoots = new ItemPrysmalArmor(3);

    //misc
    /* The item that has subtypes for prysmal shards, prysmal lamina */
	public static final Item itemResource = new ItemResource();
	public static final Item riftCatalyst = new ItemRiftCatalyst();
	public static final Item wightbulbPod = new ItemWightbulbPod();
	public static final Item thanabloomHeart = new ItemThanabloomHeart();
    public static final Item bucketIchor = new ItemBucketIchor();
    public static final Item mixedPotion = new ItemMixedPotion();
    public static final ItemSpawnEgg entityEgg = new ItemSpawnEgg();

    //thaumcraft
    public static final Item itemWandCap = new ItemXARWandCap();
    public static final PrysmalWandCap prysmalWandCap = new PrysmalWandCap();
	
	public static void init() {
        Xthuoth.assets.setItem(taintedNode, "taintedNode");
        Xthuoth.assets.setItem(veilShears, "veilShears");

        Xthuoth.assets.setItem(prysmalHelm, "prysmalHelm");
        Xthuoth.assets.setItem(prysmalChest, "prysmalChest");
        Xthuoth.assets.setItem(prysmalLegs, "prysmalLegs");
        Xthuoth.assets.setItem(prysmalBoots, "prysmalBoots");

        Xthuoth.assets.setItem(itemResource, "itemResource");
        Xthuoth.assets.setItem(riftCatalyst, "riftCatalyst");
        Xthuoth.assets.setItem(wightbulbPod, "wightbulbPod");
        Xthuoth.assets.setItem(bucketIchor, "bucketIchor");
        Xthuoth.assets.setItem(mixedPotion, "mixedPotion");
        Xthuoth.assets.setItem(entityEgg, "entityEgg");

        Xthuoth.assets.setItem(itemWandCap, "prysmalWandCap");
	}
}
