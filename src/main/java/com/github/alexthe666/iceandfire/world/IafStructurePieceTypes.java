package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.world.structure.DragonRoostComponent;
import com.github.alexthe666.iceandfire.world.structure.FireDragonRoostPiece;
import com.github.alexthe666.iceandfire.world.structure.IceDragonRoostPiece;
import com.github.alexthe666.iceandfire.world.structure.LightningDragonRoostPiece;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class IafStructurePieceTypes {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, IceAndFire.MODID);

    public static final RegistryObject<StructurePieceType> IafDragonRoost = STRUCTURE_PIECE_TYPES.register("dragon_roost", () -> DragonRoostComponent::new);
    public static final RegistryObject<StructurePieceType> FIRE_DRAGON_ROOST_PIECE = STRUCTURE_PIECE_TYPES.register("fire_dragon_roost_piece", () -> FireDragonRoostPiece::new);
    public static final RegistryObject<StructurePieceType> ICE_DRAGON_ROOST_PIECE = STRUCTURE_PIECE_TYPES.register("ice_dragon_roost_piece", () -> IceDragonRoostPiece::new);
    public static final RegistryObject<StructurePieceType> LIGHTNING_DRAGON_ROOST_PIECE = STRUCTURE_PIECE_TYPES.register("lightning_dragon_roost_piece", () -> LightningDragonRoostPiece::new);

    public static void register(IEventBus eventBus) {
        STRUCTURE_PIECE_TYPES.register(eventBus);
    }
}