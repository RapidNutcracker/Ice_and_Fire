package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class IceDragonRoostStructure extends SinglePieceStructure {
    public static final Codec<IceDragonRoostStructure> CODEC = simpleCodec(IceDragonRoostStructure::new);

    public IceDragonRoostStructure(Structure.StructureSettings structureSettings) {
        super(IceDragonRoostPiece::new, 12, 6, structureSettings);
    }

    public StructureType<?> type() {
        return IafStructureTypes.ICE_DRAGON_ROOST_STRUCTURE.get();
    }
}
