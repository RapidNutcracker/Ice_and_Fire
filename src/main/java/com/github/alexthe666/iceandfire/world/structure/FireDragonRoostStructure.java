package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.github.alexthe666.iceandfire.world.biomemodifiers.AddFeaturesWithExceptionsModifier;
import com.github.alexthe666.iceandfire.world.structure.util.ControlledSpawns;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.List;

public class FireDragonRoostStructure extends SinglePieceStructure {
    public static final Codec<FireDragonRoostStructure> CODEC = simpleCodec(FireDragonRoostStructure::new);

    public FireDragonRoostStructure(StructureSettings structureSettings) {
        super(FireDragonRoostPiece::new, 35, 35, structureSettings);
    }

    public StructureType<?> type() {
        return IafStructureTypes.FIRE_DRAGON_ROOST_STRUCTURE.get();
    }
}
