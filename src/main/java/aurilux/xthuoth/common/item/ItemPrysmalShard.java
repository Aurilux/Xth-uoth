package aurilux.xthuoth.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * This class was created by <Aurilux>. It's distributed as part of the Xth'uoth Mod.
 * <p/>
 * Xth'uoth is Open Source and distributed under the GNU Lesser General Public License v3.0
 * (https://www.gnu.org/licenses/lgpl.html)
 * <p/>
 * File Created @ [08 Jan 2015]
 */
public class ItemPrysmalShard extends Item {

    public ItemPrysmalShard() {
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }
}