package com.github.alexthe666.iceandfire.world.structure;

// import com.github.alexthe666.citadel.repack.jaad.mp4.api.Track.Codec;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

// import static com.github.alexthe666.iceandfire.world.structure.Pool.dread_pool;
// import static com.github.alexthe666.iceandfire.world.structure.Pool.replaceContext;

public class DreadMausoleumStructure extends Structure {

    // A custom codec that changes the size limit for our code_structure_sky_fan.json's config to not be capped at 7.
    // With this, we can have a structure with a size limit up to 30 if we want to have extremely long branches of pieces in the structure.
    public static final Codec<DreadMausoleumStructure> CODEC = RecordCodecBuilder.<DreadMausoleumStructure>mapCodec(instance ->
            instance.group(DreadMausoleumStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                    StructureSpawnOverride.CODEC.optionalFieldOf("spawn_overrides").forGetter(structure -> structure.spawnOverrides)
            ).apply(instance, DreadMausoleumStructure::new)).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final Optional<StructureSpawnOverride> spawnOverrides;

    public DreadMausoleumStructure(Structure.StructureSettings config,
                                   Holder<StructureTemplatePool> startPool,
                                   Optional<ResourceLocation> startJigsawName,
                                   int size,
                                   HeightProvider startHeight,
                                   Optional<Heightmap.Types> projectStartToHeightmap,
                                   int maxDistanceFromCenter,
                                   Optional<StructureSpawnOverride> spawnOverrides) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.spawnOverrides = spawnOverrides;
    }

    /*
     * This is where extra checks can be done to determine if the structure can spawn here.
     * This only needs to be overridden if you're adding additional spawn conditions.
     *
     * Fun fact, if you set your structure separation/spacing to be 0/1, you can use
     * extraSpawningChecks to return true only if certain chunk coordinates are passed in
     * which allows you to spawn structures only at certain coordinates in the world.
     *
     * Basically, this method is used for determining if the land is at a suitable height,
     * if certain other structures are too close or not, or some other restrictive condition.
     *
     * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village.
     * (Bedrock Edition seems to not have the same check)
     *
     * If you are doing Nether structures, you'll probably want to spawn your structure on top of ledges.
     * Best way to do that is to use getBaseColumn to grab a column of blocks at the structure's x/z position.
     * Then loop through it and look for land with air above it and set blockpos's Y value to it.
     * Make sure to set the final boolean in JigsawPlacement.addPieces to false so
     * that the structure spawns at blockpos's y value instead of placing the structure on the Bedrock roof!
     *
     * Also, please for the love of god, do not do dimension checking here.
     * If you do and another mod's dimension is trying to spawn your structure,
     * the locate command will make minecraft hang forever and break the game.
     * Use the biome tags for where to spawn the structure and users can datapack
     * it to spawn in specific biomes that aren't in the dimension they don't like if they wish.
     */
    private static boolean extraSpawningChecks(Structure.GenerationContext context) {
        // Grabs the chunk position we are at
        ChunkPos chunkpos = context.chunkPos();

        int spawnRoll = context.random().nextInt(IafConfig.generateMausoleumChance);
        if (spawnRoll != 0) {
            return false;
        }

        // Checks to make sure our structure does not spawn above land that's higher than y = 150
        // to demonstrate how this method is good for checking extra conditions for spawning
        boolean belowY150 = context.chunkGenerator().getFirstOccupiedHeight(
                chunkpos.getMinBlockX(),
                chunkpos.getMinBlockZ(),
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                context.heightAccessor(),
                context.randomState()) < 150;

        return belowY150;
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {

        // Check if the spot is valid for our structure. This is just as another method for cleanness.
        // Returning an empty optional tells the game to skip this spot as it will not generate the structure.
        if (!DreadMausoleumStructure.extraSpawningChecks(context)) {
            return Optional.empty();
        }

        // Set's our spawning blockpos's y offset to be 60 blocks up.
        // Since we are going to have heightmap/terrain height spawning set to true further down, this will make it so we spawn 60 blocks above terrain.
        // If we wanted to spawn on ocean floor, we would set heightmap/terrain height spawning to false and the grab the y value of the terrain with OCEAN_FLOOR_WG heightmap.
        int startY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));

        // Turns the chunk coordinates into actual coordinates we can use. (Gets corner of that chunk)
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), startY, chunkPos.getMinBlockZ());

        Optional<Structure.GenerationStub> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context, // Used for JigsawPlacement to get all the proper behaviors done.
                        this.startPool, // The starting pool to use to create the structure layout from
                        this.startJigsawName, // Can be used to only spawn from one Jigsaw block. But we don't need to worry about this.
                        this.size, // How deep a branch of pieces can go away from center piece. (5 means branches cannot be longer than 5 pieces from center piece)
                        blockPos, // Where to spawn the structure.
                        false, // "useExpansionHack" This is for legacy villages to generate properly. You should keep this false always.
                        this.projectStartToHeightmap, // Adds the terrain height's y value to the passed in blockpos's y value. (This uses WORLD_SURFACE_WG heightmap which stops at top water too)
                        // Here, blockpos's y value is 60 which means the structure spawn 60 blocks above terrain height.
                        // Set this to false for structure to be place only at the passed in blockpos's Y value instead.
                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
                        this.maxDistanceFromCenter); // Maximum limit for how far pieces can spawn from center. You cannot set this bigger than 128 or else pieces gets cutoff.

        /*
         * Note, you are always free to make your own JigsawPlacement class and implementation of how the structure
         * should generate. It is tricky but extremely powerful if you are doing something that vanilla's jigsaw system cannot do.
         * Such as for example, forcing 3 pieces to always spawn every time, limiting how often a piece spawns, or remove the intersection limitation of pieces.
         */

        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return structurePiecesGenerator;
    }

    @Override
    public StructureType<?> type() {
        return IafStructureTypes.DREAD_MAUSOLEUM_STRUCTURE.get(); // Helps the game know how to turn this structure back to json to save to chunks
    }
}

