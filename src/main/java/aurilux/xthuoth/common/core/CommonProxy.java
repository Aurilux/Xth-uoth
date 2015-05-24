package aurilux.xthuoth.common.core;

import aurilux.xthuoth.common.handler.EntityHandler;
import aurilux.xthuoth.common.handler.WorldHandler;
import aurilux.xthuoth.common.inventory.container.ContainerImmixer;
import aurilux.xthuoth.common.tileentity.TileEntityImmixer;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler {
    //gui id's
    public final int GUI_ID_IMMIXER = 0;

    public void init() {
        registerHandlers();
    }

    private void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Xthuoth.instance, Xthuoth.proxy);
    }

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
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}