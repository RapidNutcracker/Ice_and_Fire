package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.config.biome.IafSpawnBiomeData;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumDragonType;
import com.github.alexthe666.iceandfire.world.biomemodifiers.AddFeaturesWithExceptionsModifier;
import com.github.alexthe666.iceandfire.world.feature.*;
import com.github.alexthe666.iceandfire.world.gen.*;
import com.github.alexthe666.iceandfire.world.structure.DummyPiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.StructureTemplateType;
import net.minecraft.world.level.storage.LevelData;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.alexthe666.iceandfire.block.IafBlockRegistry.*;
import static net.minecraft.world.level.block.Blocks.STONE;
import static net.minecraft.world.level.levelgen.VerticalAnchor.absolute;
import static net.minecraft.world.level.levelgen.placement.InSquarePlacement.spread;

// TODO Clean this up
public class IafWorldRegistry {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES,
            IceAndFire.MODID);
//    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, IceAndFire.MODID);

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, IceAndFire.MODID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> ADD_FEATURES_WITH_EXCEPTIONS_MODIFIER = BIOME_MODIFIER_SERIALIZERS.register("add_features_with_exceptions_modifier", () -> AddFeaturesWithExceptionsModifier.CODEC);



//    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FIRE_DRAGON_ROOST;
//    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ICE_DRAGON_ROOST;
//    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_ROOST;
//    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FIRE_DRAGON_CAVE;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ICE_DRAGON_CAVE;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_CAVE;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CYCLOPS_CAVE;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PIXIE_VILLAGE;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SIREN_ISLAND;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HYDRA_CAVE;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_DESERT;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_JUNGLE;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DEATH_WORM;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_L;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_F;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_I;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_HIPPOCAMPUS;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_SEA_SERPENT;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_STYMPHALIAN_BIRD;
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_WANDERING_CYCLOPS;

    //public static final RegistryObject<StructureFeature<JigsawConfiguration>> MAUSOLEUM =
    //        STRUCTURES.register("mausoleum", DreadMausoleumStructure::new);
    //public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> GORGON_TEMPLE = STRUCTURES.register("gorgon_temple",
    //    () -> new GorgonTempleStructure(NoneFeatureConfiguration.CODEC));
    //public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> GRAVEYARD = STRUCTURES.register("graveyard",
    //    () -> new GraveyardStructure(NoneFeatureConfiguration.CODEC));

    //public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(
    //        ForgeRegistries.STRUCTURE_FEATURES,
    //        IceAndFire.MODID
    //);

//    public static final RegistryObject<StructureType> GORGON_TEMPLE = STRUCTURE_TYPES.register("gorgon_temple", () -> explicitStructureTypeTyping(GorgonTempleStructure.CODEC));
//    public static final RegistryObject<StructureType<DreadMausoleumStructure>> DREAD_MAUSOLEUM = STRUCTURE_TYPES.register("dread_mausoleum_structure", () -> explicitStructureTypeTyping(DreadMausoleumStructure.CODEC));
//    public static final RegistryObject<StructureType<GraveyardStructure>> GRAVEYARD = STRUCTURE_TYPES.register("graveyard", () -> explicitStructureTypeTyping(GraveyardStructure.CODEC));
//
//    public static final TagKey<Biome> HAS_GORGON_TEMPLE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IceAndFire.MODID, "has_structure/gorgon_temple"));
//    public static final TagKey<Biome> HAS_MAUSOLEUM = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IceAndFire.MODID, "has_structure/dread_mausoleum"));
//    public static final TagKey<Biome> HAS_GRAVEYARD = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IceAndFire.MODID, "has_structure/graveyard"));

    public static final TagKey<Biome> HAS_LIGHTNING_DRAGON_ROOST = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IceAndFire.MODID, "has_feature/lightning_dragon_roost"));
    public static final ResourceLocation RL_IAF_STRUCTURE_SET = new ResourceLocation(IceAndFire.MODID, "iaf_structure_set");
    public static final TagKey<StructureSet> IAF_STRUCTURE_SET = TagKey.create(Registry.STRUCTURE_SET_REGISTRY, RL_IAF_STRUCTURE_SET);

    public static StructurePieceType DUMMY_PIECE;
    public static Holder<PlacedFeature> FIRE_LILY_CF;
    public static Holder<PlacedFeature> FROST_LILY_CF;
    public static Holder<PlacedFeature> LIGHTNING_LILY_CF;
    public static Holder<PlacedFeature> COPPER_ORE_CF;
    public static Holder<PlacedFeature> SILVER_ORE_CF;
    //    public static Holder<PlacedFeature> SAPPHIRE_ORE_CF;
    public static Holder<PlacedFeature> AMETHYST_ORE_CF;
