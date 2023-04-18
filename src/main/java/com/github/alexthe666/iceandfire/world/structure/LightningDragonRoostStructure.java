package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class LightningDragonRoostStructure extends SinglePieceStructure {
    public static final Codec<LightningDragonRoostStructure> CODEC = simpleCodec(LightningDragonRoostStructure::new);

    public LightningDragonRoostStructure(StructureSettings structureSettings) {
        super(LightningDragonRoostPiece::new, 12, 6, structureSettings);
    }

    public StructureType<?> type() {
        return IafStructureTypes.LIGHTNING_DRAGON_ROOST_STRUCTURE.get();
    }
}
