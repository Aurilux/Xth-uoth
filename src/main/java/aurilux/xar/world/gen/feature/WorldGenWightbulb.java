package aurilux.xar.world.gen.feature;

import aurilux.xar.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenWightbulb extends WorldGenerator {
	
	private final int amount = 20;
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		for (int i = 0; i < amount; ++i) {
            int xCoord = x + random.nextInt(8) - random.nextInt(8);
            int yCoord = y;
            int zCoord = z + random.nextInt(8) - random.nextInt(8);
            
            //is the current block air and is there an adjacent block of water?
            if (world.isAirBlock(xCoord, y, zCoord) && 
            		  (world.getBlock(xCoord - 1, yCoord - 1, zCoord).getMaterial() == Material.water
            		|| world.getBlock(xCoord + 1, yCoord - 1, zCoord).getMaterial() == Material.water
            		|| world.getBlock(xCoord, yCoord - 1, zCoord - 1).getMaterial() == Material.water
            		|| world.getBlock(xCoord, yCoord - 1, zCoord + 1).getMaterial() == Material.water)
            		&& ModBlocks.wightbulb.canBlockStay(world, xCoord, yCoord, zCoord)) {
            	world.setBlock(xCoord, yCoord, zCoord, ModBlocks.wightbulb, 0, 2);
            }
        }

        return true;
	}
}