//    public static Holder<PlacedFeature> FIRE_DRAGON_ROOST_CF;
//    public static Holder<PlacedFeature> ICE_DRAGON_ROOST_CF;
//    public static Holder<PlacedFeature> LIGHTNING_DRAGON_ROOST_CF;
    public static Holder<PlacedFeature> FIRE_DRAGON_CAVE_CF;
    public static Holder<PlacedFeature> ICE_DRAGON_CAVE_CF;
    public static Holder<PlacedFeature> LIGHTNING_DRAGON_CAVE_CF;
    public static Holder<PlacedFeature> CYCLOPS_CAVE_CF;
    public static Holder<PlacedFeature> PIXIE_VILLAGE_CF;
    public static Holder<PlacedFeature> SIREN_ISLAND_CF;
    public static Holder<PlacedFeature> HYDRA_CAVE_CF;
    public static Holder<PlacedFeature> MYRMEX_HIVE_DESERT_CF;
    public static Holder<PlacedFeature> MYRMEX_HIVE_JUNGLE_CF;
    public static Holder<PlacedFeature> SPAWN_DEATH_WORM_CF;
    public static Holder<PlacedFeature> SPAWN_DRAGON_SKELETON_L_CF;
    public static Holder<PlacedFeature> SPAWN_DRAGON_SKELETON_F_CF;
    public static Holder<PlacedFeature> SPAWN_DRAGON_SKELETON_I_CF;
    public static Holder<PlacedFeature> SPAWN_HIPPOCAMPUS_CF;
    public static Holder<PlacedFeature> SPAWN_SEA_SERPENT_CF;
    public static Holder<PlacedFeature> SPAWN_STYMPHALIAN_BIRD_CF;
    public static Holder<PlacedFeature> SPAWN_WANDERING_CYCLOPS_CF;
