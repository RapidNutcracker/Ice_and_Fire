package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockBurntTorchWall extends WallTorchBlock implements IDreadBlock {

    public BlockBurntTorchWall() {
        super(
                Properties.of(Material.DECORATION)
                        .lightLevel((state) -> 1)
                        .sound(SoundType.WOOD)
                        .dropsLike(IafBlockRegistry.BURNT_TORCH.get())
                        .noCollission(),
                ParticleTypes.SMOKE
        );
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {     }
}