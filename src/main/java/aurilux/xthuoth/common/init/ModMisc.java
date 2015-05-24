package aurilux.xthuoth.common.init;

import aurilux.xthuoth.common.enchantment.EnchantmentRadiant;
import aurilux.xthuoth.common.handler.ConfigHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ModMisc {
	public static final EnumCreatureAttribute ABERRATION = EnumHelper.addCreatureAttribute("ABERRATION");
    public static final ItemArmor.ArmorMaterial CRYSTAL = EnumHelper.addArmorMaterial("CRYSTAL", 10, new int[]{2, 5, 4, 1}, 15);
	public static Enchantment radiant;

	public static void init() {
		radiant = new EnchantmentRadiant(ConfigHandler.RADIANT_ENCH_ID, 5, 3);
	}
}