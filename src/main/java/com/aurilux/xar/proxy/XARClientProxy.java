package com.aurilux.xar.proxy;

import com.aurilux.xar.Xthuoth;
import com.aurilux.xar.client.renderer.entity.RenderBlighter;
import com.aurilux.xar.client.renderer.entity.RenderRift;
import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.entity.monster.EntityBlighter;
import com.aurilux.xar.handler.EntityHandler;
import com.aurilux.xar.handler.GuiHandler;
import com.aurilux.xar.handler.UpdateHandler;
import com.aurilux.xar.handler.WorldHandler;
import com.aurilux.xar.init.XARItems;
import com.aurilux.xar.tileentity.TileEntityImmixer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.common.MinecraftForge;

public class XARClientProxy extends XARServerProxy {
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
        FMLCommonHandler.instance().bus().register(new UpdateHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Xthuoth.instance, new GuiHandler());
    }

    private void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRift.class, new RenderRift());
		RenderingRegistry.registerEntityRenderingHandler(EntityRiftCatalyst.class, new RenderSnowball(XARItems.riftCatalyst, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlighter.class, new RenderBlighter());
	}
}