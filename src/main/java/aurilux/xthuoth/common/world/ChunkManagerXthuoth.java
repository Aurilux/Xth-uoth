package aurilux.xthuoth.common.world;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChunkManagerXthuoth extends WorldChunkManager {
	
	private BiomeGenBase biomeGenerator;
    private float temperature;
    private float rainfall;

    public ChunkManagerXthuoth(BiomeGenBase biome, float temp, float rain) {
        this.biomeGenerator = biome;
        this.temperature = temp;
        this.rainfall = rain;
        allowedBiomes.clear();
        allowedBiomes.add(biome);
    }

    /**
     * Returns the BiomeGenBase related to the x, z position on the world.
     */
    public BiomeGenBase getBiomeGenAt(int xCoord, int zCoord) {
    	//since there is only one biome, there is no need to check the coords
        return this.biomeGenerator;
    }

    /** Returns an array of biomes for the location input. */
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomeArray, int xCoord, int yCoord, int width, int length) {
        if (biomeArray == null || biomeArray.length < width * length) {
            biomeArray = new BiomeGenBase[width * length];
        }

        Arrays.fill(biomeArray, 0, width * length, biomeGenerator);
        return biomeArray;
    }

    /** Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length */
    public float[] getTemperatures(float[] floatArray, int xCoord, int yCoord, int width, int length) {
        if (floatArray == null || floatArray.length < width * length) {
            floatArray = new float[width * length];
        }

        Arrays.fill(floatArray, 0, width * length, temperature);
        return floatArray;
    }

    /** Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length. */
    public float[] getRainfall(float[] floatArray, int xCoord, int zCoord, int width, int length) {
        if (floatArray == null || floatArray.length < width * length) {
        	floatArray = new float[width * length];
        }

        Arrays.fill(floatArray, 0, width * length, rainfall);
        return floatArray;
    }

    /** Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the WorldChunkManager */
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomeArray, int xCoord, int zCoord, int width, int length) {
        if (biomeArray == null || biomeArray.length < width * length) {
            biomeArray = new BiomeGenBase[width * length];
        }

        Arrays.fill(biomeArray, 0, width * length, biomeGenerator);
        return biomeArray;
    }

    /** Return a list of biomes for the specified blocks */
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomeArray, int xCoord, int yCoord, int width, int length, boolean cacheFlag) {
        return loadBlockGeneratorData(biomeArray, xCoord, yCoord, width, length);
    }

    /** Finds a valid position within a range, that is in one of the listed biomes. Searches {xCoord,zCoord} +-yCoord blocks. */
    @SuppressWarnings("rawtypes")
	public ChunkPosition findBiomePosition(int xCoord, int yCoord, int zCoord, List list, Random rand) {
        return list.contains(biomeGenerator) ? new ChunkPosition(xCoord - zCoord + rand.nextInt(zCoord * 2 + 1), 0, yCoord - zCoord + rand.nextInt(zCoord * 2 + 1)) : null;
    }

    /** Checks given Chunk's Biomes against List of allowed ones */
    @SuppressWarnings("rawtypes")
	public boolean areBiomesViable(int xCoord, int yCoord, int zCoord, List list) {
        return list.contains(biomeGenerator);
    }
}