package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.BlockTreasurePile;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.util.HomePosition;
import com.github.alexthe666.iceandfire.event.WorldGenUtils;
import com.github.alexthe666.iceandfire.util.ShapeBuilder;
import com.github.alexthe666.iceandfire.world.IafStructurePieceTypes;
import com.github.alexthe666.iceandfire.world.gen.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DragonCavePiece extends ScatteredFeaturePiece {

    public static final int RADIUS = 12;
    private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public static ResourceLocation DRAGON_CHEST = new ResourceLocation("iceandfire", "chest/fire_dragon_female_cave");
    public static ResourceLocation DRAGON_MALE_CHEST = new ResourceLocation("iceandfire", "chest/fire_dragon_male_cave");
    public WorldGenCaveStalactites CEILING_DECO;
    public BlockState PALETTE_BLOCK1;
    public BlockState PALETTE_BLOCK2;
    public BlockState PALETTE_ORE1;
    public BlockState PALETTE_ORE2;
    public BlockState TREASURE_PILE;

    private static boolean isDragonMale;
    public boolean generateGemOre = false;

    private int radius = 0;
    private boolean hasPlacedDragon;
    private int numberOfPlacedChests;
    private int numberOfPlacedTreasurePiles;


    protected DragonCavePiece(RandomSource pRandom, int pX, int pZ) {
        super(IafStructurePieceTypes.FIRE_DRAGON_ROOST_PIECE.get(), pX, 64, pZ, 128, 128, 128, getRandomHorizontalDirection(pRandom));
    }

    protected DragonCavePiece(StructurePieceSerializationContext context, CompoundTag pTag) {
        super(IafStructurePieceTypes.FIRE_DRAGON_ROOST_PIECE.get(), pTag);
        this.radius = pTag.getInt("radius");
        this.hasPlacedDragon = pTag.getBoolean("hasPlacedDragon");
//        this.numberOfPlacedTreasurePiles = pTag.getInt("numberOfPlacedTreasurePiles");
//        this.numberOfPlacedChests = pTag.getInt("numberOfPlacedChests");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
        super.addAdditionalSaveData(pContext, pTag);
        pTag.putInt("radius", this.radius);
        pTag.putBoolean("hasPlacedDragon", this.hasPlacedDragon);
//        pTag.putInt("numberOfPlacedTreasurePiles", this.numberOfPlacedTreasurePiles);
//        pTag.putInt("numberOfPlacedChests", this.numberOfPlacedChests);
    }

    @Override
    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
        if (!hasPlacedDragon && this.updateAverageGroundHeight(pLevel, pBox, 0)) {
            // Center the position at the "middle" of the chunk
            BlockPos finalPosition = new BlockPos((pChunkPos.x << 4) + 8, 20 + pRandom.nextInt(20), (pChunkPos.z << 4) + 8);
            int dragonAge = 75 + pRandom.nextInt(50);
            radius = (int) (dragonAge * 0.2F) + pRandom.nextInt(4);
            generateCave(pLevel, radius, 3, finalPosition, pRandom);
//            EntityDragonBase dragon = createDragon(worldIn, rand, position, dragonAge);
//            worldIn.addFreshEntity(dragon);

            isDragonMale = pRandom.nextBoolean();

            if (!hasPlacedDragon) {
                EntityDragonBase dragon = IafEntityRegistry.FIRE_DRAGON.get().create(pLevel.getLevel());
                dragon.setGender(isDragonMale);
                dragon.growDragon(dragonAge);
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
        }
    }

    public void generateCave(LevelAccessor worldIn, int radius, int amount, BlockPos center, RandomSource rand) {
        List<SphereInfo> sphereList = new ArrayList<>();
        sphereList.add(new SphereInfo(radius, center.immutable()));
        Stream<BlockPos> sphereBlocks = ShapeBuilder.start().getAllInCutOffSphereMutable(radius, radius / 2, center).toStream(false);
        Stream<BlockPos> hollowBlocks = ShapeBuilder.start().getAllInRandomlyDistributedRangeYCutOffSphereMutable(radius - 2, (int) ((radius - 2) * 0.75), (radius - 2) / 2, rand, center).toStream(false);
        //Get shells
        //Get hollows
        for (int i = 0; i < amount + rand.nextInt(2); i++) {
            Direction direction = HORIZONTALS[rand.nextInt(HORIZONTALS.length - 1)];
            int r = 2 * (int) (radius / 3F) + rand.nextInt(8);
            BlockPos centerOffset = center.relative(direction, radius - 2);
            sphereBlocks = Stream.concat(sphereBlocks, ShapeBuilder.start().getAllInCutOffSphereMutable(r, r, centerOffset).toStream(false));
            hollowBlocks = Stream.concat(hollowBlocks, ShapeBuilder.start().getAllInRandomlyDistributedRangeYCutOffSphereMutable(r - 2, (int) ((r - 2) * 0.75), (r - 2) / 2, rand, centerOffset).toStream(false));
            sphereList.add(new SphereInfo(r, centerOffset));
        }
        Set<BlockPos> shellBlocksSet = sphereBlocks.map(BlockPos::immutable).collect(Collectors.toSet());
        Set<BlockPos> hollowBlocksSet = hollowBlocks.map(BlockPos::immutable).collect(Collectors.toSet());
        shellBlocksSet.removeAll(hollowBlocksSet);

        //setBlocks
        createShell(worldIn, rand, shellBlocksSet);
        //removeBlocks
        hollowOut(worldIn, hollowBlocksSet);
        //decorate
        decorateCave(worldIn, rand, hollowBlocksSet, sphereList, center);
        sphereList.clear();
    }

    public void createShell(LevelAccessor worldIn, RandomSource rand, Set<BlockPos> positions) {
        positions.forEach(blockPos -> {
            if (!(worldIn.getBlockState(blockPos).getBlock() instanceof BaseEntityBlock) && worldIn.getBlockState(blockPos).getDestroySpeed(worldIn, blockPos) >= 0) {
                boolean doOres = rand.nextInt(IafConfig.oreToStoneRatioForDragonCaves + 1) == 0;
                if (doOres) {
                    int chance = rand.nextInt(199) + 1;
                    if (chance < 30) {
                        worldIn.setBlock(blockPos, Blocks.IRON_ORE.defaultBlockState(), 2);
                    } else if (chance > 30 && chance < 40) {
                        worldIn.setBlock(blockPos, Blocks.GOLD_ORE.defaultBlockState(), 2);
                    } else if (chance > 40 && chance < 45) {
                        worldIn.setBlock(blockPos, IafConfig.generateCopperOre ? IafBlockRegistry.COPPER_ORE.get().defaultBlockState() : PALETTE_BLOCK1, 2);
                    } else if (chance > 45 && chance < 50) {
                        worldIn.setBlock(blockPos, IafConfig.generateSilverOre ? IafBlockRegistry.SILVER_ORE.get().defaultBlockState() : PALETTE_BLOCK1, 2);
                    } else if (chance > 50 && chance < 60) {
                        worldIn.setBlock(blockPos, Blocks.COAL_ORE.defaultBlockState(), 2);
                    } else if (chance > 60 && chance < 70) {
                        worldIn.setBlock(blockPos, Blocks.REDSTONE_ORE.defaultBlockState(), 2);
                    } else if (chance > 70 && chance < 80) {
                        worldIn.setBlock(blockPos, Blocks.LAPIS_ORE.defaultBlockState(), 2);
                    } else if (chance > 80 && chance < 90) {
                        worldIn.setBlock(blockPos, Blocks.DIAMOND_ORE.defaultBlockState(), 2);
                    } else if (chance > 90) {
                        worldIn.setBlock(blockPos, generateGemOre ? PALETTE_ORE1 : PALETTE_ORE2, 2);
                    }
                } else {
                    worldIn.setBlock(blockPos, rand.nextBoolean() ? PALETTE_BLOCK1 : PALETTE_BLOCK2, 2);
                }
            }
        });
    }

    public void hollowOut(LevelAccessor worldIn, Set<BlockPos> positions) {
        positions.forEach(blockPos -> {
            if (!(worldIn.getBlockState(blockPos).getBlock() instanceof BaseEntityBlock)) {
                worldIn.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
            }
        });
    }

    public void decorateCave(LevelAccessor worldIn, RandomSource rand, Set<BlockPos> positions, List<SphereInfo> spheres, BlockPos center) {
        for (SphereInfo sphere : spheres) {
            BlockPos pos = sphere.pos;
            int radius = sphere.radius;
            for (int i = 0; i < 15 + rand.nextInt(10); i++) {
                CEILING_DECO.generate(worldIn, rand, pos.above(radius / 2 - 1).offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2));
            }

        }
        int y = center.getY();
        positions.forEach(blockPos -> {
            if (blockPos.getY() < y) {
                if (worldIn.getBlockState(blockPos.below()).getMaterial() == Material.STONE && worldIn.getBlockState(blockPos).getMaterial() == Material.AIR)
                    setGoldPile(worldIn, blockPos, rand);
            }
        });
    }

    public void setGoldPile(LevelAccessor world, BlockPos pos, RandomSource rand) {
        if (!(world.getBlockState(pos).getBlock() instanceof BaseEntityBlock)) {
            int chance = rand.nextInt(99) + 1;
            if (chance < 60) {
                int goldRand = Math.max(1, IafConfig.dragonDenGoldAmount) * (isDragonMale ? 1 : 2);
                boolean generateGold = rand.nextInt(goldRand) == 0;
                world.setBlock(pos, generateGold ? TREASURE_PILE.setValue(BlockTreasurePile.LAYERS, 1 + rand.nextInt(7)) : Blocks.AIR.defaultBlockState(), 3);
            } else if (chance == 61) {
                world.setBlock(pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, HORIZONTALS[rand.nextInt(3)]), 2);
                if (world.getBlockState(pos).getBlock() instanceof ChestBlock) {
                    BlockEntity tileentity1 = world.getBlockEntity(pos);
                    if (tileentity1 instanceof ChestBlockEntity) {
                        ((ChestBlockEntity) tileentity1).setLootTable(isDragonMale ? DRAGON_MALE_CHEST : DRAGON_CHEST, rand.nextLong());
                    }
                }
            }
        }
    }

    abstract EntityDragonBase createDragon(WorldGenLevel worldIn, RandomSource rand, BlockPos position, int dragonAge);

    private static class SphereInfo {
        int radius;
        BlockPos pos;

        private SphereInfo(int radius, BlockPos pos) {
            this.radius = radius;
            this.pos = pos;
        }
    }
}
