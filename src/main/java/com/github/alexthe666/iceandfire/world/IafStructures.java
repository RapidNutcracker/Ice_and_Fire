package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IafLandmark;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.world.structure.start.DragonLandmark;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class IafStructures {

    /**
     * We are using the Deferred Registry system to register our structure as this is the preferred way on Forge.
     * This will handle registering the base structure for us at the correct time, so we don't have to handle it ourselves.
     */
    public static final DeferredRegister<Structure> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_REGISTRY, IceAndFire.MODID);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the resourceLocation of structure_tutorial:dread_mausoleum_structure.
     */
    public static final RegistryObject<Structure> FIRE_DRAGON_ROOST_LANDMARK = STRUCTURES.register("fire_dragon_roost_landmark", () -> DragonLandmark.extractLandmark(IafLandmark.FIRE_DRAGON_ROOST));


    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}