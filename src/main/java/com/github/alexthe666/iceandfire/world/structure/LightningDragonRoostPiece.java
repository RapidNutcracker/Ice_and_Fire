package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.util.HomePosition;
import com.github.alexthe666.iceandfire.event.WorldGenUtils;
import com.github.alexthe666.iceandfire.world.IafStructurePieceTypes;
import com.github.alexthe666.iceandfire.world.gen.WorldGenRoostArch;
import com.github.alexthe666.iceandfire.world.gen.WorldGenRoostBoulder;
import com.github.alexthe666.iceandfire.world.gen.WorldGenRoostPile;
import com.github.alexthe666.iceandfire.world.gen.WorldGenRoostTreasurePile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class LightningDragonRoostPiece extends ScatteredFeaturePiece {

    public static final int RADIUS = 12;

    private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    private static boolean isDragonMale;
    public static ResourceLocation DRAGON_CHEST = new ResourceLocation("iceandfire", "chest/lightning_dragon_roost");

    private int radius = 0;
    private boolean hasPlacedDragon;
    private boolean isDragonAlive;
    private boolean hasPlacedBase;
    private int numberOfPlacedChests;
    private int numberOfPlacedTreasurePiles;


    public LightningDragonRoostPiece(RandomSource pRandom, int pX, int pZ) {
        super(IafStructurePieceTypes.LIGHTNING_DRAGON_ROOST_PIECE.get(), pX, 64, pZ, 35, 10, 35, getRandomHorizontalDirection(pRandom));
    }

    public LightningDragonRoostPiece(StructurePieceSerializationContext context, CompoundTag pTag) {
        super(IafStructurePieceTypes.LIGHTNING_DRAGON_ROOST_PIECE.get(), pTag);
        this.radius = pTag.getInt("radius");
        this.hasPlacedDragon = pTag.getBoolean("hasPlacedDragon");
        this.isDragonAlive = pTag.getBoolean("isDragonAlive");
        this.numberOfPlacedTreasurePiles = pTag.getInt("numberOfPlacedTreasurePiles");
        this.numberOfPlacedChests = pTag.getInt("numberOfPlacedChests");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
        super.addAdditionalSaveData(pContext, pTag);
        pTag.putInt("radius", this.radius);
        pTag.putBoolean("hasPlacedDragon", this.hasPlacedDragon);
        pTag.putBoolean("isDragonAlive", this.isDragonAlive);
        pTag.putInt("numberOfPlacedTreasurePiles", this.numberOfPlacedTreasurePiles);
        pTag.putInt("numberOfPlacedChests", this.numberOfPlacedChests);
    }

    private void transformState(LevelAccessor world, BlockPos blockpos, BlockState state) {
        float hardness = state.getDestroySpeed(world, blockpos);
        if (hardness != -1.0F) {
            if (state.getBlock() instanceof BaseEntityBlock) {
                return;
            }
            if (state.getMaterial() == Material.GRASS) {
                world.setBlock(blockpos, IafBlockRegistry.CRACKLED_GRASS.get().defaultBlockState(), 2);
            } else if (state.getMaterial() == Material.DIRT && state.getBlock() == Blocks.DIRT) {
                world.setBlock(blockpos, IafBlockRegistry.CRACKLED_DIRT.get().defaultBlockState(), 2);
            } else if (state.getMaterial() == Material.DIRT && state.getBlock() == Blocks.GRAVEL) {
                world.setBlock(blockpos, IafBlockRegistry.CRACKLED_GRAVEL.get().defaultBlockState(), 2);
            } else if (state.getMaterial() == Material.STONE && (state.getBlock() == Blocks.COBBLESTONE || state.getBlock().getDescriptionId().contains("cobblestone"))) {
                world.setBlock(blockpos, IafBlockRegistry.CRACKLED_COBBLESTONE.get().defaultBlockState(), 2);
            } else if (state.getMaterial() == Material.STONE && state.getBlock() != IafBlockRegistry.CRACKLED_COBBLESTONE.get()) {
                world.setBlock(blockpos, IafBlockRegistry.CRACKLED_STONE.get().defaultBlockState(), 2);
            } else if (state.getBlock() == Blocks.DIRT_PATH) {
                world.setBlock(blockpos, IafBlockRegistry.CRACKLED_DIRT_PATH.get().defaultBlockState(), 2);
            } else if (state.getMaterial() == Material.WOOD) {
                world.setBlock(blockpos, IafBlockRegistry.ASH.get().defaultBlockState(), 2);
            } else if (state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.PLANT) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    @Override
    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {

        if (this.updateAverageGroundHeight(pLevel, pBox, 0)) {

            if (radius == 0) {
                radius = RADIUS + pRandom.nextInt(8);
            }

            BlockPos finalPosition = pLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pPos);

            isDragonMale = pRandom.nextBoolean();

            if (!hasPlacedDragon) {
                EntityDragonBase dragon = IafEntityRegistry.LIGHTNING_DRAGON.get().create(pLevel.getLevel());
                dragon.setGender(isDragonMale);
                dragon.growDragon(40 + radius);
                dragon.setAgingDisabled(true);
                dragon.setHealth(dragon.getMaxHealth());
                dragon.setVariant(new Random().nextInt(4));
                dragon.absMoveTo(finalPosition.getX() + 0.5, finalPosition.getY() + 1.5, finalPosition.getZ() + 0.5, pRandom.nextFloat() * 360, 0);
                dragon.homePos = new HomePosition(finalPosition, pLevel.getLevel());
                dragon.hasHomePosition = true;
                dragon.setHunger(50);
                pLevel.addFreshEntity(dragon);
                this.hasPlacedDragon = true;
            }

            if (!hasPlacedBase) {
                {
                    int j = radius;
                    int k = 2;
                    int l = radius;
                    float f = (j + k + l) * 0.333F + 0.5F;
                    float fSquared = f * f;

                    BlockPos.betweenClosedStream(finalPosition.offset(-j, -k, -l), finalPosition.offset(j, 0, l)).map(BlockPos::immutable).forEach(blockPos -> {
                        int yAdd = blockPos.getY() - finalPosition.getY();
                        double squaredDistance = blockPos.distSqr(finalPosition);

                        if (squaredDistance <= fSquared && yAdd < 2 + pRandom.nextInt(k) && !pLevel.isEmptyBlock(blockPos.below())) {
                            if (pLevel.isEmptyBlock(blockPos.above()))
                                pLevel.setBlock(blockPos, IafBlockRegistry.CRACKLED_GRASS.get().defaultBlockState(), 2);
                            else
                                pLevel.setBlock(blockPos, IafBlockRegistry.CRACKLED_DIRT.get().defaultBlockState(), 2);
                        }
                    });
                }
                {
                    int j = radius;
                    int k = (radius / 3);
                    int l = radius;
                    float f = (j + k + l) * 0.333F + 0.5F;
                    BlockPos.betweenClosedStream(finalPosition.offset(-j, -k, -l), finalPosition.offset(j, 1, l)).map(BlockPos::immutable).forEach(blockPos -> {
                        if (blockPos.distSqr(finalPosition) < f * f) {
                            pLevel.setBlock(blockPos, pRandom.nextBoolean() ? IafBlockRegistry.CRACKLED_GRAVEL.get().defaultBlockState() : IafBlockRegistry.CRACKLED_DIRT.get().defaultBlockState(), 2);
                        } else if (blockPos.distSqr(finalPosition) == f * f) {
                            pLevel.setBlock(blockPos, IafBlockRegistry.CRACKLED_COBBLESTONE.get().defaultBlockState(), 2);
                        }
                    });
                }
                {
                    int j = radius - 2;
                    int k = 2;
                    int l = radius - 2;
                    float f = (j + k + l) * 0.333F + 0.5F;
                    BlockPos up = finalPosition.above(k - 1);
                    BlockPos.betweenClosedStream(up.offset(-j, -k + 2, -l), up.offset(j, k, l)).map(BlockPos::immutable).forEach(blockPos -> {
                        if (blockPos.distSqr(finalPosition) <= f * f) {
                            pLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
                        }
                    });
                }
                this.hasPlacedBase = true;
            }
            {
                int j = (int) (radius * 1.25F);
                int k = ((radius * 2) / 3);
                int l = (int) (radius * 1.25F);
                float f = (j + k + l) * 0.333F + 0.5F;
                BlockPos.betweenClosedStream(finalPosition.offset(-j, -k, -l), finalPosition.offset(j, k, l)).map(BlockPos::immutable).forEach(blockPos -> {
                    if (blockPos.distSqr(finalPosition) <= f * f) {
                        double dist = blockPos.distSqr(finalPosition) / (f * f);
                        if (!pLevel.isEmptyBlock(finalPosition) && pRandom.nextDouble() > dist * 0.5D) {
                            transformState(pLevel, blockPos, pLevel.getBlockState(blockPos));
                        }
                        if (dist > 0.5D && pRandom.nextInt(1000) == 0) {
                            BlockPos height = pLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockPos);
                            new WorldGenRoostBoulder(IafBlockRegistry.CRACKLED_COBBLESTONE.get(), pRandom.nextInt(3), true).generate(pLevel, pRandom, height);
                        }
                        if (dist > 0.5D && pRandom.nextInt(1500) == 0) {
                            BlockPos height = pLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockPos);
                            new WorldGenRoostPile(IafBlockRegistry.ASH.get()).generate(pLevel, pRandom, height);
                        }
                        if (dist < 0.3D && numberOfPlacedTreasurePiles < 10 && pRandom.nextInt(isDragonMale ? 200 : 300) == 0) {
                            BlockPos height = WorldGenUtils.degradeSurface(pLevel, pLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockPos)).above();
                            new WorldGenRoostTreasurePile(IafBlockRegistry.COPPER_PILE.get()).generate(pLevel, pRandom, height);
                            this.numberOfPlacedTreasurePiles++;
                        }
                        if (dist < 0.3D && numberOfPlacedChests < 6 && pRandom.nextInt(isDragonMale ? 500 : 700) == 0) {
                            BlockPos height = WorldGenUtils.degradeSurface(pLevel, pLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockPos)).above();
                            if (pLevel.setBlock(height, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, HORIZONTALS[new Random().nextInt(3)]), 2)) {
                                numberOfPlacedChests++;

                                if (pLevel.getBlockState(height).getBlock() instanceof ChestBlock) {
                                    BlockEntity blockEntity = pLevel.getBlockEntity(height);
                                    if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                                        chestBlockEntity.setLootTable(DRAGON_CHEST, new Random().nextLong());
                                    }
                                }
                            }
                        }
                        if (dist > 0.3D && pRandom.nextInt(5000) == 0) {
                            BlockPos height = pLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockPos);
                            new WorldGenRoostArch(IafBlockRegistry.CRACKLED_COBBLESTONE.get()).generate(pLevel, pRandom, height);
                        }
                    }
                });
            }
        }
    }
}
