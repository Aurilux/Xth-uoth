package com.aurilux.xar.proxy;

import com.aurilux.xar.client.renderer.entity.RenderBlighter;
import com.aurilux.xar.client.renderer.entity.RenderRift;
import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.entity.item.EntityRiftCatalyst;
import com.aurilux.xar.entity.monster.EntityBlighter;
import com.aurilux.xar.lib.XARItems;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ClientXARProxy extends CommonXARProxy {
	
	@Override
	public void initRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRift.class, new RenderRift());
		RenderingRegistry.registerEntityRenderingHandler(EntityRiftCatalyst.class, new RenderSnowball(XARItems.riftCatalyst, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlighter.class, new RenderBlighter());
	}
}