// public DreadMausoleumStructure() {
//     super(JigsawConfiguration.CODEC, DreadMausoleumStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
// }

// public static JigsawConfiguration config(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
//     return new JigsawConfiguration(
//         context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).getOrCreateHolder(dread_pool),
//         5
//     );
// }

// public static @NotNull Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
//     if (!context.validBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG) || !IafConfig.generateMausoleums)
//         return Optional.empty();

//     context = replaceContext(context, config(context));

//     ChunkGenerator chunkGenerator = context.chunkGenerator();
//     ChunkPos pos = context.chunkPos();
//     LevelHeightAccessor height = context.heightAccessor();
//     Rotation rotation = Rotation.getRandom(ThreadLocalRandom.current());
//     int xOffset = 5;
//     int yOffset = 5;
//     if (rotation == Rotation.CLOCKWISE_90) {
//         xOffset = -5;
//     } else if (rotation == Rotation.CLOCKWISE_180) {
//         xOffset = -5;
//         yOffset = -5;
//     } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
//         yOffset = -5;
//     }

//     int x = pos.getMiddleBlockX();
//     int z = pos.getMiddleBlockZ();
//     int y1 = chunkGenerator.getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, height);
//     int y2 = chunkGenerator.getFirstOccupiedHeight(x, z + yOffset, Heightmap.Types.WORLD_SURFACE_WG, height);
//     int y3 = chunkGenerator.getFirstOccupiedHeight(x + xOffset, z, Heightmap.Types.WORLD_SURFACE_WG, height);
//     int y4 = chunkGenerator.getFirstOccupiedHeight(x + xOffset, z + yOffset, Heightmap.Types.WORLD_SURFACE_WG, height);
//     int yMin = Math.min(Math.min(y1, y2), Math.min(y3, y4));
//     BlockPos blockpos = pos.getMiddleBlockPosition(yMin + 1);

//     // All a structure has to do is call this method to turn it into a jigsaw based structure!
//     // No manual pieces class needed.

//     return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, false, false);
// }

// @Override
// public GenerationStep.@NotNull Decoration step() {
//     return GenerationStep.Decoration.SURFACE_STRUCTURES;
// }

// @Override
// public boolean canGenerate(@NotNull RegistryAccess p_197172_, @NotNull ChunkGenerator p_197173_, @NotNull BiomeSource p_197174_, @NotNull StructureManager p_197175_, long p_197176_, @NotNull ChunkPos p_197177_, @NotNull JigsawConfiguration p_197178_, @NotNull LevelHeightAccessor p_197179_, @NotNull Predicate<Holder<Biome>> p_197180_) {
//     return super.canGenerate(p_197172_, p_197173_, p_197174_, p_197175_, p_197176_, p_197177_, p_197178_, p_197179_, p_197180_);
// }
// }
