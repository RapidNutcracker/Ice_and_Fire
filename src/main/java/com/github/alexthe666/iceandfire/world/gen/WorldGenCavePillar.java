package com.github.alexthe666.iceandfire.world.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;


public class WorldGenCavePillar {
    private final boolean ice;

    public WorldGenCavePillar(boolean ice) {
        this.ice = ice;
    }

    public boolean generate(Level worldIn, RandomSource rand, BlockPos position) {
        int height = 3 + rand.nextInt(3);

        return true;
    }
}
