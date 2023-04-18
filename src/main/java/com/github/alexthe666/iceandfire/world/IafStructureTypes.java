package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.world.structure.*;
import com.github.alexthe666.iceandfire.world.structure.start.DragonLandmark;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class IafStructureTypes {

    /**
     * We are using the Deferred Registry system to register our structure as this is the preferred way on Forge.
     * This will handle registering the base structure for us at the correct time, so we don't have to handle it ourselves.
     */
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, IceAndFire.MODID);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the resourceLocation of structure_tutorial:dread_mausoleum_structure.
     */
    public static final RegistryObject<StructureType<JigsawStructure>> DREAD_MAUSOLEUM_STRUCTURE = STRUCTURE_TYPES.register("dread_mausoleum_structure", () -> explicitStructureTypeTyping(JigsawStructure.CODEC));
    public static final RegistryObject<StructureType<JigsawStructure>> GORGON_TEMPLE_STRUCTURE = STRUCTURE_TYPES.register("gorgon_temple_structure", () -> explicitStructureTypeTyping(JigsawStructure.CODEC));
    public static final RegistryObject<StructureType<JigsawStructure>> GRAVEYARD_STRUCTURE = STRUCTURE_TYPES.register("graveyard", () -> explicitStructureTypeTyping(JigsawStructure.CODEC));

    public static final RegistryObject<StructureType<FireDragonRoostStructure>> FIRE_DRAGON_ROOST_STRUCTURE = STRUCTURE_TYPES.register("fire_dragon_roost_structure", () -> explicitStructureTypeTyping(FireDragonRoostStructure.CODEC));
    public static final RegistryObject<StructureType<IceDragonRoostStructure>> ICE_DRAGON_ROOST_STRUCTURE = STRUCTURE_TYPES.register("ice_dragon_roost_structure", () -> explicitStructureTypeTyping(IceDragonRoostStructure.CODEC));
    public static final RegistryObject<StructureType<LightningDragonRoostStructure>> LIGHTNING_DRAGON_ROOST_STRUCTURE = STRUCTURE_TYPES.register("lightning_dragon_roost_structure", () -> explicitStructureTypeTyping(LightningDragonRoostStructure.CODEC));

    public static final RegistryObject<StructureType<FireDragonCaveStructure>> FIRE_DRAGON_CAVE_STRUCTURE = STRUCTURE_TYPES.register("fire_dragon_cave_structure", () -> explicitStructureTypeTyping(FireDragonCaveStructure.CODEC));
//    public static final RegistryObject<StructureType<IceDragonRoostStructure>> ICE_DRAGON_ROOST_STRUCTURE = STRUCTURE_TYPES.register("ice_dragon_roost_structure", () -> explicitStructureTypeTyping(IceDragonRoostStructure.CODEC));
//    public static final RegistryObject<StructureType<LightningDragonRoostStructure>> LIGHTNING_DRAGON_ROOST_STRUCTURE = STRUCTURE_TYPES.register("lightning_dragon_roost_structure", () -> explicitStructureTypeTyping(LightningDragonRoostStructure.CODEC));

    /**
     * Originally, I had a double lambda ()->()-> for the RegistryObject line above, but it turns out that
     * some IDEs cannot resolve the typing correctly. This method explicitly states what the return type
     * is so that the IDE can put it into the DeferredRegistry properly.
     */
    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }

    public static void register(IEventBus eventBus) {
        STRUCTURE_TYPES.register(eventBus);
    }
}