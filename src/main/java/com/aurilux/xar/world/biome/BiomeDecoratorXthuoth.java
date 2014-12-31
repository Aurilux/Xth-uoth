package com.aurilux.xar.world.biome;

import com.aurilux.xar.world.gen.feature.WorldGenWightbulb;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CUSTOM;

public class BiomeDecoratorXthuoth extends BiomeDecorator {
	//WorldGens
	private WorldGenerator wightBulbGen;
	
	//Gen amount constants. NOTE: These are not the minimum amounts for each block, just iterations. You will often find much less
	//than the number provided.
	private int wightBulbPerChunk = 50;
	
	public BiomeDecoratorXthuoth() {
		super();
		
		//WorldGen inits
		wightBulbGen = new WorldGenWightbulb();
	}

	@Override
    protected void genDecorations(BiomeGenBase biome) {
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
		//TODO will need to update most, if not all, randomly-generated coordinates once I make the new terrain gen
		
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