package com.aurilux.xar.world.gen.feature;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.aurilux.xar.lib.Blocks;

import cpw.mods.fml.common.IWorldGenerator;

public class CrystalGenerator implements IWorldGenerator {
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
    	int amount = 30;
    	int veinSize = 5;
    	
        for (int i = 0; i < amount; i++) {
            int randPosX = x + random.nextInt(16);
            int randPosY = random.nextInt(64);
            int randPosZ = z + random.nextInt(16);

            new WorldGenMinable(Blocks.oreCrystal.blockID, veinSize).generate(world, random, randPosX, randPosY, randPosZ);
        }
    }
    
    /**
    +   * 
    +   * This Method adds ore generation. See below what all params mean
    +   * 
    +   * @param Block which you want to spawn
    +   * @param World where you want it to spawn
    +   * @param Randomizer used for spawning
    +   * @param Start of the Chunk in X-Direction
    +   * @param Start of the Chunk in Z-Direction
    +   * @param Max X-Size where the block can spawn, normal = 16
    +   * @param Max Z-Size where the block can spawn, normal = 16
    +   * @param The vain size
    +   * @param The chance to spawn a block
    +   * @param Minimum Y-level to spawn block
    +   * @param Maximum Y-level to spawn block
    +   * 
    +   *
    public void spawnOres(Block block, World world, Random random, int chunkX, int chunkZ, int XMax, int ZMax, int vainSize, int spawnChance, int YMin, int YMax){
    	for(int i = 0; i < spawnChance; i ++){
    		int posX = chunkX + random.nextInt(XMax);
    		int posZ = chunkZ + random.nextInt(ZMax);
    		int posY = YMin + random.nextInt(YMax-YMin);
    		(new WorldGenMinable(block.blockID, vainSize)).generate(world, random, posX, posY, posZ);
        }
    }*/
}