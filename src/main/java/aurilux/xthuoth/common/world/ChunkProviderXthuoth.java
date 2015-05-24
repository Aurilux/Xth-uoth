package aurilux.xthuoth.common.world;

import aurilux.xthuoth.common.init.ModBlocks;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;

public class ChunkProviderXthuoth implements IChunkProvider {
    /** Helper variable to make the code easier to understand */
    private final Block airBlockID = Blocks.air;
    /** ID of the block used on the first pass (which generates the overall shape) of terrain generation */
    private final Block fillerBlockID = ModBlocks.blockAberrack;
    /** ID of the block used to form the seas */
    private final Block fluidBlockID = ModBlocks.blockFluidIchor;
    /** Random number generator */
    private Random xarRNG;
    // Constant variables used in noise initialization
    private final double xzMajorScale = 684.412D;
    private final double yMajorScale =  2053.236D; //2053.236 = 684.412 * 3
    private final double xLowerScale =  1.0D;
    //private final double xMidScale =    1.0D;
    private final double xUpperScale =  1.0D;
    private final double yLowerScale =  1.0D;
    //private final double yMidScale =    1.0D;
    private final double yUpperScale =  1.0D;
    private final double zLowerScale =  1.0D;
    //private final double zMidScale =    1.0D;
    private final double zUpperScale =  1.0D;
    private final double xSlopeDiv =    80.0D;
    private final double ySlopeDiv =    60.0D;
    private final double zSlopeDiv =    80.0D;
    
    //Constant variables used in terrain generation
    private final double genYScale =  .125D;
    private final double genXZScale = .25D;
    
    /** The biomes that are used to generate the chunk */
    //private BiomeGenBase[] biomesForGeneration;
    /** A NoiseGeneratorOctaves used in generating nether terrain */
    private NoiseGenerator netherNoiseGen1; //first dimension?
    private NoiseGenerator netherNoiseGen2; //second dimension?
    private NoiseGenerator netherNoiseGen3; //third dimension?
    
    public NoiseGenerator netherNoiseGen6;
    public NoiseGenerator netherNoiseGen7;

    /** The world where the chunks are being generated. */
    private World world;
    /** The array that stores all of the noise generators */
    private double[] noiseFields;
    
    private MapGenBase netherCaveGenerator = new MapGenCavesHell();
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    {
        netherCaveGenerator = TerrainGen.getModdedMapGen(netherCaveGenerator, NETHER_CAVE);
    }

