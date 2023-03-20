// package com.github.alexthe666.iceandfire.world.structure;

// import net.minecraft.core.BlockPos;
// import net.minecraft.core.Registry;
// import net.minecraft.world.level.ChunkPos;
// import net.minecraft.world.level.LevelHeightAccessor;
// import net.minecraft.world.level.StructureFeatureManager;
// import net.minecraft.world.level.WorldGenLevel;
// import net.minecraft.world.level.block.Rotation;
// import net.minecraft.world.level.chunk.ChunkGenerator;
// import net.minecraft.world.level.levelgen.GenerationStep;
// import net.minecraft.world.level.levelgen.Heightmap;
// import net.minecraft.world.level.levelgen.feature.StructureFeature;
// import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
// import net.minecraft.world.level.levelgen.structure.BoundingBox;
// import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
// import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
// import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
// import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
// import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
// import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
// import org.jetbrains.annotations.NotNull;

// import java.util.Optional;
// import java.util.Random;
// import java.util.concurrent.ThreadLocalRandom;

// public class GraveyardStructure extends StructureFeature<JigsawConfiguration> {

//     public GraveyardStructure() {
//         super(JigsawConfiguration.CODEC, GraveyardStructure::createPiecesGenerator, new PlacementProcessor());
//     }

//     public static @NotNull Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
//         ChunkGenerator chunkGenerator = context.chunkGenerator();
//         ChunkPos pos = context.chunkPos();
//         LevelHeightAccessor height = context.heightAccessor();
//         Rotation rotation = Rotation.getRandom(ThreadLocalRandom.current());
//         int xOffset = 5;
//         int yOffset = 5;
//         if (rotation == Rotation.CLOCKWISE_90) {
//             xOffset = -5;
//         } else if (rotation == Rotation.CLOCKWISE_180) {
//             xOffset = -5;
//             yOffset = -5;
//         } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
//             yOffset = -5;
//         }

//         int x = pos.getMiddleBlockX();
//         int z = pos.getMiddleBlockZ();
//         int y1 = chunkGenerator.getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, height);
//         int y2 = chunkGenerator.getFirstOccupiedHeight(x, z + yOffset, Heightmap.Types.WORLD_SURFACE_WG, height);
//         int y3 = chunkGenerator.getFirstOccupiedHeight(x + xOffset, z, Heightmap.Types.WORLD_SURFACE_WG, height);
//         int y4 = chunkGenerator.getFirstOccupiedHeight(x + xOffset, z + yOffset, Heightmap.Types.WORLD_SURFACE_WG, height);
//         int yMin = Math.min(Math.min(y1, y2), Math.min(y3, y4));
//         BlockPos blockpos = pos.getMiddleBlockPosition(yMin + 1);
//         context = Pool.replaceContext(context, new JigsawConfiguration(
//             context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
//                 .getOrCreateHolder(Pool.graveyard_pool),
//             2));

//         // All a structure has to do is call this method to turn it into a jigsaw based structure!
//         // No manual pieces class needed.
//         return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, false, false);
//     }

//     @Override
//     public GenerationStep.@NotNull Decoration step() {
//         return GenerationStep.Decoration.SURFACE_STRUCTURES;
//     }

//     public static class PlacementProcessor implements PostPlacementProcessor {
//         @Override
//         public void afterPlace(@NotNull WorldGenLevel level, @NotNull StructureFeatureManager structureFeatureManager, @NotNull ChunkGenerator generator, @NotNull Random random, @NotNull BoundingBox box, @NotNull ChunkPos pos, PiecesContainer container) {
//             container.calculateBoundingBox();
//         }
//     }
// }