//    public static Holder<ConfiguredFeature<?, ?>> GORGON_TEMPLE_CF;
//    public static Holder<ConfiguredFeature<?, ?>> MAUSOLEUM_CF;
//    public static Holder<ConfiguredFeature<?, ?>> GRAVEYARD_CF;

    static {
//        FIRE_DRAGON_ROOST = register("fire_dragon_roost", () -> new WorldGenFireDragonRoosts(NoneFeatureConfiguration.CODEC));
//        ICE_DRAGON_ROOST = register("ice_dragon_roost", () -> new WorldGenIceDragonRoosts(NoneFeatureConfiguration.CODEC));
//        LIGHTNING_DRAGON_ROOST = register("lightning_dragon_roost",
//                () -> new WorldGenLightningDragonRoosts(NoneFeatureConfiguration.CODEC));
//        FIRE_DRAGON_CAVE = register("fire_dragon_cave", () -> new WorldGenFireDragonCave(NoneFeatureConfiguration.CODEC));
        ICE_DRAGON_CAVE = register("ice_dragon_cave", () -> new WorldGenIceDragonCave(NoneFeatureConfiguration.CODEC));
        LIGHTNING_DRAGON_CAVE = register("lightning_dragon_cave",
                () -> new WorldGenLightningDragonCave(NoneFeatureConfiguration.CODEC));
        CYCLOPS_CAVE = register("cyclops_cave", () -> new WorldGenCyclopsCave(NoneFeatureConfiguration.CODEC));
        PIXIE_VILLAGE = register("pixie_village", () -> new WorldGenPixieVillage(NoneFeatureConfiguration.CODEC));
        SIREN_ISLAND = register("siren_island", () -> new WorldGenSirenIsland(NoneFeatureConfiguration.CODEC));
        HYDRA_CAVE = register("hydra_cave", () -> new WorldGenHydraCave(NoneFeatureConfiguration.CODEC));
        MYRMEX_HIVE_DESERT = register("myrmex_hive_desert",
                () -> new WorldGenMyrmexHive(false, false, NoneFeatureConfiguration.CODEC));
        MYRMEX_HIVE_JUNGLE = register("myrmex_hive_jungle",
                () -> new WorldGenMyrmexHive(false, true, NoneFeatureConfiguration.CODEC));

        SPAWN_DEATH_WORM = register("spawn_death_worm", () -> new SpawnDeathWorm(NoneFeatureConfiguration.CODEC));
        SPAWN_DRAGON_SKELETON_F = register("spawn_dragon_skeleton_f",
        () -> new SpawnDragonSkeleton(EnumDragonType.FIRE, NoneFeatureConfiguration.CODEC));
        SPAWN_DRAGON_SKELETON_I = register("spawn_dragon_skeleton_i",
        () -> new SpawnDragonSkeleton(EnumDragonType.ICE, NoneFeatureConfiguration.CODEC));
        SPAWN_DRAGON_SKELETON_L = register("spawn_dragon_skeleton_l",
                () -> new SpawnDragonSkeleton(EnumDragonType.LIGHTNING, NoneFeatureConfiguration.CODEC));
        SPAWN_HIPPOCAMPUS = register("spawn_hippocampus", () -> new SpawnHippocampus(NoneFeatureConfiguration.CODEC));
        SPAWN_SEA_SERPENT = register("spawn_seaserpent", () -> new SpawnSeaSerpent(NoneFeatureConfiguration.CODEC));
        SPAWN_STYMPHALIAN_BIRD = register("spawn_stymphalian_bird",
                () -> new SpawnStymphalianBird(NoneFeatureConfiguration.CODEC));
        SPAWN_WANDERING_CYCLOPS = register("spawn_wandering_cyclops",
                () -> new SpawnWanderingCyclops(NoneFeatureConfiguration.CODEC));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name,
                                                                                                     Supplier<? extends F> supplier) {
        return FEATURES.register(name, supplier);
    }

    private static HeightRangePlacement maxHeight(int max) {
        return HeightRangePlacement.uniform(VerticalAnchor.bottom(), absolute(max));
    }

    private static HeightRangePlacement minMaxHeight(int min, int max) {
        return HeightRangePlacement.uniform(absolute(min), absolute(max));
    }

    private static CountPlacement count(int count) {
        return CountPlacement.of(count);
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> register(String registerName, ConfiguredFeature<C, F> feature, PlacementModifier... modifiers) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(registerName), feature);
        return PlacementUtils.register(registerName, Holder.direct(feature), modifiers);
    }

    private static final BiFunction<String, Feature, Holder<PlacedFeature>> registerSimple = (name, feat) -> {
        return register("%s:%s".formatted(IceAndFire.MODID, name), new ConfiguredFeature<>(feat, FeatureConfiguration.NONE), BiomeFilter.biome());
    };

    public static void registerConfiguredFeatures() {
        // Technically we don't need the piece classes anymore but we should register
        // dummy pieces
        // under the same registry name or else player's will get logspammed by Minecraft in existing worlds.
//        DUMMY_PIECE = Registry.register(Registry.STRUCTURE_PIECE, "iceandfire:gorgon_piece", (StructureTemplateType) DummyPiece::new);
//        Registry.register(Registry.STRUCTURE_PIECE, "iceandfire:mausoleum_piece", (StructureTemplateType) DummyPiece::new);
//        Registry.register(Registry.STRUCTURE_PIECE, "iceandfire:gorgon_piece_empty", (StructureTemplateType) DummyPiece::new);
//        Registry.register(Registry.STRUCTURE_PIECE, "iceandfire:graveyard_piece", (StructureTemplateType) DummyPiece::new);

        COPPER_ORE_CF = register("iceandfire:copper_ore",
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, COPPER_ORE.get().defaultBlockState(), 8)),
                CountPlacement.of(2), maxHeight(128), spread());

        SILVER_ORE_CF = register("iceandfire:silver_ore",
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, SILVER_ORE.get().defaultBlockState(), 8)),
                CountPlacement.of(2), maxHeight(32), spread()
        );

        AMETHYST_ORE_CF = register("%s:amethyst_ore".formatted(IceAndFire.MODID),
                new ConfiguredFeature<>(Feature.REPLACE_SINGLE_BLOCK, new ReplaceBlockConfiguration(STONE.defaultBlockState(), AMETHYST_ORE.get().defaultBlockState())),
                CountPlacement.of(UniformInt.of(3, 8))
        );

        Function<Block, RandomPatchConfiguration> flowerConf = (block) -> new RandomPatchConfiguration(
                1, 1, 1,
                            PlacementUtils.onlyWhenEmpty(
                                    Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(block))));
        // (block) -> FeatureUtils.simpleRandomPatchConfiguration(1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.defaultBlockState().getBlock()))));

        FIRE_LILY_CF = register("%s:fire_lily".formatted(IceAndFire.MODID),
                new ConfiguredFeature<>(Feature.FLOWER, flowerConf.apply(FIRE_LILY.get())),
                PlacementUtils.HEIGHTMAP);

        FROST_LILY_CF = register("%s:frost_lily".formatted(IceAndFire.MODID),
                new ConfiguredFeature<>(Feature.FLOWER, flowerConf.apply(FROST_LILY.get())),
                PlacementUtils.HEIGHTMAP);

        LIGHTNING_LILY_CF = register("%s:lightning_lily".formatted(IceAndFire.MODID),
                new ConfiguredFeature<>(Feature.FLOWER, flowerConf.apply(LIGHTNING_LILY.get())),
                PlacementUtils.HEIGHTMAP);

