package aurilux.xar.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import java.util.List;

public class ItemTaintedNode extends Item {
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon("thaumcraft:taint_slime");
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            int xMod = 0, yMod = 0, zMod = 0;
            switch(side) {
                case 0: yMod = -2; break; //since the rift is 2 blocks tall, we move it down 2 blocks if we click the bottom side of the block
                case 1: yMod = 1; break;
                case 2: zMod = -1; break;
                case 3: zMod = 1; break;
                case 4: xMod = -1; break;
                case 5: xMod = 1; break;
                default: yMod = 1;
            }
            ThaumcraftWorldGenerator.createNodeAt(world, x + xMod, y + yMod, z + zMod, NodeType.TAINTED, null, new AspectList().add(Aspect.TAINT, 10));
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedInfo) {
        list.add("§oSpawn a tainted node");
        list.add("§5Creative Mode Only");
    }
}