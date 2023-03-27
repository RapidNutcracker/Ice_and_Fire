package com.github.alexthe666.iceandfire.world.feature;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class IafConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, IceAndFire.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, IceAndFire.MODID);


    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_SAPPHIRE_ORES = Suppliers.memoize(
            () -> List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, IafBlockRegistry.SAPPHIRE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, IafBlockRegistry.DEEPSLATE_SAPPHIRE_ORE.get().defaultBlockState())));


    public static final RegistryObject<ConfiguredFeature<?, ?>> SAPPHIRE_ORE = CONFIGURED_FEATURES.register("sapphire_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_SAPPHIRE_ORES.get(), 7)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CRYSTAL_GEODE = CONFIGURED_FEATURES.register(
            "crystal_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
                    new GeodeConfiguration(
                            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(IafBlockRegistry.RAW_CRYSTAL_BLOCK.get()),
                                    BlockStateProvider.simple(IafBlockRegistry.BUDDING_CRYSTAL.get()),
                                    BlockStateProvider.simple(Blocks.CALCITE),
                                    BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                                    List.of(IafBlockRegistry.SMALL_CRYSTAL_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.MEDIUM_CRYSTAL_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.LARGE_CRYSTAL_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.CRYSTAL_CLUSTER.get().defaultBlockState()),
                                    BlockTags.FEATURES_CANNOT_REPLACE,
                                    BlockTags.GEODE_INVALID_BLOCKS),
                            new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                            new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
                            true,
                            UniformInt.of(4, 6),
                            UniformInt.of(3, 4),
                            UniformInt.of(1, 2),
                            -16, 16, 0.05D, 1)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> EMERALD_GEODE = CONFIGURED_FEATURES.register(
            "emerald_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
                    new GeodeConfiguration(
                            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(IafBlockRegistry.RAW_EMERALD_BLOCK.get()),
                                    BlockStateProvider.simple(IafBlockRegistry.BUDDING_EMERALD.get()),
                                    BlockStateProvider.simple(Blocks.CALCITE),
                                    BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                                    List.of(IafBlockRegistry.SMALL_EMERALD_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.MEDIUM_EMERALD_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.LARGE_EMERALD_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.EMERALD_CLUSTER.get().defaultBlockState()),
                                    BlockTags.FEATURES_CANNOT_REPLACE,
                                    BlockTags.GEODE_INVALID_BLOCKS),
                            new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                            new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
                            true,
                            UniformInt.of(4, 6),
                            UniformInt.of(3, 4),
                            UniformInt.of(1, 2),
                            -16, 16, 0.05D, 1)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> RUBY_GEODE = CONFIGURED_FEATURES.register(
            "ruby_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
                    new GeodeConfiguration(
                            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(IafBlockRegistry.RAW_RUBY_BLOCK.get()),
                                    BlockStateProvider.simple(IafBlockRegistry.BUDDING_RUBY.get()),
                                    BlockStateProvider.simple(Blocks.CALCITE),
                                    BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                                    List.of(IafBlockRegistry.SMALL_RUBY_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.MEDIUM_RUBY_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.LARGE_RUBY_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.RUBY_CLUSTER.get().defaultBlockState()),
                                    BlockTags.FEATURES_CANNOT_REPLACE,
                                    BlockTags.GEODE_INVALID_BLOCKS),
                            new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                            new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
                            true,
                            UniformInt.of(4, 6),
                            UniformInt.of(3, 4),
                            UniformInt.of(1, 2),
                            -16, 16, 0.05D, 1)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SAPPHIRE_GEODE = CONFIGURED_FEATURES.register(
            "sapphire_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
                    new GeodeConfiguration(
                            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(IafBlockRegistry.RAW_SAPPHIRE_BLOCK.get()),
                                    BlockStateProvider.simple(IafBlockRegistry.BUDDING_SAPPHIRE.get()),
                                    BlockStateProvider.simple(Blocks.CALCITE),
                                    BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                                    List.of(IafBlockRegistry.SMALL_SAPPHIRE_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.MEDIUM_SAPPHIRE_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.LARGE_SAPPHIRE_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.SAPPHIRE_CLUSTER.get().defaultBlockState()),
                                    BlockTags.FEATURES_CANNOT_REPLACE,
                                    BlockTags.GEODE_INVALID_BLOCKS),
                            new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                            new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
                            true,
                            UniformInt.of(4, 6),
                            UniformInt.of(3, 4),
                            UniformInt.of(1, 2),
                            -16, 16, 0.05D, 1)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> TOPAZ_GEODE = CONFIGURED_FEATURES.register(
            "topaz_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
                    new GeodeConfiguration(
                            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(IafBlockRegistry.RAW_TOPAZ_BLOCK.get()),
                                    BlockStateProvider.simple(IafBlockRegistry.BUDDING_TOPAZ.get()),
                                    BlockStateProvider.simple(Blocks.CALCITE),
                                    BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                                    List.of(IafBlockRegistry.SMALL_TOPAZ_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.MEDIUM_TOPAZ_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.LARGE_TOPAZ_BUD.get().defaultBlockState(),
                                            IafBlockRegistry.TOPAZ_CLUSTER.get().defaultBlockState()),
                                    BlockTags.FEATURES_CANNOT_REPLACE,
                                    BlockTags.GEODE_INVALID_BLOCKS),
                            new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                            new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
                            true,
                            UniformInt.of(4, 6),
                            UniformInt.of(3, 4),
                            UniformInt.of(1, 2),
                            -16, 16, 0.05D, 1)));

//    public static final RegistryObject<ConfiguredFeature<?, ?>> FIRE_LILY = CONFIGURED_FEATURES.register(
//            "fire_lily",
//            () -> new ConfiguredFeature<>(Feature.FLOWER,
//                    new RandomPatchConfiguration(
//                            32, 6, 2,
//                            PlacementUtils.onlyWhenEmpty(
//                                    Feature.SIMPLE_BLOCK,
//                                    new SimpleBlockConfiguration(
//                                            BlockStateProvider.simple(IafBlockRegistry.FIRE_LILY.get()))))));
//
//    public static final RegistryObject<ConfiguredFeature<?, ?>> ICE_LILY = CONFIGURED_FEATURES.register(
//            "ice_lily",
//            () -> new ConfiguredFeature<>(Feature.FLOWER,
//                    new RandomPatchConfiguration(
//                            32, 6, 2,
//                            PlacementUtils.onlyWhenEmpty(
//                                    Feature.SIMPLE_BLOCK,
//                                    new SimpleBlockConfiguration(
//                                            BlockStateProvider.simple(IafBlockRegistry.ICE_LILY.get()))))));
//
//    public static final RegistryObject<ConfiguredFeature<?, ?>> FIRE_LILY = CONFIGURED_FEATURES.register(
//            "fire_lily",
//            () -> new ConfiguredFeature<>(Feature.FLOWER,
//                    new RandomPatchConfiguration(
//                            32, 6, 2,
//                            PlacementUtils.onlyWhenEmpty(
//                                    Feature.SIMPLE_BLOCK,
//                                    new SimpleBlockConfiguration(
//                                            BlockStateProvider.simple(IafBlockRegistry.FIRE_LILY.get()))))));

//    public static final RegistryObject<Feature<?>> FIRE_DRAGON_ROOST =  FEATURES.register(
//            "fire_dragon_roost",
//            () -> new WorldGenTestFeature(NoneFeatureConfiguration.CODEC)
//    );

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
        FEATURES.register(eventBus);
    }
}
