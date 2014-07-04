package com.aurilux.xar.handlers;

import com.aurilux.xar.entity.EntityRift;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class PlayerHandler {
    @SubscribeEvent
    public void onPlayerInteract(EntityInteractEvent event) {
        EntityPlayer player = event.entityPlayer;
        System.out.println("here, " + player.capabilities.isCreativeMode + ", " + (event.target));
        if (player.capabilities.isCreativeMode && event.target instanceof EntityRift) {
            event.target.setDead();
            System.out.println("here2");
        }
    }
}
