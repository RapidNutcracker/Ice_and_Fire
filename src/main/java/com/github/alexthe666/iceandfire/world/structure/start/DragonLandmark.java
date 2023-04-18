package com.github.alexthe666.iceandfire.world.structure.start;

import com.github.alexthe666.iceandfire.IafLandmark;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.util.LegacyLandmarkPlacements;
import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.github.alexthe666.iceandfire.world.structure.util.ControlledSpawns;
import com.github.alexthe666.iceandfire.world.structure.util.DecorationClearance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

// Landmark structure without progression lock; Hollow Hills/Hedge Maze/Naga Courtyard/Quest Grove
public class DragonLandmark extends ProgressionStructure {
    public final IafLandmark feature;

    public static final Codec<DragonLandmark> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(IafLandmark.CODEC.fieldOf("legacy_landmark_start").forGetter(DragonLandmark::getFeatureType))
            .and(progressionCodec(instance))
            .apply(instance, DragonLandmark::new)
    );

    public DragonLandmark(IafLandmark landmark, AdvancementLockConfig advancementLockConfig, HintConfig hintConfig, ControlledSpawningConfig controlledSpawningConfig, DecorationConfig decorationConfig, StructureSettings structureSettings) {
        super(advancementLockConfig, hintConfig, controlledSpawningConfig, decorationConfig, structureSettings);
        this.feature = landmark;
    }

    public static DragonLandmark extractLandmark(IafLandmark landmark) {
        return new DragonLandmark(
                landmark,
                new AdvancementLockConfig(landmark.getRequiredAdvancements()),
                new HintConfig(landmark.createHintBook(), IafEntityRegistry.PIXIE.get()),
                ControlledSpawns.ControlledSpawningConfig.create(landmark.getSpawnableMonsterLists(), landmark.getAmbientCreatureList(), landmark.getWaterCreatureList()),
                new DecorationClearance.DecorationConfig(landmark.isSurfaceDecorationsAllowed(), landmark.isUndergroundDecoAllowed(), landmark.shouldAdjustToTerrain()),
                new Structure.StructureSettings(
                        BuiltinRegistries.BIOME.getOrCreateTag(landmark.getBiomeTag()),
                        Map.of(), // Landmarks have Controlled Mob spawning
                        landmark.getDecorationStage(),
                        landmark.getBeardifierContribution()
                )
        );
    }

    @Override
    // [VANILLA COPY] Structure.generate
    public StructureStart generate(RegistryAccess registryAccess, ChunkGenerator chunkGen, BiomeSource biomeSource, RandomState randomState, StructureTemplateManager structureTemplateManager, long seed, ChunkPos chunkPos, int references, LevelHeightAccessor heightAccessor, Predicate<Holder<Biome>> biomeAt) {
        return LegacyLandmarkPlacements.chunkHasLandmarkCenter(chunkPos) ? super.generate(registryAccess, chunkGen, biomeSource, randomState, structureTemplateManager, seed, chunkPos, references, heightAccessor, biomeAt) : StructureStart.INVALID_START;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return this.feature.generateStub(context);
    }

    @Override
    public StructureType<?> type() {
        return IafStructureTypes.FIRE_DRAGON_ROOST_STRUCTURE.get();
    }

    public IafLandmark getFeatureType() {
        return this.feature;
    }

}
