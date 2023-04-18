package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class DragonCaveStructure extends SinglePieceStructure {
    public static final Codec<DragonCaveStructure> CODEC = simpleCodec(DragonCaveStructure::new);

    public DragonCaveStructure(StructureSettings structureSettings) {
        super(FireDragonRoostPiece::new, 128, 128, structureSettings);
    }

    public StructureType<?> type() {
        return IafStructureTypes.FIRE_DRAGON_CAVE_STRUCTURE.get();
    }
}
