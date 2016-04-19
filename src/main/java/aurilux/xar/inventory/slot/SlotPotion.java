package aurilux.xar.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class SlotPotion extends Slot {
    public SlotPotion(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean isItemValid(ItemStack itemStack)  {
        return itemStack != null && itemStack.getItem() instanceof ItemPotion;
    }

    public int getSlotStackLimit() {
        return 1;
    }
}