//        FIRE_DRAGON_ROOST_CF = registerSimple.apply("fire_dragon_roost", FIRE_DRAGON_ROOST.get());
//        ICE_DRAGON_ROOST_CF = registerSimple.apply("ice_dragon_roost", ICE_DRAGON_ROOST.get());
//        LIGHTNING_DRAGON_ROOST_CF = registerSimple.apply("lightning_dragon_roost", LIGHTNING_DRAGON_ROOST.get());
//        FIRE_DRAGON_CAVE_CF = registerSimple.apply("fire_dragon_cave", FIRE_DRAGON_CAVE.get());
        ICE_DRAGON_CAVE_CF = registerSimple.apply("ice_dragon_cave", ICE_DRAGON_CAVE.get());
        LIGHTNING_DRAGON_CAVE_CF = registerSimple.apply("lightning_dragon_cave", LIGHTNING_DRAGON_CAVE.get());

        CYCLOPS_CAVE_CF = registerSimple.apply("cyclops_cave", CYCLOPS_CAVE.get());
        PIXIE_VILLAGE_CF = registerSimple.apply("pixie_village", PIXIE_VILLAGE.get());
        SIREN_ISLAND_CF = registerSimple.apply("siren_island", SIREN_ISLAND.get());
        HYDRA_CAVE_CF = registerSimple.apply("hydra_cave", HYDRA_CAVE.get());
        MYRMEX_HIVE_DESERT_CF = registerSimple.apply("myrmex_hive_desert", MYRMEX_HIVE_DESERT.get());
        MYRMEX_HIVE_JUNGLE_CF = registerSimple.apply("myrmex_hive_jungle", MYRMEX_HIVE_JUNGLE.get());
        SPAWN_DEATH_WORM_CF = registerSimple.apply("spawn_death_worm", SPAWN_DEATH_WORM.get());
        SPAWN_DRAGON_SKELETON_L_CF = registerSimple.apply("spawn_lightning_dragon_skeleton", SPAWN_DRAGON_SKELETON_L.get());
        SPAWN_DRAGON_SKELETON_F_CF = registerSimple.apply("spawn_fire_dragon_skeleton", SPAWN_DRAGON_SKELETON_F.get());
        SPAWN_DRAGON_SKELETON_I_CF = registerSimple.apply("spawn_ice_dragon_skeleton", SPAWN_DRAGON_SKELETON_I.get());
        SPAWN_HIPPOCAMPUS_CF = registerSimple.apply("spawn_hippocampus", SPAWN_HIPPOCAMPUS.get());
        SPAWN_SEA_SERPENT_CF = registerSimple.apply("spawn_seaserpent", SPAWN_SEA_SERPENT.get());
        SPAWN_STYMPHALIAN_BIRD_CF = registerSimple.apply("spawn_stymphalian_bird", SPAWN_STYMPHALIAN_BIRD.get());
        SPAWN_WANDERING_CYCLOPS_CF = registerSimple.apply("spawn_wandering_cyclops", SPAWN_WANDERING_CYCLOPS.get());
    }

