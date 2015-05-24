package aurilux.xthuoth.common.item;

import aurilux.ardentcore.common.util.LangUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemMixedPotion extends ItemPotion {
    @SideOnly(Side.CLIENT)
	private IIcon drinkableIcon;
    @SideOnly(Side.CLIENT)
	private IIcon splashIcon;
    @SideOnly(Side.CLIENT)
	private IIcon overlayIcon;
	
	public ItemMixedPotion() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        this.setMaxDamage(0);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
    	//NO-OP
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        drinkableIcon = reg.registerIcon("potion_bottle_drinkable");
        splashIcon = reg.registerIcon("potion_bottle_splash");
        overlayIcon = reg.registerIcon("potion_overlay");
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return isSplash(damage) ? this.splashIcon : this.drinkableIcon;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 0 ? this.overlayIcon : super.getIconFromDamageForRenderPass(damage, pass);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int pass) {
        return pass > 0 ? 16777215 : super.getColorFromItemStack(itemStack, pass);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean additionalInfo) {
    	if (itemStack == null) return;
    	
    	for (PotionEffect effect : (List<PotionEffect>)this.getEffects(itemStack)) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String potionString = (potion.isBadEffect() ? EnumChatFormatting.RED : EnumChatFormatting.GRAY) +
                    LangUtils.translate(effect.getEffectName());
            
            if (effect.getAmplifier() > 0) {
                potionString += " " + LangUtils.translate("potion.potency." + effect.getAmplifier()).trim();
            }

            if (effect.getDuration() > 20) {
                potionString += " (" + Potion.getDurationString(effect) + ")";
            }
            
            list.add(potionString);
    	}
    }

	@Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        if (itemStack.getItemDamage() == 0 || !isSplash(itemStack.getItemDamage())) {
            return LangUtils.translate("item.mixedPotion.drink").trim();
        }
        else { //potion is splashable
            return LangUtils.translate("item.mixedPotion.throw").trim();
        }
    }

	@Override
    public EnumRarity getRarity(ItemStack itemStack) {
    	return EnumRarity.rare;
    }
}