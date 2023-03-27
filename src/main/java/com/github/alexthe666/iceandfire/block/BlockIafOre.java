package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockIafOre extends DropExperienceBlock {
    public Item itemBlock;

    public BlockIafOre(float hardness, float resistance) {
        super(
                Properties
                        .of(Material.STONE)
                        .strength(hardness, resistance)
                        .requiresCorrectToolForDrops(),
                UniformInt.of(3, 7)
        );
    }

    public BlockIafOre(float hardness, float resistance, IntProvider xpRange) {
        super(
                Properties
                        .of(Material.STONE)
                        .strength(hardness, resistance)
                        .requiresCorrectToolForDrops(),
                xpRange
        );
    }
}
