package aurilux.xthuoth.client.gui;

import aurilux.ardentcore.common.util.RenderUtils;
import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.inventory.container.ContainerImmixer;
import aurilux.xthuoth.common.tileentity.TileEntityImmixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiImmixer extends GuiContainer {
    public TileEntityImmixer tile;
    private static final ResourceLocation guiTexture = Xthuoth.assets.getGuiRes("immixerGui.png");

    public GuiImmixer(World world, InventoryPlayer inventoryPlayer, TileEntityImmixer tileEntity) {
        super(new ContainerImmixer(world, inventoryPlayer, tileEntity));
        this.tile = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = StatCollector.translateToLocal("container.immixer");
        this.fontRendererObj.drawString(s, this.xSize / 2 - 80, 6 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.bindTexture(this.mc, guiTexture);
        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(centerX, centerY, 0, 0, this.xSize, this.ySize);

        // This draws the bubbling icon for the progress indicator
        if (tile.isMixing()) {
            int mixTime = tile.getMixTimeScaled();
            this.drawTexturedModalRect(centerX + 97, centerY + 37, 176, 0, mixTime, 12);
        };
    }
}