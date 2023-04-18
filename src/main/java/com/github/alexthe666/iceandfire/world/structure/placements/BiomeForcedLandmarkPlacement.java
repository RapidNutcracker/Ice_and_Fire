package com.github.alexthe666.iceandfire.world.structure.placements;

import com.github.alexthe666.iceandfire.IafLandmark;
import com.github.alexthe666.iceandfire.util.LegacyLandmarkPlacements;
import com.github.alexthe666.iceandfire.world.IafStructurePlacementTypes;
import com.github.alexthe666.iceandfire.world.components.chunkgenerators.IafChunkGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

public class BiomeForcedLandmarkPlacement extends StructurePlacement {
    public static final Codec<BiomeForcedLandmarkPlacement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            IafLandmark.CODEC.fieldOf("landmark_set").forGetter(p -> p.landmark),
            Codec.intRange(-64, 256).fieldOf("scan_elevation").forGetter(p -> p.scanHeight)
    ).apply(inst, BiomeForcedLandmarkPlacement::new));

    private final IafLandmark landmark;
    private final int scanHeight;

    public BiomeForcedLandmarkPlacement(IafLandmark landmark, int biomeScanHeight) {
        super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1f, 0, Optional.empty()); // None of these params matter except for possibly flat-world or whatever

        this.landmark = landmark;
        this.scanHeight = biomeScanHeight;
    }

    @Override
    public boolean isStructureChunk(ChunkGenerator chunkGenerator, RandomState randomState, long seed, int chunkX, int chunkZ) {
        return this.isPlacementChunk(chunkGenerator, randomState, seed, chunkX, chunkZ);
    }

    @Override
    public boolean isPlacementChunk(ChunkGenerator chunkGenerator, RandomState randomState, long seed, int chunkX, int chunkZ) {
        if (chunkGenerator instanceof IafChunkGenerator iafChunkGenerator)
            return iafChunkGenerator.isLandmarkPickedForChunk(this.landmark, chunkGenerator.getBiomeSource().getNoiseBiome(chunkX << 2, this.scanHeight, chunkZ << 2, randomState.sampler()), chunkX, chunkZ, seed);

        if (!LegacyLandmarkPlacements.chunkHasLandmarkCenter(chunkX, chunkZ))
            return false;

        return LegacyLandmarkPlacements.pickVarietyLandmark(chunkX, chunkZ, seed) == this.landmark;
    }

    @Override
    public StructurePlacementType<?> type() {
        return IafStructurePlacementTypes.FORCED_LANDMARK_PLACEMENT_TYPE.get();
    }

    public IafLandmark getLandmark() {
        return this.landmark;
    }
}
