package com.github.alexthe666.iceandfire.loot;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.core.Registry.LOOT_FUNCTION_TYPE;

public class IafLootRegistry {

//    public static LootItemFunctionType CUSTOMIZE_TO_DRAGON;
//    public static LootItemFunctionType CUSTOMIZE_TO_SERPENT;

//    public static final DeferredRegister<LootItemFunction> REGISTER = DeferredRegister.create(Registry.LOOT_FUNCTION_TYPE, IceAndFire.MODID);

    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(LOOT_FUNCTION_TYPE.key(), IceAndFire.MODID);

    public static final RegistryObject<LootItemFunctionType> CUSTOMIZE_TO_DRAGON = LOOT_FUNCTION_TYPES.register("customize_to_dragon",
            () -> new LootItemFunctionType(new CustomizeToDragon.Serializer()));

    public static final RegistryObject<LootItemFunctionType> CUSTOMIZE_TO_SEA_SERPENT = LOOT_FUNCTION_TYPES.register("customize_to_sea_serpent",
            () -> new LootItemFunctionType(new CustomizeToDragon.Serializer()));


    public static void register(IEventBus eventBus) {
        LOOT_FUNCTION_TYPES.register(eventBus);
    }

//    private static LootItemFunctionType register(String p_237451_0_, Serializer<? extends LootItemFunction> p_237451_1_) {
//        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(p_237451_0_), new LootItemFunctionType(p_237451_1_));
//    }
//
//    public static void init() {
//        CUSTOMIZE_TO_DRAGON = register("iceandfire:customize_to_dragon", new CustomizeToDragon.Serializer());
//        CUSTOMIZE_TO_SERPENT = register("iceandfire:customize_to_sea_serpent", new CustomizeToSeaSerpent.Serializer());
//    }

}