    public ChunkProviderXthuoth(World world, long seed) {
        this.world = world;
        xarRNG = new Random(seed);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.xarRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.xarRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.xarRNG, 8);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.xarRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.xarRNG, 16);

        NoiseGenerator[] noiseGens = {netherNoiseGen1, netherNoiseGen2, netherNoiseGen3, netherNoiseGen6, netherNoiseGen7};
        noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.xarRNG, noiseGens);
        this.netherNoiseGen1 = noiseGens[0];
        this.netherNoiseGen2 = noiseGens[1];
        this.netherNoiseGen3 = noiseGens[2];
        this.netherNoiseGen6 = noiseGens[3];
        this.netherNoiseGen7 = noiseGens[4];
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
    public Chunk provideChunk(int xCoord, int zCoord) {
        this.xarRNG.setSeed((long)xCoord * 341873128712L + (long)zCoord * 132897987541L);
        //32768 = 2^15 and 16x16x128 (x, z, and y respectively)
        //Although the height limit is 256, world land generation does not go above 128
        Block[] terrainBlocks = new Block[32768]; //32768 = 16*16*128; 65536 = 16*16*256
        this.generateAberrantTerrain(xCoord, zCoord, terrainBlocks);
        //TODO uncomment this when I actually have something to replace
        //this.replaceBlocksForBiome(xCoord, zCoord, terrainBlocks);
        //Do other terrain gen here i.e. ravines, caves, etc
        //nethercavegen.generate(this, world, xCoord, yCoord, terrainBlocks);
        
        Chunk chunk = new Chunk(world, terrainBlocks, xCoord, zCoord);
        BiomeGenBase[] biomeGen = world.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, xCoord * 16, zCoord * 16, 16, 16);
        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; i++)  {
            biomeArray[i] = (byte)biomeGen[i].biomeID;
        }
        chunk.resetRelightChecks();
        return chunk;
    }

    /** Generates the overall shape of the terrain in Xth'uoth including the 'sea' */
    public void generateAberrantTerrain(int xCoord, int yCoord, Block[] terrainBlocks) {
        byte b0 = 4;
        byte seaLevel = 32;
        double solidCutoff = 0.0D;
        int p = b0 + 1;
        byte b2 = 17;
        int w = b0 + 1;
        noiseFields = initializeNoiseField(noiseFields, xCoord * b0, 0, yCoord * b0, p, b2, w);
        
        //The combination of these six for-loops generates 32768 iterations, the size of the terrainBlocks array.
        //The outermost three for-loops are a combined 256 iterations, while the innermost ones are 128
        //but why split it up as such?
        for (int i = 0; i < b0; i++) {
            for (int j = 0; j < b0; j++) {
                for (int k = 0; k < 16; k++) {
                	//unskewed coords?
                    double d1 = noiseFields[ ((i + 0) * w + (j + 0)) * b2 + (k + 0)];
                    double d2 = noiseFields[ ((i + 0) * w + (j + 1)) * b2 + (k + 0)];
                    double d3 = noiseFields[ ((i + 1) * w + (j + 0)) * b2 + (k + 0)];
                    double d4 = noiseFields[ ((i + 1) * w + (j + 1)) * b2 + (k + 0)];
                    //The x,y,z distances from the cell origin?
                    double d5 = (noiseFields[((i + 0) * w + (j + 0)) * b2 + (k + 1)] - d1) * genYScale;
                    double d6 = (noiseFields[((i + 0) * w + (j + 1)) * b2 + (k + 1)] - d2) * genYScale;
                    double d7 = (noiseFields[((i + 1) * w + (j + 0)) * b2 + (k + 1)] - d3) * genYScale;
                    double d8 = (noiseFields[((i + 1) * w + (j + 1)) * b2 + (k + 1)] - d4) * genYScale;

                    for (int l = 0; l < 8; l++) {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * genXZScale;
                        double d13 = (d4 - d2) * genXZScale;

                        for (int m = 0; m < 4; m++) {
                        	//The current position(index) in the byte array, based on where in the 5 for-loops we are
                            int position = m + i * 4 << 11 | 0 + j * 4 << 7 | k * 8 + l;
                            short maxHeight = 128;
                            double d15 = d10;
                            double d16 = (d11 - d10) * genXZScale;

                            for (int n = 0; n < 4; n++) {
                                Block blockID = airBlockID;
                                
                                //This will generate the lakes of liquid
                                //as this is a mimic of the nether, it will replace what would have been lava
                                if (k * 8 + l < seaLevel) {
                                    blockID = fluidBlockID;
                                }
                                
                                //This is will generate the most abundant block
                                //as this is a mimic of the nether, it will replace what would have been netherrack
                                if (d15 > solidCutoff)  {
                                    blockID = fillerBlockID;
                                }

                                terrainBlocks[position] = blockID;
                                position += maxHeight;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    /** Replaces blocks depending on the biome */
    public void replaceBlocksForBiome(int xCoord, int zCoord, Block[] terrainBlocks) {
        //MinecraftForge.EVENT_BUS.post(event);
        //if (event.getResult() == Result.DENY) return;
    }

    /** loads or generates the chunk at the chunk location specified */
    @Override
    public Chunk loadChunk(int xCoord, int zCoord) {
        return this.provideChunk(xCoord, zCoord);
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] noiseArray, int xCoord, int yCoord, int zCoord, int length, int width, int height) {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, noiseArray, xCoord, yCoord, zCoord, length, width, height);
        //TODO this is where I need to take into account biome height ranges
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.noisefield;
        if (noiseArray == null) {
            noiseArray = new double[length * width * height];
        }
        
        noiseData1 = ((NoiseGeneratorOctaves) netherNoiseGen3).generateNoiseOctaves(noiseData1, xCoord, yCoord, zCoord, length, width, height, xzMajorScale / xSlopeDiv,   yMajorScale / ySlopeDiv,   xzMajorScale / zSlopeDiv);
        noiseData2 = ((NoiseGeneratorOctaves) netherNoiseGen1).generateNoiseOctaves(noiseData2, xCoord, yCoord, zCoord, length, width, height, xzMajorScale * xLowerScale, yMajorScale * yLowerScale, xzMajorScale * zLowerScale);
        noiseData3 = ((NoiseGeneratorOctaves) netherNoiseGen2).generateNoiseOctaves(noiseData3, xCoord, yCoord, zCoord, length, width, height, xzMajorScale * xUpperScale, yMajorScale * yUpperScale, xzMajorScale * zUpperScale);
        noiseData4 = ((NoiseGeneratorOctaves) netherNoiseGen6).generateNoiseOctaves(noiseData4, xCoord, yCoord, zCoord, length, 1,     height, 1.0D,                       0.0D,                      1.0D);
        noiseData5 = ((NoiseGeneratorOctaves) netherNoiseGen7).generateNoiseOctaves(noiseData5, xCoord, yCoord, zCoord, length, 1,     height, 100.0D,                     0.0D,                      100.0D);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[width];
        
        for (int i = 0; i < width; i++) {
            adouble1[i] = Math.cos((double)i * Math.PI * 6.0D / (double)width) * 2.0D;
            double d2 = (double)i;

            if (i > width / 2) {
                d2 = (double)(width - 1 - i);
            }

            if (d2 < 4.0D) {
                d2 = 4.0D - d2;
                adouble1[i] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;

                if (d3 > 1.0D) {
                    d3 = 1.0D;
                }

                double d4 = 0.0D;
                double d5 = this.noiseData5[l1] / 8000.0D;

                if (d5 < 0.0D) {
                    d5 = -d5;
                }

                d5 = d5 * 3.0D - 3.0D;

                if (d5 < 0.0D) {
                    d5 /= 2.0D;

                    if (d5 < -1.0D)
                    {
                        d5 = -1.0D;
                    }

                    d5 /= 1.4D;
                    d5 /= 2.0D;
                    d3 = 0.0D;
                }
                else {
                    if (d5 > 1.0D) {
                        d5 = 1.0D;
                    }

                    d5 /= 6.0D;
                }

                d3 += 0.5D;
                d5 = d5 * (double)width / 16.0D;
                l1++;

                for (int k = 0; k < width; ++k) {
                    double d6 = 0.0D;
                    double d7 = adouble1[k];
                    double d8 = this.noiseData2[k1] / 512.0D;
                    double d9 = this.noiseData3[k1] / 512.0D;
                    double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D) {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D) {
                        d6 = d9;
                    }
                    else {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    double d11;

                    if (k > width - 4) {
                        d11 = (double)((float)(k - (width - 4)) / 3.0F);
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    if ((double)k < d4) {
                        d11 = (d4 - (double)k) / 4.0D;

                        if (d11 < 0.0D) {
                            d11 = 0.0D;
                        }

                        if (d11 > 1.0D) {
                            d11 = 1.0D;
                        }

                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    noiseArray[k1] = d6;
                    k1++;
                }
            }
        }
        return noiseArray;
    }

    /** Checks to see if a chunk exists at x, y */
    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    /** Populates chunk with ores, plants, etc */
    public void populate(IChunkProvider provider, int xCoord, int zCoord) {
        BlockSand.fallInstantly = true;
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, world, xarRNG, xCoord, zCoord, false));

        int chunkX = xCoord * 16;
        int chunkZ = zCoord * 16;
        BiomeGenBase biomeGen = world.getBiomeGenForCoords(chunkX + 16, chunkZ + 16);
        biomeGen.decorate(world, world.rand, chunkX, chunkZ);
        
        //Add structure generation here i.e. villages, mineshafts, strongholds, etc
        
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, world, xarRNG, xCoord, zCoord, false));
        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    @Override
    public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressUpdate){
        return true;
    }

    /** Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk. */
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    /** Returns if the IChunkProvider supports saving. */
    @Override
    public boolean canSave() {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString() {
        return "XthuothRandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @SuppressWarnings("rawtypes")
	public List getPossibleCreatures(EnumCreatureType type, int xCoord, int yCoord, int zCoord) {
        BiomeGenBase biomeGen = world.getBiomeGenForCoords(xCoord, zCoord);
        return biomeGen == null ? null : biomeGen.getSpawnableList(type);
    }

	@Override
	public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5) {
		// NO-OP
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		// NO-OP
		return 0;
	}

	@Override
	public void recreateStructures(int var1, int var2) {
		// NO-OP
	}

	@Override
	public void saveExtraData() {
		// NO-OP
	}
}