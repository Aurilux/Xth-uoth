package com.aurilux.xar.world.biome;

import com.aurilux.xar.entity.monster.EntityBlighter;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenAberrant extends BiomeGenBase {
	public BiomeGenAberrant(int id) {
		super(id, true);
		this.setBiomeName("Aberrant");
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
        return getModdedBiomeDecorator(new BiomeDecoratorXthuoth());
    }
}