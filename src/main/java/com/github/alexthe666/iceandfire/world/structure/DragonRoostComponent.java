package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.IafLandmark;
import com.github.alexthe666.iceandfire.world.IafStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class DragonRoostComponent extends StructurePiece {

    private final int roostSize;
    final int radius;
    final int diameter;

    public DragonRoostComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
        this(IafStructurePieceTypes.IafDragonRoost.get(), nbt);
    }

    public DragonRoostComponent(StructurePieceType piece, CompoundTag nbt) {
        super(piece, nbt);
        roostSize = nbt.getInt("roostSize");
        this.radius = ((roostSize * 2 + 1) * 8) - 6;
        this.diameter = (roostSize * 2 + 1) * 16;
    }

    public DragonRoostComponent(StructurePieceType piece, IafLandmark feature, int i, int size, int x, int y, int z) {
        super(piece, i, new BoundingBox(x, y, z, x + size, y + size, z + size));

        this.setOrientation(Direction.SOUTH);

        // get the size of this hill?
        this.roostSize = size;
        this.radius = ((roostSize * 2 + 1) * 8) - 6;
        this.diameter = (roostSize * 2 + 1) * 16;

        // can we determine the size here?
        this.boundingBox = feature.getComponentToAddBoundingBox(x, y, z, -radius, -(3 + roostSize), -radius, radius * 2, radius / (roostSize == 1 ? 2 : roostSize), radius * 2, Direction.SOUTH);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
//        super.addAdditionalSaveData(pContext, pTag);
        pTag.putInt("roostSize", roostSize);
    }

    @Override
    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {

    }
}
