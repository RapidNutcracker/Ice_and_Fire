package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BlockBuddingRuby extends BuddingAmethystBlock {

    private static final Direction[] DIRECTIONS = Direction.values();

    public BlockBuddingRuby(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(5) == 0) {
            Direction $$4 = DIRECTIONS[pRandom.nextInt(DIRECTIONS.length)];
            BlockPos $$5 = pPos.relative($$4);
            BlockState $$6 = pLevel.getBlockState($$5);
            Block $$7 = null;
            if (canClusterGrowAtState($$6)) {
                $$7 = IafBlockRegistry.SMALL_RUBY_BUD.get();
            } else if ($$6.is(IafBlockRegistry.SMALL_RUBY_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = IafBlockRegistry.MEDIUM_RUBY_BUD.get();
            } else if ($$6.is(IafBlockRegistry.MEDIUM_RUBY_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = IafBlockRegistry.LARGE_RUBY_BUD.get();
            } else if ($$6.is(IafBlockRegistry.LARGE_RUBY_BUD.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = IafBlockRegistry.RUBY_CLUSTER.get();
            }

            if ($$7 != null) {
                BlockState $$8 = (BlockState)((BlockState)$$7.defaultBlockState().setValue(AmethystClusterBlock.FACING, $$4)).setValue(AmethystClusterBlock.WATERLOGGED, $$6.getFluidState().getType() == Fluids.WATER);
                pLevel.setBlockAndUpdate($$5, $$8);
            }
        }
    }
}
