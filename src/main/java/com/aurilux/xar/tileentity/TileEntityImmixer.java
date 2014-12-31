package com.aurilux.xar.tileentity;

import com.aurilux.xar.init.XARItems;
import com.aurilux.xar.inventory.slot.SlotEmulsifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class TileEntityImmixer extends TileEntity implements ISidedInventory {
    //Constants for easier readability
    public final int EMULSIFIER_INDEX = 0;
    public final int RESULT_INDEX = 1;
    public final int POTION_START = 2;

    /** The inventory of this TileEntity. 0 = emulsifier slot, 1 = result slot, 2-7 = potion slots */
    private ItemStack[] inventory = new ItemStack[8];

    /** The current emulsifier */
    private ItemStack emulsifer;

    /** Time spent brewing */
    public int mixTime;

    @Override
    public void updateEntity() {
        if (mixTime > 0) {
            --mixTime;
            if (mixTime == 0) {
                mixPotions();
                this.markDirty();
            }
            else if (!canMix() || emulsifer != getStackInSlot(EMULSIFIER_INDEX)) {
                mixTime = 0;
                this.markDirty();
            }
        }
        else if (canMix()) {
            mixTime = 400;
            emulsifer = getStackInSlot(EMULSIFIER_INDEX);
        }

        /*
        //TODO after I have a custom model: do I actually need this or do I just handle this in the model rendering?
        //Updates the block. Most likely to update the rendered model depending on how many potions are in the slots
        int i = this.getFilledSlots();

        if (i != this.filledSlots) {
            this.filledSlots = i;
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, i, 2);
        }
        */
        //this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        super.updateEntity();
    }

    private void mixPotions() {
        ItemStack resultPotion = new ItemStack(XARItems.mixedPotion, 1);
        int emulsifierLevel = calculateEmulsifierLevel();
        ArrayList<PotionEffect> resultList = new ArrayList<PotionEffect>();
        NBTTagCompound potionEffects = new NBTTagCompound();
        NBTTagList taglist = new NBTTagList();

        for (int i = POTION_START; i < getSizeInventory(); i++) {
            ItemStack potion = getStackInSlot(i);
            ArrayList<PotionEffect> effectList = getPotionEffects(potion);
            if (resultList.size() == emulsifierLevel) {
                break;
            }
            else if (resultList.size() + effectList.size() > emulsifierLevel) {
                continue;
            }
            else {
                for (PotionEffect effect : effectList) {
                    if (!resultList.contains(effect)) {
                        resultList.add(effect);
                        NBTTagCompound effectCompound = new NBTTagCompound();
                        taglist.appendTag(effect.writeCustomPotionEffectToNBT(effectCompound));
                    }
                }
                //since this potion was used, remove it from the inventory
                setInventorySlotContents(i, null);
            }
        }

        potionEffects.setTag("CustomPotionEffects", taglist);
        resultPotion.setTagCompound(potionEffects);
        setInventorySlotContents(EMULSIFIER_INDEX, decrStackSize(EMULSIFIER_INDEX, 1));
        setInventorySlotContents(RESULT_INDEX, resultPotion);
    }

    private boolean canMix() {
        //FIRST: There is no reason to mix if we don't have an emulsifier or if there is already a result potion.
        if (getStackInSlot(EMULSIFIER_INDEX) == null || getStackInSlot(RESULT_INDEX) != null) return false;

        int potionCount = 0;
        for (int i = POTION_START; i < getSizeInventory(); i++) {
        	ItemStack itemStack = getStackInSlot(i);
        	if (itemStack != null) {
        		potionCount++;
        	}
        }
        return potionCount >= 2;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        inventory = new ItemStack[getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            setInventorySlotContents(slotIndex, ItemStack.loadItemStackFromNBT(tagCompound));
        }
        mixTime = nbtTagCompound.getShort("mixTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("mixTime", (short)mixTime);

        //Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                getStackInSlot(i).writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        readFromNBT(packet.func_148857_g());
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    //// INVENTORY
    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        inventory[slot] = itemStack;
    }

    /**
     * Decreases the size of the stack in the specified slot
     * @param index the index of the slot
     * @param amount the amount to decrease the stack by
     * @return the resulting itemstack
     */
    @Override
    public ItemStack decrStackSize(int index, int amount) {
        ItemStack itemStack = getStackInSlot(index);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount) {
                setInventorySlotContents(index, null);
            }
            else {
                ItemStack splitStack = itemStack.splitStack(amount);
                if (splitStack.stackSize == 0) {
                    setInventorySlotContents(index, null);
                }
            }
        }
        return itemStack;
    }

    @Override
    public boolean receiveClientEvent(int eventId, int eventData) {
        return eventId == 1 || super.receiveClientEvent(eventId, eventData);
    }
    //// END INVENTORY

    //// AUTOMATION
    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[0];
    }

    /** Returns true if automation can insert the given item in the given slot from the given side. */
    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        return false;
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. */
    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return false;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return false;
    }
    //// END AUTOMATION

    //// HELPERS
    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     *
     * All of our slots store their contents, so we return null and don't drop anything.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<PotionEffect> getPotionEffects(ItemStack itemStack) {
        return itemStack == null ? new ArrayList<PotionEffect>() : (ArrayList<PotionEffect>)((ItemPotion) itemStack.getItem()).getEffects(itemStack);
    }

    private int calculateEmulsifierLevel() {
        int index = 0;
        for (ItemStack emulsifierStack : SlotEmulsifier.emulsifiers) {
            if (emulsifierStack.getItem() == inventory[EMULSIFIER_INDEX].getItem()) {
                return (index + 1) * 2;
            }
            index++;
        }
        return 0;
    }
    //// END HELPERS

    //// GETTERS AND SETTERS
    /** Returns the name of the inventory */
    @Override
    public String getInventoryName() {
		return null;
    }

    /** Returns true if the inventory is named */
    @Override
    public boolean hasCustomInventoryName() {
		return false;
    }

    /** Returns the maximum stack size for an inventory slot. */
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
            player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    /** Returns the number of slots in the inventory. */
    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    /** Returns the stack in the specified slot. */
    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    /** Used by 'ContainerImmixer' to determine how much to draw the progress indicator and to sync with server */
    @SideOnly(Side.CLIENT)
    public int getMixTimeScaled() {
        return (int) (mixTime * 29.0F / 400.0F);
    }

    public boolean isMixing() {
        return mixTime > 0;
    }
    //// END GETTERS AND SETTERS
    
    //// NO-OP's
    @Override
    public void openInventory() {
        //NO-OP
    }

    @Override
    public void closeInventory() {
        //NO-OP
    }
    //// END NO-OP's
}