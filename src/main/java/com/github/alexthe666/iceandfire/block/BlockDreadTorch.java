package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
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

public class BlockDreadTorch extends TorchBlock implements IDreadBlock, IWallBlock {

    public BlockDreadTorch() {
        super(
                Properties
                        .of(Material.DECORATION)
                        .lightLevel((state) -> 5)
                        .sound(SoundType.STONE)
                        .noCollission()
                        .instabreak(),
                DustParticleOptions.REDSTONE
        );
    }

    @Override
    public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, BlockPos pos, @NotNull RandomSource rand) {        // Direction Direction = stateIn.get(FACING);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.6D;
        double d2 = (double) pos.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.27D;
        worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Torch, d0, d1, d2, 0.0D, 0.0D, 0.0D);

//        if (stateIn.getValue(FACING).getAxis().isHorizontal()) {
//            Direction direction = stateIn.getValue(FACING).getOpposite();
//            //worldIn.spawnParticle(ParticleTypes.SMOKE_NORMAL, d0 + d4 * (double)Direction1.getXOffset(), d1 + d3, d2 + d4 * (double)Direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
//            IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Torch, d0 + d4 * (double) direction.getStepX(), d1 + d3, d2 + d4 * (double) direction.getStepZ(), 0.0D, 0.0D, 0.0D);
//
//        } else {
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
//        }
    }

    @Override
    public Block wallBlock() {
        return IafBlockRegistry.DREAD_TORCH_WALL.get();
    }
}