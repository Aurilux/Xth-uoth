package com.aurilux.xar.world;

import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

import com.aurilux.xar.lib.WorldGen;
import com.aurilux.xar.render.SkyRendererXthuoth;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderXthuoth extends WorldProvider {
	
	@Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new ChunkManagerXthuoth(WorldGen.aberrantBiome, 0.0f, 0.0f);
        this.isHellWorld = false;
        this.hasNoSky = true;
        this.dimensionId = WorldGen.DIM_ID;
        this.setSkyRenderer(new SkyRendererXthuoth());
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Return Vec3D with biome specific fog color
     */
    public Vec3 getFogColor(float par1, float par2) {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(
                0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
    }
    
    @Override
    protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasVoidParticles(boolean var1) {
        return false;
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return false;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    public boolean doesXZShowFog(int par1, int par2) {
        return true;
    }
    
	@Override
	public String getDimensionName() {
		return "Xth'uoth";
	}
	
	@Override
    public String getWelcomeMessage() {
        return "Entering Xthuoth: the Aberrant Realm";
    }
	
	@Override
    public String getDepartMessage() {
        return "Leaving Xthuoth: the Aberrant Realm";
    }
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderXthuoth(this.worldObj, this.worldObj.getSeed());
	}
	
	@Override
	public boolean shouldMapSpin(String entity, double x, double y, double z) {
        return true;
    }
}