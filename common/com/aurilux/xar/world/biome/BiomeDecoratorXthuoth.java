package com.aurilux.xar.world.biome;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CUSTOM;

import com.aurilux.xar.world.gen.feature.WorldGenWightBulb;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorXthuoth extends BiomeDecorator {
	//WorldGens
	private WorldGenerator wightBulbGen;
	
	//Gen amount constants
	private int wightBulbPerChunk = 50;
	
	public BiomeDecoratorXthuoth(BiomeGenBase biomeGenBase) {
		super(biomeGenBase);
		
		//WorldGen inits
		wightBulbGen = new WorldGenWightBulb();
	}
	
	@Override
	protected void decorate() {
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
		//TODO add some way to generate rifts
		
        int i; //the for-loop iterator (counter); declared here for re-usability on subsequent world-gen loops
        int x; //the randomly-generated x-Coord; declared here for re-usability on subsequent world-gen loops
        int y; //the randomly-generated y-Coord; declared here for re-usability on subsequent world-gen loops
        int z; //the randomly-generated z-Coord; declared here for re-usability on subsequent world-gen loops
        
        //the event firing is here just for completeness though it may never be used
        boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, CUSTOM);
        for (i = 0; doGen && i < wightBulbPerChunk; ++i)
        {
            x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            y = this.randomGenerator.nextInt(128);
            z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.wightBulbGen.generate(this.currentWorld, this.randomGenerator, x, y, z);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}
}