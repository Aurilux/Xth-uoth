package aurilux.xar.render;

import aurilux.ardentcore.util.RenderUtils;
import aurilux.xar.Xthuoth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class SkyRendererXthuoth extends IRenderHandler {
	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //RenderHelper.disableStandardItemLighting();
        GL11.glDepthMask(false);
        RenderUtils.bindTexture(mc, Xthuoth.getEnviroRes("xthuoth_space.png"));
        Tessellator tessellator = Tessellator.instance;
        
        //Draws each side of the skybox cube
        for (int i = 0; i < 6; i++) {
            GL11.glPushMatrix();
            //mc.getTextureManager().bindTexture(ResourceUtils.getResource("textures/environment/img_space" + i + ".png"));
            if (i == 1)      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            else if (i == 2) GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            else if (i == 3) GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            else if (i == 4) GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            else if (i == 5) GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);

            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(2631720);
            tessellator.addVertexWithUV(-101.00D, -99.0D, -100.0D, 0.0D, 0.0D); // 0,0
            tessellator.addVertexWithUV(-101.00D, -99.0D, 100.0D, 0.0D, 1.0D); // 0,1
            tessellator.addVertexWithUV(101.00D, -99.0D, 100.0D, 1.0D, 1.0D); // 1,1
            tessellator.addVertexWithUV(101.00D, -99.0D, -100.0D, 1.0D, 0.0D); //1,0
            tessellator.draw();
            GL11.glPopMatrix();
        }

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
}