package com.aurilux.xar.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.helpers.ItemXAR;

public class ItemRiftCatalyst extends ItemXAR {

	public ItemRiftCatalyst(int id) {
		super(id, "riftCatalyst");
        this.maxStackSize = 1;
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
    	if (!player.capabilities.isCreativeMode && !(player.ridingEntity != null)) {
            --itemStack.stackSize;
    	}
    	
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityRiftCatalyst(world, player));
        }
        return itemStack;
    }
}