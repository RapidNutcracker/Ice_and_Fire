package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockBurntTorch extends TorchBlock implements IDreadBlock, IWallBlock {

    public BlockBurntTorch() {
        super(
                Properties.of(Material.DECORATION)
                        .lightLevel((state) -> 1)
                        .sound(SoundType.WOOD)
                        .noCollission()
                        .instabreak(),
                ParticleTypes.SMOKE
        );
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) { }

    @Override
    public Block wallBlock() {
        return IafBlockRegistry.BURNT_TORCH_WALL.get();
    }
}