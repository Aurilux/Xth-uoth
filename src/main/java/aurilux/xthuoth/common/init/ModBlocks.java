package aurilux.xthuoth.common.init;

import aurilux.xthuoth.common.block.*;
import aurilux.xthuoth.common.core.Xthuoth;
import aurilux.xthuoth.common.fluids.FluidIchor;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

//@GameRegistry.ObjectHolder(Xthuoth.MOD_ID)
public class ModBlocks {
	public static final Fluid ichor = new FluidIchor().setLuminosity(15).setDensity(3000).setViscosity(6000)
			.setTemperature(1300);
	public static final Block orePrysmal = new BlockPrysmalOre().setHardness(2.0F).setResistance(15.0F)
			.setStepSound(Block.soundTypeStone);
	public static final Block blockPrysmal = new BlockPrysmalCompact().setHardness(5.0F).setResistance(30.0F)
			.setStepSound(Block.soundTypeStone);
	public static final Block blockAberrack = new BlockAberrack().setHardness(0.4F).setResistance(20.0F)
			.setStepSound(Block.soundTypeStone);
	public static final Block wightbulb = new BlockWightbulb().setHardness(0.0F).setStepSound(Block.soundTypeGrass);
    public static final Block thanabloom = new BlockThanabloom().setHardness(0.0F).setStepSound(Block.soundTypeGrass);
	public static final Block blockFluidIchor = new BlockFluidIchor().setHardness(100.0F).setLightOpacity(50);
    public static final Block immixer = new BlockImmixer().setHardness(2.0F).setResistance(20.0F)
			.setStepSound(Block.soundTypeStone);
	
	public static void init() {
		Xthuoth.assets.setBlock(wightbulb, "wightbulb");
		Xthuoth.assets.setBlock(blockAberrack, "blockAberrack");
		Xthuoth.assets.setBlock(blockFluidIchor, "fluidIchor");
		Xthuoth.assets.setBlock(orePrysmal, "orePrysmal");
		Xthuoth.assets.setBlock(blockPrysmal, "blockPrysmal");
		Xthuoth.assets.setBlock(immixer, "immixer");
	}
}