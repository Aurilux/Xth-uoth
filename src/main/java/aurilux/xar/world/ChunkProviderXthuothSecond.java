package aurilux.xar.world;

import aurilux.xar.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import java.util.List;
import java.util.Random;

public class ChunkProviderXthuothSecond implements IChunkProvider {
	//TODO Finish this once I've learned enough about noise
    /** Air block reference to help make the code easier to understand */
    private final Block airBlock = Blocks.air;
    /** Block used on the first pass (which generates the overall shape) of terrain generation */
    private final Block fillerBlock = ModBlocks.aberrack;
    /** Block used to form the seas */
    private final Block fluidBlock = ModBlocks.ichor;

    /** Random number generator */
    private Random rand;
    /** The world where the chunks are being generated. */
    private World worldObj;
    /** The array that stores all of the noise generators */
    private double[] noiseFields;
    /** The array that stores the biomes available for this dimension */
    private BiomeGenBase[] biomesForGeneration;

    public ChunkProviderXthuothSecond(World world, long seed) {
        worldObj = world;
        rand = new Random(seed);
    }

    @Override
    public boolean chunkExists(int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        //2^15 = 32768 = 16*16*128; 2^16 = 65536 = 16*16*256
        Block[] blocks = new Block[65536];
        byte[] meta = new byte[blocks.length];
        biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration,
            chunkX * 16, chunkZ * 16, 16, 16);

        generateTerrain(chunkX, chunkZ, blocks);

        Chunk chunk = new Chunk(this.worldObj, blocks, meta, chunkX, chunkZ);
        byte[] abyte = chunk.getBiomeArray();
        for (int k = 0; k < abyte.length; k++) {
            abyte[k] = ((byte)this.biomesForGeneration[k].biomeID);
        }
        chunk.generateSkylightMap();
        return chunk;
    }

    public void generateTerrain(int chunkX, int chunkZ, Block[] blocks) {
        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                for (int j1 = 255; j1 >= 0; j1--)
                {
                    int k1 = (l * 16 + k) * 256 + j1;
                    if (j1 < 45 || j1 > 128) {
                        blocks[k1] = fillerBlock;
                    }
                    else {
                        blocks[k1] = null;
                    }
                    //meta[k1] = 0;
                }
            }
        }

    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        return provideChunk(chunkX, chunkZ);
    }

    @Override
    public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, this.worldObj, this.worldObj.rand, chunkX, chunkZ, false));

        int k = chunkX * 16;
        int l = chunkZ * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        biomegenbase.decorate(this.worldObj, this.worldObj.rand, k, l);

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, this.worldObj, this.worldObj.rand, chunkX, chunkZ, false));
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return false;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return false;
    }

    @Override
    public String makeString() {
        return null;
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
        return null;
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_) {

    }

    @Override
    public void saveExtraData() {

    }
}