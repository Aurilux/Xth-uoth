package com.aurilux.xar.world.gen.feature;

import com.aurilux.xar.entity.EntityRift;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class CorruptionGenerator implements IWorldGenerator {
	
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
	        case -1:
	            generateNether(world, random, chunkX * 16, chunkZ * 16); break;
	        case 0:
	        	generateSurface(world, random, chunkX * 16, chunkZ * 16); break;
	        case 1:
	            generateEnd(world, random, chunkX * 16, chunkZ * 16); break;
        }
    }

    private void generateEnd(World world, Random random, int x, int z) {

    }

    private void generateNether(World world, Random random, int x, int z) {
    }

    private void generateSurface(World world, Random random, int x, int z) {
    	//TODO decrease the chance of a rift spawning (1/100?)
    	//spawnChance generates a number between 0 - 9
    	int spawnChance = random.nextInt(10);
    	//there is a 10% chance a rift will spawn
    	if (spawnChance > 8) {
            int randPosX = x + random.nextInt(16); //generates a number between 0 - 15 + the chunk
            int randPosY = 32 + random.nextInt(48); //generates a number between 32 - 79
            int randPosZ = z + random.nextInt(16); //generates a number between 0 - 15 + the chunk
            
            while (!world.isAirBlock(randPosX, randPosY, randPosZ) && !world.isAirBlock(randPosX, randPosY + 1, randPosZ) && randPosY < 129) {
            	randPosY++;
            }
            
            if (randPosY > 129) return;
            world.spawnEntityInWorld(new EntityRift(world, randPosX, randPosY + 1, randPosZ));
    	}
    }
}
