package aurilux.xar.item;

import aurilux.xar.Xthuoth;
import aurilux.xar.entity.item.EntityRiftCatalyst;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRiftCatalyst extends Item {

	public ItemRiftCatalyst() {
		super();
        this.maxStackSize = 1;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = Xthuoth.getIcon(reg, "riftCatalyst");
    }
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
    	if (!player.capabilities.isCreativeMode && !(player.ridingEntity != null)) {
            --itemStack.stackSize;
    	}
    	
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityRiftCatalyst(world, player));
        }
        return itemStack;
    }
}