package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class BlockElementalFlower extends BushBlock {
    public Item itemBlock;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public BlockElementalFlower() {
        super(
                Properties
                        .of(Material.REPLACEABLE_PLANT)
                        .noOcclusion()
                        .noCollission()
                        .dynamicShape()
                        .randomTicks()
                        .sound(SoundType.GRASS)
        );
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
        Block block = state.getBlock();
        Material soil = worldIn.getBlockState(pos.below()).getMaterial();

        final List<Block> ALL_LILY_BLOCKS = Arrays.asList(
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.PODZOL,
                Blocks.FARMLAND
        );

        final List<Block> FIRE_LILY_BLOCKS = Arrays.asList(
                Blocks.NETHERRACK,
                IafBlockRegistry.CHARRED_DIRT.get(),
                IafBlockRegistry.CHARRED_GRASS.get(),
                IafBlockRegistry.CHARRED_STONE.get(),
                IafBlockRegistry.CHARRED_GRAVEL.get(),
                IafBlockRegistry.CHARRED_COBBLESTONE.get()
        );

        final List<Material> FIRE_LILY_SOILS = Arrays.asList(
                Material.SAND
        );

        final List<Block> FROST_LILY_BLOCKS = Arrays.asList(
                Blocks.SNOW,
                Blocks.SNOW_BLOCK,
                IafBlockRegistry.FROZEN_DIRT.get(),
                IafBlockRegistry.FROZEN_GRASS.get(),
                IafBlockRegistry.FROZEN_STONE.get(),
                IafBlockRegistry.FROZEN_GRAVEL.get(),
                IafBlockRegistry.FROZEN_COBBLESTONE.get()
        );

        final List<Material> FROST_LILY_SOILS = Arrays.asList(
                Material.ICE,
                Material.ICE_SOLID
        );

        final List<Block> LIGHTNING_LILY_BLOCKS = Arrays.asList(
                Blocks.TERRACOTTA,
                Blocks.WHITE_TERRACOTTA,
                Blocks.ORANGE_TERRACOTTA,
                Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA,
                Blocks.YELLOW_TERRACOTTA,
                Blocks.LIME_TERRACOTTA,
                Blocks.PINK_TERRACOTTA,
                Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA,
                Blocks.CYAN_TERRACOTTA,
                Blocks.PURPLE_TERRACOTTA,
                Blocks.BLUE_TERRACOTTA,
                Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA,
                Blocks.RED_TERRACOTTA,
                Blocks.BLACK_TERRACOTTA,
                IafBlockRegistry.CRACKLED_DIRT.get(),
                IafBlockRegistry.CRACKLED_GRASS.get(),
                IafBlockRegistry.CRACKLED_STONE.get(),
                IafBlockRegistry.CRACKLED_GRAVEL.get(),
                IafBlockRegistry.CRACKLED_COBBLESTONE.get()
        );

        final List<Material> LIGHTNING_LILY_SOILS = Arrays.asList(
                Material.SAND
        );

        if (ALL_LILY_BLOCKS.contains(block)) {
            return true;
        }

        if (this == IafBlockRegistry.FIRE_LILY.get()) {
            return FIRE_LILY_BLOCKS.contains(block) || FIRE_LILY_SOILS.contains(soil);
        } else if (this == IafBlockRegistry.FROST_LILY.get()) {
            return FROST_LILY_BLOCKS.contains(block) || FROST_LILY_SOILS.contains(soil);
        } else if (this == IafBlockRegistry.LIGHTNING_LILY.get()) {
            return LIGHTNING_LILY_BLOCKS.contains(block) || LIGHTNING_LILY_SOILS.contains(soil);
        }

        return false;
    }

    public boolean canStay(Level worldIn, BlockPos pos) {
        BlockState soil = worldIn.getBlockState(pos.below());
        if (this == IafBlockRegistry.FIRE_LILY.get()) {
            return soil.getMaterial() == Material.SAND || soil.getBlock() == Blocks.NETHERRACK;
        } else if (this == IafBlockRegistry.LIGHTNING_LILY.get()) {
            return soil.getMaterial() == Material.DIRT || soil.getBlock() == Blocks.GRASS;
        } else {
            return soil.getMaterial() == Material.ICE_SOLID || soil.getMaterial() == Material.ICE;
        }
    }

    public void updateTick(Level worldIn, BlockPos pos, BlockState state, RandomSource rand) {
        this.checkFall(worldIn, pos);
    }

    private boolean checkFall(Level worldIn, BlockPos pos) {
        if (!this.canStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
            return false;
        } else {
            return true;
        }
    }

    protected boolean canSustainBush(BlockState state) {
        return true;
    }

}
