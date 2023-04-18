package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class FireDragonCaveStructure extends SinglePieceStructure {
    public static final Codec<FireDragonCaveStructure> CODEC = simpleCodec(FireDragonCaveStructure::new);

    public FireDragonCaveStructure(StructureSettings structureSettings) {
        super(FireDragonCavePiece::new, 128, 128, structureSettings);
    }

    public StructureType<?> type() {
        return IafStructureTypes.FIRE_DRAGON_CAVE_STRUCTURE.get();
    }
}
