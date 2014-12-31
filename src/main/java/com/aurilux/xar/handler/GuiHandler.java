package com.aurilux.xar.handler;

import com.aurilux.xar.client.gui.GuiImmixer;
import com.aurilux.xar.inventory.container.ContainerImmixer;
import com.aurilux.xar.tileentity.TileEntityImmixer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    //gui id's
    public static final int GUI_ID_IMMIXER = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUI_ID_IMMIXER:
                TileEntityImmixer tileEntityImmixer = (TileEntityImmixer) world.getTileEntity(x, y, z);
                if (tileEntityImmixer != null) {
                    return new ContainerImmixer(world, player.inventory, tileEntityImmixer);
                }
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUI_ID_IMMIXER:
                TileEntityImmixer tileEntityImmixer = (TileEntityImmixer) world.getTileEntity(x, y, z);
                if (tileEntityImmixer != null) {
                    return new GuiImmixer(world, player.inventory, tileEntityImmixer);
                }
                break;
        }
        return null;
    }
}