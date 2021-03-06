package aurilux.xthuoth.client.renderer.entity;

import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.entity.EntityRift;
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
        private ResourceLocation riftTexture = Xthuoth.assets.getEntityRes("rift.png");

	public void doRender(Entity entity, double xPos, double yPos, double zPos, float width, float height) {
		this.doRenderRift((EntityRift) entity, xPos, yPos, zPos, width, height);
	}

	public void doRenderRift(EntityRift rift, double xPos, double yPos, double zPos, float width, float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)xPos, (float)yPos + 1, (float)zPos);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        this.bindEntityTexture(rift);

        //Dimensions in terms of blocks. The Rift is only one block wide and two blocks tall
        float riftHalfWidth  = .5f;
        float riftHalfHeight = 1f;

        //rotates the entity to always face the player across the y-axis
        //the first parameter determines the value(amount) of the rotation, while the other three are the value's effect on the axis (x, y, and z respectively)
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        //tessellator.setNormal(0.0F, 1.0F, 0.0F);
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
		return riftTexture;
	}
}