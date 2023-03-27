package com.github.alexthe666.iceandfire.world.feature;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class IafPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, IceAndFire.MODID);

    public static final RegistryObject<PlacedFeature> SAPPHIRE_ORE_PLACED = PLACED_FEATURES.register(
            "sapphire_ore_placed",
            () -> new PlacedFeature(
                    IafConfiguredFeatures.SAPPHIRE_ORE.getHolder().get(),
                    commonOrePlacement(
                            3,
                            8, // VeinsPerChunk
                            HeightRangePlacement.triangle(
                                    VerticalAnchor.absolute(-64),
                                    VerticalAnchor.absolute(64)
                            ))));

    public static final RegistryObject<PlacedFeature> CRYSTAL_GEODE_PLACED = PLACED_FEATURES.register(
            "crystal_geode_placed",
            () -> new PlacedFeature(
                    IafConfiguredFeatures.CRYSTAL_GEODE.getHolder().get(),
                    List.of(
                            RarityFilter.onAverageOnceEvery(50),
                            InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(50)),
                            BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> EMERALD_GEODE_PLACED = PLACED_FEATURES.register(
            "emerald_geode_placed",
            () -> new PlacedFeature(
                    IafConfiguredFeatures.EMERALD_GEODE.getHolder().get(),
                    List.of(
                            RarityFilter.onAverageOnceEvery(50),
                            InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(50)),
                            BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> RUBY_GEODE_PLACED = PLACED_FEATURES.register(
            "ruby_geode_placed",
            () -> new PlacedFeature(
                    IafConfiguredFeatures.RUBY_GEODE.getHolder().get(),
                    List.of(
                            RarityFilter.onAverageOnceEvery(50),
                            InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(50)),
                            BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> SAPPHIRE_GEODE_PLACED = PLACED_FEATURES.register(
            "sapphire_geode_placed",
            () -> new PlacedFeature(
                    IafConfiguredFeatures.SAPPHIRE_GEODE.getHolder().get(),
                    List.of(
                            RarityFilter.onAverageOnceEvery(50),
                            InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(50)),
                            BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> TOPAZ_GEODE_PLACED = PLACED_FEATURES.register(
            "topaz_geode_placed",
            () -> new PlacedFeature(
                    IafConfiguredFeatures.TOPAZ_GEODE.getHolder().get(),
                    List.of(
                            RarityFilter.onAverageOnceEvery(50),
                            InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(50)),
                            BiomeFilter.biome())));

//    public static final RegistryObject<PlacedFeature> FIRE_LILY_PLACED = PLACED_FEATURES.register(
//            "fire_lily_placed",
//            () -> new PlacedFeature(
//                    IafConfiguredFeatures.FIRE_LILY.getHolder().get(),
//                    List.of(
//                            RarityFilter.onAverageOnceEvery(16),
//                            InSquarePlacement.spread(),
//                            PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

//    public static final RegistryObject<PlacedFeature> FIRE_DRAGON_ROOST_PLACED = PLACED_FEATURES.register(
//            "fire_dragon_roost_placed",
//            () -> new PlacedFeature(
//                    new ConfiguredFeature<>(IafConfiguredFeatures.FIRE_DRAGON_ROOST.get(), FeatureConfiguration.NONE),
//                    List.of(
//                            RarityFilter.onAverageOnceEvery(50),
//                            InSquarePlacement.spread(),
//                            HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(75)),
//                            BiomeFilter.biome()
//                    )
//            )
//    );

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int pMinCount, int pMaxCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(UniformInt.of(pMinCount, pMaxCount)), pHeightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
