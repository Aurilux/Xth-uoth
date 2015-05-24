package aurilux.xthuoth.common.item;

import aurilux.ardentcore.common.util.ArmorSetUtils;
import aurilux.ardentcore.common.util.LangUtils;
import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.init.ModItems;
import aurilux.xthuoth.common.init.ModMisc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;

import java.util.List;

public class ItemPrysmalArmor extends ItemArmor implements IVisDiscountGear, IRepairable, IRunicArmor {
    //TODO have this implement ISpecialArmor to add a bit of magic resistance?
    private IIcon[] icons = new IIcon[4];

    public ItemPrysmalArmor(int armorType) {
        super(ModMisc.CRYSTAL, 2, armorType);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        icons[0] = Xthuoth.assets.getIcon(reg, "prysmalHelm");
        icons[1] = Xthuoth.assets.getIcon(reg, "prysmalChest");
        icons[2] = Xthuoth.assets.getIcon(reg, "prysmalLegs");
        icons[3] = Xthuoth.assets.getIcon(reg, "prysmalBoots");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icons[this.armorType];
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        String whichTexture = stack.getItem() == ModItems.prysmalLegs ? "2" : "1";
        return Xthuoth.assets.getArmorRes("/prysmal_layer_" + whichTexture + ".png").toString();
    }

    /**
     * @param stack ItemStack containing this item
     * @return the flat amount of vis discount depending on the armorType (helm, chest, legs, boots)
     */
    private int getFlatVisDiscount(ItemStack stack) {
        int flatAmount = 0;
        switch (this.armorType) {
            case 0: flatAmount = 2; break;
            case 1: flatAmount = 4; break;
            case 2: flatAmount = 3; break;
            case 3: flatAmount = 1; break;
        }
        return flatAmount;
    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
        int discount = getFlatVisDiscount(stack);
        //we only need to calculate the additional discounts if the player is currently wearing the armor
        if (ArmorSetUtils.isEquipped(player, stack)) {
            //add one percent for each additional piece of prysmal armor equipped
            discount += ArmorSetUtils.getNumDonnedArmor(player, this.getClass(), stack);
            //add an additional two percent if the player is wearing all prysmal armor
            discount += ArmorSetUtils.isWearingCompleteSet(player, this.getClass()) ? 2 : 0;
        }
        return discount;
    }

    @Override
    public int getRunicCharge(ItemStack itemstack) {
        return 0;
    }

    /**
     * NOTE: should your method calls within this function fail due to a null value, your item will become an "Unnamed"
     * item and an error WILL NOT be thrown to indicate this.
     */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean additionalInfo) {
        EnumChatFormatting infoColor = EnumChatFormatting.DARK_PURPLE;
        list.add(infoColor + StatCollector.translateToLocal("xth.purifying"));
        list.add(infoColor + StatCollector.translateToLocal("tc.visdiscount") + ": " +
            getVisDiscount(stack, player, null) + "%");

        //we only need to display the additional discounts if the player is currently wearing the armor
        if (ArmorSetUtils.isEquipped(player, stack)) {
            if (ArmorSetUtils.getNumDonnedArmor(player, this.getClass(), null) > 0) {
                list.add(infoColor + "   +" + getFlatVisDiscount(stack) + "% " +
                    LangUtils.translate("xth.visdiscountdetail0"));
                list.add(infoColor + "   +1% " + LangUtils.translate("xth.visdiscountdetail1"));
            }

            if (ArmorSetUtils.isWearingCompleteSet(player, this.getClass())) {
                list.add(infoColor + "   +2% " + LangUtils.translate("xth.visdiscountdetail2"));
            }
        }
    }
}