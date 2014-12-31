package com.aurilux.xar.client.renderer.entity;

import com.aurilux.xar.entity.monster.EntityBlighter;
import com.aurilux.xar.util.ResourceUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBlighter extends RenderLiving {
	private final ResourceLocation BLIGHTER_TEXTURE = ResourceUtils.getResource("textures/entity/blighter.png");
	
	public RenderBlighter() {
		super(new ModelCreeper(), .5f);
	}

    /** Updates creeper scale in prerender callback */
    protected void updateBlighterScale(EntityBlighter blighter, float partialTick) {
        float flashIntensity = blighter.getBlighterFlashIntensity(partialTick);
        float f2 = 1.0F + MathHelper.sin(flashIntensity * 100.0F) * flashIntensity * 0.01F;

        if (flashIntensity < 0.0F) flashIntensity = 0.0F;
        if (flashIntensity > 1.0F) flashIntensity = 1.0F;

        flashIntensity *= flashIntensity;
        flashIntensity *= flashIntensity;
        float f3 = (1.0F + flashIntensity * 0.4F) * f2;
        float f4 = (1.0F + flashIntensity * 0.1F) / f2;
        GL11.glScalef(f3, f4, f3);
    }

    /** Updates color multiplier based on creeper state called by getColorMultiplier */
    protected int updateBlighterColorMultiplier(EntityBlighter blighter, float par2, float partialTick) {
        float f2 = blighter.getBlighterFlashIntensity(partialTick);

        if ((int)(f2 * 10.0F) % 2 == 0) {
            return 0;
        }
        else {
            int i = (int)(f2 * 0.2F * 255.0F);

            if (i < 0) i = 0;
            if (i > 255) i = 255;

            short short1 = 255;
            short short2 = 255;
            short short3 = 255;
            return i << 24 | short1 << 16 | short2 << 8 | short3;
        }
    }

    protected int func_77061_b(EntityBlighter blighter, int par2, float par3) {
        return -1;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entity, float partialTick) {
        updateBlighterScale((EntityBlighter)entity, partialTick);
    }

    /** Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime */
    protected int getColorMultiplier(EntityLivingBase entity, float brightness, float partialTick) {
        return updateBlighterColorMultiplier((EntityBlighter)entity, brightness, partialTick);
    }

    protected int inheritRenderPass(EntityLivingBase entity, int par2, float par3) {
        return func_77061_b((EntityBlighter)entity, par2, par3);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return BLIGHTER_TEXTURE;
	}
}