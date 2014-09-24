package com.aurilux.xar.client.renderer.entity;

import com.aurilux.xar.entity.EntityRift;
import com.aurilux.xar.lib.XARModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderRift extends Render {
	private final ResourceLocation RIFT_TEXTURE = new ResourceLocation(XARModInfo.MOD_ID + ":textures/entity/rift.png");
	
	public void doRender(Entity entity, double xPos, double yPos, double zPos, float width, float height) {
		this.doRenderRift((EntityRift) entity, xPos, yPos, zPos, width, height);
	}
	
	public void doRenderRift(EntityRift rift, double xPos, double yPos, double zPos, float width, float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)xPos, (float)yPos + 1, (float)zPos);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        this.bindEntityTexture(rift);
        
        float riftHalfWidth  = .5f;
        float riftHalfHeight = 1f;
        
        //rotates the entity to always face the player across the y-axis
        //the first parameter determines the value(amount) of the rotation, while the other three are the value's effect on the axis (x, y, and z respectively)
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        //rotates the entity to always face the player on the x-axis
        //GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        //There is no z-rotation because that would cause it to tilt to the left or right, like when your character falls to the side when you die

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        //top-left
        tessellator.addVertexWithUV((double)(0 - riftHalfWidth), (double)(0 - riftHalfHeight), 0.0D, (double)0, (double)0);
        //top-right
        tessellator.addVertexWithUV((double)(1 - riftHalfWidth), (double)(0 - riftHalfHeight), 0.0D, (double)1, (double)0);
        //bottom-right
        tessellator.addVertexWithUV((double)(1 - riftHalfWidth), (double)(2 - riftHalfHeight), 0.0D, (double)1, (double)1);
        //bottom-left
        tessellator.addVertexWithUV((double)(0 - riftHalfWidth), (double)(2 - riftHalfHeight), 0.0D, (double)0, (double)1);
        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return RIFT_TEXTURE;
	}
}