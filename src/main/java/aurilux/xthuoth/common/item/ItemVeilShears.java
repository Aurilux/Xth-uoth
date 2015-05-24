package aurilux.xthuoth.common.item;

import aurilux.xthuoth.common.entity.EntityRift;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;

public class ItemVeilShears extends Item {
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon("minecraft:shears");
    }

    /**
     * This method is called when a player right-clicks at item on an object. For our purposes it spawns a rift
     * Although spawning a rift is handled by this method, the destruction is handled by an event in the EntityHandler class
     * @param itemStack the itemstack containing this item
     * @param player the player who activated the item
     * @param world
     * @param x the x position of the block clicked on
     * @param y the y position of the block clicked on
     * @param z the z position of the block clicked on
     * @param side the side of the block clicked on
     * @param hitX the local x coordinate clicked on the side of the block
     * @param hitY the local y coordinate clicked on the side of the block
     * @param hitZ the local z coordinate clicked on the side of the block
     * @return true if everything we wanted occurred correctly, false otherwise
     */
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            EntityRift newRift = new EntityRift(world, x, y, z);
            world.spawnEntityInWorld(newRift);
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
        list.add("§oSpawn or destroy a rift");
        list.add("§5Creative Mode Only");
    }
}