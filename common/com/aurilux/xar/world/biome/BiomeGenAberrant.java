package com.aurilux.xar.world.biome;

import com.aurilux.xar.entity.monster.EntityBlighter;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenAberrant extends BiomeGenBase {

	//par1 the biome ID
	public BiomeGenAberrant(int id) {
		super(id, true);
		this.setBiomeName("Aberrant");
		this.topBlock = Blocks.bedrock;
		//this.fillerBlock = 
		//this.theBiomeDecorator = this.createBiomeDecorator();
		setDisableRain();
		
		//clear all of the spawn lists
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        
        //add native entities
        EntityRegistry.addSpawn(EntityBlighter.class, 5, 1, 2, EnumCreatureType.monster, this);
	}
	
	@Override
    public BiomeDecorator createBiomeDecorator() {
        return getModdedBiomeDecorator(new BiomeDecoratorXthuoth(this));
    }
}