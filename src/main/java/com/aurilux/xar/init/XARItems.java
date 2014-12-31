package com.aurilux.xar.init;

import com.aurilux.xar.XARModInfo;
import com.aurilux.xar.item.*;
import com.aurilux.xar.item.wand.ItemXARWandCap;
import com.aurilux.xar.item.wand.PrysmalWandCap;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class XARItems {
    //creative
    public static Item taintedNode;
    public static Item veilShears;

    //armor
    public static ItemArmor prysmalHelm;
    public static ItemArmor prysmalChest;
    public static ItemArmor prysmalLegs;
    public static ItemArmor prysmalBoots;

    //misc
    //TODO optimize some of these to share the same class but have different meta values, similar to Thaumcraft
	public static Item prysmalShard;
	public static Item prysmalLamina;
	public static Item riftCatalyst;
	public static Item wightbulbPod;
	public static Item thanabloomHeart;
    public static Item bucketIchor;
    public static Item mixedPotion;

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

		prysmalShard = new ItemPrysmalShard();
        setItem(prysmalShard, "prysmalShard", "PrysmalShard");
		prysmalLamina = new ItemPrysmalLamina();
        setItem(prysmalLamina, "prysmalLamina", "PrysmalLamina");
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

        itemWandCap = new ItemXARWandCap();
        prysmalWandCap = new PrysmalWandCap();
        setItem(itemWandCap, "prysmalWandCap", "PrysmalWandCap");
	}

    public static void setItem(Item item, String str, String str2) {
        item.setUnlocalizedName(str);
        item.setTextureName(str);
        item.setCreativeTab(XARMisc.tab);
        GameRegistry.registerItem(item, str2, XARModInfo.MOD_ID);
    }
}
