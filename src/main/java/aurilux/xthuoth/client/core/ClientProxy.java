package aurilux.xthuoth.client.core;

import aurilux.xthuoth.client.gui.GuiImmixer;
import aurilux.xthuoth.client.renderer.entity.RenderBlighter;
import aurilux.xthuoth.client.renderer.entity.RenderRift;
import aurilux.xthuoth.common.core.CommonProxy;
import aurilux.xthuoth.common.entity.EntityRift;
import aurilux.xthuoth.common.entity.item.EntityRiftCatalyst;
import aurilux.xthuoth.common.entity.monster.EntityBlighter;
import aurilux.xthuoth.common.init.ModItems;
import aurilux.xthuoth.common.tileentity.TileEntityImmixer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
	public void init() {
        super.init();
        registerTileEntities();
        registerRenderers();
    }

    private void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityImmixer.class, "immixer");
    }

    private void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRift.class, new RenderRift());
		RenderingRegistry.registerEntityRenderingHandler(EntityRiftCatalyst.class, new RenderSnowball(ModItems.riftCatalyst, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlighter.class, new RenderBlighter());
	}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
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