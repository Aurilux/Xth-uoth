package aurilux.xthuoth.common.inventory.container;

import aurilux.xthuoth.common.inventory.slot.SlotEmulsifier;
import aurilux.xthuoth.common.inventory.slot.SlotPotion;
import aurilux.xthuoth.common.tileentity.TileEntityImmixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerImmixer extends Container {
    //Constants for easier readability
    private final int INV_START = 8;
    private final int INV_END = 34;
    private final int HOTBAR_START = 35;
    private final int HOTBAR_END = 43;

    private TileEntityImmixer tile;
    private int brewTime;
    private World world;

    public ContainerImmixer(World world, InventoryPlayer inventoryPlayer, TileEntityImmixer tileEntity) {
        this.world = world;
        tile = tileEntity;
        //tile.openInventory();

        this.addSlotToContainer(new SlotEmulsifier(tile, 0, 128, 35));
        //result/center slot
        this.addSlotToContainer(new Slot(tile, 1, 79, 35) {
            public boolean isItemValid(ItemStack itemStack)
            {
                return false;
            }
        });
        //top three potion slots
        this.addSlotToContainer(new SlotPotion(tile, 2, 56, 16));
        this.addSlotToContainer(new SlotPotion(tile, 3, 79, 9));
        this.addSlotToContainer(new SlotPotion(tile, 4, 102, 16));
        //bottom three potion slots
        this.addSlotToContainer(new SlotPotion(tile, 5, 56, 54));
        this.addSlotToContainer(new SlotPotion(tile, 6, 79, 61));
        this.addSlotToContainer(new SlotPotion(tile, 7, 102, 54));

        bindPlayerInventory(inventoryPlayer);
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                if (row < 3) { //the player's inventory
                    this.addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, tile.getSizeInventory() + col * 18, 84 + row * 18));
                }
                else { //row == 3, the player's hotbar
                    this.addSlotToContainer(new Slot(inventoryPlayer, col, tile.getSizeInventory() + col * 18, 142));
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUseableByPlayer(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, tile.mixTime);
    }

    /** Looks for changes made in the container, sends them to every listener. */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); i++) {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (brewTime != tile.mixTime) {
                icrafting.sendProgressBarUpdate(this, 0, tile.mixTime);
            }
        }
        brewTime = tile.mixTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int valueType, int updatedValue) {
        if (valueType == 0) {
            tile.mixTime = updatedValue;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //the slot clicked is in the player's inventory
            if (slotIndex >= tile.getSizeInventory()) {
                Item item = itemstack1.getItem();
                //try to move it into a potion slot
                if (item instanceof ItemPotion) {
                    if (!this.mergeItemStack(itemstack1, tile.POTION_START, tile.POTION_START + 6, false)) {
                        return null;
                    }
                }
                //try to move it into the emulsifier slot
                else if (((Slot)this.inventorySlots.get(tile.EMULSIFIER_INDEX)).isItemValid(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, tile.EMULSIFIER_INDEX, tile.EMULSIFIER_INDEX + 1, false)) {
                        return null;
                    }
                }
                //if its in the main inventory, try to move it to the hotbar
                else if (slotIndex >= INV_START && slotIndex < INV_END) {
                    if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
                        return null;
                    }
                }
                //if its in the hotbar, try to move it to the main inventory
                else if (slotIndex >= HOTBAR_START && slotIndex < HOTBAR_END) {
                    if(!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
                        return null;
                    }
                }
            }
            else { //slotIndex < tile.getSizeInventory()
                if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, false)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, itemstack1);
        }
        return itemstack;
    }
}