package aurilux.xthuoth.common.inventory.slot;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.common.config.ConfigItems;

public class SlotEmulsifier extends Slot {
	//TODO make this a hashmap and allow other modders to add thier own emulsifiers
    //redstone dust, glowstone dust, tallow, salis mundus, tainted goo
    public static ItemStack[] emulsifiers = new ItemStack[] {
        new ItemStack(Items.redstone), new ItemStack(Items.glowstone_dust),
        new ItemStack(ConfigItems.itemResource, 4, 1), new ItemStack(ConfigItems.itemResource, 14, 1),
        new ItemStack(ConfigItems.itemResource, 11, 1)
    };

    public SlotEmulsifier(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean isItemValid(ItemStack itemStack)  {
        if (itemStack == null) return false;

        for (ItemStack stack : emulsifiers) {
            if (stack.getItem().equals(itemStack.getItem())) {
                return true;
            }
        }
        return false;
    }

    public int getSlotStackLimit() {
        return 64;
    }
}