//     public static void registerStructureSet(Holder<Structure> structure, String name, int spacing, int separation, int seed) {
//         BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, new ResourceLocation(IceAndFire.MODID, name), new StructureSet(structure, new RandomSpreadStructurePlacement(spacing, separation, RandomSpreadType.LINEAR, seed)));
//         BuiltinRegistries.register(
//                 BuiltinRegistries.STRUCTURE_SETS,
//                 ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation("%s/%s".formatted(IceAndFire.MODID, name))),
//                 new StructureSet(structure,
//                         new RandomSpreadStructurePlacement(spacing, separation, RandomSpreadType.LINEAR, seed)));
//     }
//
//     public static Holder<Structure> registerConfiguredStructureFeature(String name, RegistryObject<Structure> structure, TagKey<Biome> biomeTag) {
//         // Placeholder pools since we haven't loaded our own json files at this stage
//         var DUMMY_CONFIG = new ConfiguredFeature<>(PlainVillagePools.START, 0);
//         return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, "%s:%s".formatted(IceAndFire.MODID, name), structure.get().configured(DUMMY_CONFIG, biomeTag, false));
//     }
//
//     public static void registerStructureConfiguredFeatures()
//     {
//
//         GORGON_TEMPLE_CF = registerConfiguredStructureFeature("gorgon_temple", GORGON_TEMPLE, HAS_GORGON_TEMPLE);
//         MAUSOLEUM_CF = registerConfiguredStructureFeature("mausoleum", DREAD_MAUSOLEUM, HAS_MAUSOLEUM);
//         GRAVEYARD_CF = registerConfiguredStructureFeature("graveyard", GRAVEYARD, HAS_GRAVEYARD);
//
//         int average = (int) Math.ceil(IntStream.of(IafConfig.spawnGorgonsChance, IafConfig.generateMausoleumChance, IafConfig.generateGraveyardChance * 3).average().getAsDouble());
//
//         StructureSet structures = new StructureSet(
//                 List.of(
//                         new StructureSet.StructureSelectionEntry(GRAVEYARD_CF, IafConfig.generateGraveyardChance * 3),
//                         new StructureSet.StructureSelectionEntry(MAUSOLEUM_CF, IafConfig.generateMausoleumChance),
//                         new StructureSet.StructureSelectionEntry(GORGON_TEMPLE_CF, IafConfig.spawnGorgonsChance)
//                 ),
//                 new RandomSpreadStructurePlacement(Math.max(average, 2), Math.max(average / 2, 1), RandomSpreadType.LINEAR, 342226450));
//
//         BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, new ResourceLocation(IceAndFire.MODID, "structures"), structures);
//     }


    public static boolean isFarEnoughFromSpawn(LevelAccessor world, BlockPos pos) {
        LevelData spawnPoint = world.getLevelData();
        BlockPos spawnRelative = new BlockPos(spawnPoint.getXSpawn(), pos.getY(), spawnPoint.getYSpawn());

        boolean spawnCheck = !spawnRelative.closerThan(pos, IafConfig.dangerousWorldGenDistanceLimit);
        return spawnCheck;
    }

    public static boolean isDimensionListedForFeatures(ServerLevelAccessor world) {
        ResourceLocation name = world.getLevel().dimension().location();
        if (name == null) {
            return false;
        }
        if (IafConfig.useDimensionBlackList) {
            for (String blacklisted : IafConfig.featureBlacklistedDimensions) {
                if (name.toString().equals(blacklisted)) {
                    return false;
                }
            }
            return true;
        } else {
            for (String whitelist : IafConfig.featureWhitelistedDimensions) {
                if (name.toString().equals(whitelist)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isDimensionListedForDragons(ServerLevelAccessor world) {
        ResourceLocation name = world.getLevel().dimension().location();
        if (name == null) {
            return false;
        }
        if (IafConfig.useDimensionBlackList) {
            for (String blacklisted : IafConfig.dragonBlacklistedDimensions) {
                if (name.toString().equals(blacklisted)) {
                    return false;
                }
            }
            return true;
        } else {
            for (String whitelist : IafConfig.dragonWhitelistedDimensions) {
                if (name.toString().equals(whitelist)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isDimensionListedForMobs(ServerLevelAccessor world) {
        ResourceLocation name = world.getLevel().dimension().location();
        if (name == null) {
            return false;
        }
        if (IafConfig.useDimensionBlackList) {
            for (String blacklisted : IafConfig.mobBlacklistedDimensions) {
                if (name.toString().equals(blacklisted)) {
                    return false;
                }
            }
            return true;
        } else {
            for (String whitelist : IafConfig.mobWhitelistedDimensions) {
                if (name.toString().equals(whitelist)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isFarEnoughFromDangerousGen(ServerLevelAccessor world, BlockPos pos) {
        boolean canGen = true;
        IafWorldData data = IafWorldData.get(world.getLevel());
        if (data != null) {
            BlockPos last = data.lastGeneratedDangerousStructure;
            canGen = last.distSqr(pos) > IafConfig.dangerousWorldGenSeparationLimit * IafConfig.dangerousWorldGenSeparationLimit;
            if (canGen) {
                data.setLastGeneratedDangerousStructure(pos);
            }

        }
        return canGen;
    }
}
