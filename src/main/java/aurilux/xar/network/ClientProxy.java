package aurilux.xar.network;

import aurilux.xar.Xthuoth;
import aurilux.xar.client.renderer.entity.RenderBlighter;
import aurilux.xar.client.renderer.entity.RenderRift;
import aurilux.xar.entity.EntityRift;
import aurilux.xar.entity.item.EntityRiftCatalyst;
import aurilux.xar.entity.monster.EntityBlighter;
import aurilux.xar.handler.EntityHandler;
import aurilux.xar.handler.GuiHandler;
import aurilux.xar.handler.WorldHandler;
import aurilux.xar.init.ModItems;
import aurilux.xar.tileentity.TileEntityImmixer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy {
    @Override
	public void init() {
        //register client-side components such as renderers, key handlers, etc
        registerHandlers();
        registerTileEntities();
        registerRenderers();
    }

    private void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityImmixer.class, "immixer");
    }

    private void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Xthuoth.instance, new GuiHandler());
    }

    private void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRift.class, new RenderRift());
		RenderingRegistry.registerEntityRenderingHandler(EntityRiftCatalyst.class, new RenderSnowball(ModItems.riftCatalyst, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlighter.class, new RenderBlighter());
	}
}