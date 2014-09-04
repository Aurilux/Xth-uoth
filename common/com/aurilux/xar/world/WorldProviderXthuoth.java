package com.aurilux.xar.world;

import com.aurilux.xar.lib.XARWorldgen;
import com.aurilux.xar.render.SkyRendererXthuoth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderXthuoth extends WorldProvider {
	
	@Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new ChunkManagerXthuoth(XARWorldgen.aberrantBiome, 0.0f, 0.0f);
        this.isHellWorld = false;
        this.hasNoSky = true;
        this.dimensionId = XARWorldgen.DIM_ID;
        this.setSkyRenderer(new SkyRendererXthuoth());
    }

    /**
     * Return Vec3D with biome specific fog color
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float par1, float par2) {
        return Vec3.createVectorHelper(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
    }

    @Override
    protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() { return false; }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public float getCloudHeight() { return 0.0F; }
    
    @Override
    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return false;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public int getAverageGroundLevel() { return 128; }

    @Override
    public double getHorizon() { return 0.0D; }

    @Override
    public double getVoidFogYFactor() { return 0.0D; }

    @Override
    @SideOnly(Side.CLIENT)
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