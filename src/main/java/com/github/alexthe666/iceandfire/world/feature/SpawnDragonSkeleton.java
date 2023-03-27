package com.github.alexthe666.iceandfire.world.feature;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumDragonType;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


public class SpawnDragonSkeleton extends Feature<NoneFeatureConfiguration> {

    protected EnumDragonType dragonType;

    public SpawnDragonSkeleton(EnumDragonType dragonType, Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
        this.dragonType = dragonType;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldIn = context.level();
        RandomSource rand = context.random();
        BlockPos position = context.origin();
        if (!IafWorldRegistry.isDimensionListedForMobs(worldIn)) {
            return false;
        }
        position = worldIn.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, position.offset(8, 0, 8));

        if (IafConfig.generateDragonSkeletons) {
            if (rand.nextInt(IafConfig.generateDragonSkeletonChance + 1) == 0) {
                EntityDragonBase dragon = switch(dragonType) {
                    case FIRE -> IafEntityRegistry.FIRE_DRAGON.get().create(worldIn.getLevel());
                    case ICE -> IafEntityRegistry.ICE_DRAGON.get().create(worldIn.getLevel());
                    case LIGHTNING -> IafEntityRegistry.LIGHTNING_DRAGON.get().create(worldIn.getLevel());
                    default -> null;
                };

                if (dragon == null) {
                    return false;
                }

                dragon.setPos(position.getX() + 0.5F, position.getY() + 1, position.getZ() + 0.5F);
                int dragonAge = 10 + rand.nextInt(100);
                dragon.growDragon(dragonAge);
                dragon.modelDeadProgress = 20;
                dragon.setModelDead(true);
                dragon.setDeathStage((dragonAge / 5) / 2);
                dragon.setYRot(rand.nextInt(360));
                worldIn.addFreshEntity(dragon);
            }
        }

        return false;
    }
}
