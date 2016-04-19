package aurilux.xar.client.gui;

import aurilux.ardentcore.util.RenderUtils;
import aurilux.xar.Xthuoth;
import aurilux.xar.inventory.container.ContainerImmixer;
import aurilux.xar.tileentity.TileEntityImmixer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiImmixer extends GuiContainer {
    public TileEntityImmixer tile;
    private static final ResourceLocation guiTexture = Xthuoth.getGuiRes("immixerGui.png");

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