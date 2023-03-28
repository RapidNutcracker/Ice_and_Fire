package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.world.gen.processor.VillageHouseProcessor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = IceAndFire.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IafVillagerRegistry {

    private static final String[] VILLAGE_TYPES = new String[]{"plains", "desert", "snowy", "savanna", "taiga"};
    private static final Holder<StructureProcessorList> HOUSE_PROCESSOR = BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, new ResourceLocation("iceandfire:village_house_processor"), genVillageHouseProcessor());

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, IceAndFire.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, IceAndFire.MODID);
    
    public static final RegistryObject<PoiType> LECTERN_POI = POI_TYPES.register(
            "lectern_poi",
            () -> new PoiType(
                    ImmutableSet.copyOf(IafBlockRegistry.LECTERN.get().getStateDefinition().getPossibleStates()),
                    1, 1));
    public static final RegistryObject<VillagerProfession> SCRIBE = VILLAGER_PROFESSIONS.register(
            "scribe",
            () -> new VillagerProfession(
                    "scribe",
                    x -> x.get() == LECTERN_POI.get(),
                    x -> x.get() == LECTERN_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_LIBRARIAN));

    public static void setup() {
        if (IafConfig.villagerHouseWeight > 0) {
            PlainVillagePools.bootstrap();
            SnowyVillagePools.bootstrap();
            SavannaVillagePools.bootstrap();
            DesertVillagePools.bootstrap();
            TaigaVillagePools.bootstrap();

            for (String type : VILLAGE_TYPES) {
                addStructureToPool(new ResourceLocation("village/" + type + "/houses"), new ResourceLocation("iceandfire", "village/" + type + "_scriber_1"), IafConfig.villagerHouseWeight);
            }
        }

        registerPOIs();
    }

    public static void registerPOIs() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, LECTERN_POI.get());
        } catch (InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }

    public static void addScribeTrades(VillagerTradesEvent event) {
        final float emeraldForItemsMultiplier = 0.05F; //Values taken from VillagerTrades.java
        final float itemForEmeraldMultiplier = 0.05F;
        final float rareItemForEmeraldMultiplier = 0.2F;

        if (event.getType() == SCRIBE.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        
            trades.get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(IafItemRegistry.MANUSCRIPT.get(), 4), 25, 2, emeraldForItemsMultiplier));
            trades.get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.BOOKSHELF, 3), new ItemStack(Items.EMERALD, 1), 8, 3, itemForEmeraldMultiplier));
            trades.get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.PAPER, 15), new ItemStack(Items.EMERALD, 2), 4, 4, itemForEmeraldMultiplier));
            trades.get(1).add((entity, random) -> new MerchantOffer(new ItemStack(IafBlockRegistry.ASH.get(), 10), new ItemStack(Items.EMERALD, 1), 8, 4, itemForEmeraldMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(IafItemRegistry.SILVER_INGOT.get(), 5), new ItemStack(Items.EMERALD, 1), 3, 5, itemForEmeraldMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(IafBlockRegistry.FIRE_LILY.get(), 8), new ItemStack(Items.EMERALD, 1), 3, 5, itemForEmeraldMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(IafBlockRegistry.LIGHTNING_LILY.get(), 7), new ItemStack(Items.EMERALD, 3), 2, 5, itemForEmeraldMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(IafBlockRegistry.FROST_LILY.get(), 4), 3, 3, emeraldForItemsMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(IafBlockRegistry.DRAGON_ICE_SPIKES.get(), 7), 2, 3, emeraldForItemsMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(IafItemRegistry.SAPPHIRE_GEM.get()), new ItemStack(Items.EMERALD, 2), 30, 3, rareItemForEmeraldMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(IafBlockRegistry.JAR_EMPTY.get(), 1), 3, 4, emeraldForItemsMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(IafItemRegistry.MYRMEX_DESERT_RESIN.get(), 1), 40, 2, emeraldForItemsMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(IafItemRegistry.MYRMEX_JUNGLE_RESIN.get(), 1), 40, 2, emeraldForItemsMultiplier));
            trades.get(2).add((entity, random) -> new MerchantOffer(new ItemStack(IafItemRegistry.AMETHYST_GEM.get()), new ItemStack(Items.EMERALD, 3), 20, 3, rareItemForEmeraldMultiplier));
            trades.get(3).add((entity, random) -> new MerchantOffer(new ItemStack(IafItemRegistry.DRAGON_BONE.get(), 6), new ItemStack(Items.EMERALD, 1), 7, 4, itemForEmeraldMultiplier));
            trades.get(3).add((entity, random) -> new MerchantOffer(new ItemStack(IafItemRegistry.CHAIN.get(), 2), new ItemStack(Items.EMERALD, 3), 4, 2, itemForEmeraldMultiplier));
            trades.get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(IafItemRegistry.PIXIE_DUST.get(), 2), 8, 3, emeraldForItemsMultiplier));
            trades.get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(IafItemRegistry.FIRE_DRAGON_FLESH.get(), 2), 8, 3, emeraldForItemsMultiplier));
            trades.get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 7), new ItemStack(IafItemRegistry.ICE_DRAGON_FLESH.get(), 1), 8, 3, emeraldForItemsMultiplier));
            trades.get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 8), new ItemStack(IafItemRegistry.LIGHTNING_DRAGON_FLESH.get(), 1), 8, 3, emeraldForItemsMultiplier));
            trades.get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 10), new ItemStack(IafItemRegistry.DRAGON_BONE.get(), 2), 20, 5, emeraldForItemsMultiplier));
            trades.get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(IafItemRegistry.SHINY_SCALES.get(), 1), 5, 2, emeraldForItemsMultiplier));
            trades.get(4).add((entity, random) -> new MerchantOffer(new ItemStack(IafItemRegistry.DREAD_SHARD.get(), 5), new ItemStack(Items.EMERALD, 1), 10, 4, itemForEmeraldMultiplier));
            trades.get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 8), new ItemStack(IafItemRegistry.STYMPHALIAN_BIRD_FEATHER.get(), 12), 3, 6, emeraldForItemsMultiplier));
            trades.get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(IafItemRegistry.TROLL_TUSK.get(), 12), 7, 3, emeraldForItemsMultiplier));
            trades.get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 15), new ItemStack(IafItemRegistry.SERPENT_FANG.get(), 3), 20, 3, emeraldForItemsMultiplier));
            trades.get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 12), new ItemStack(IafItemRegistry.HYDRA_FANG.get(), 1), 20, 3, emeraldForItemsMultiplier));
            trades.get(5).add((entity, random) -> new MerchantOffer(new ItemStack(IafItemRegistry.ECTOPLASM.get(), 6), new ItemStack(Items.EMERALD, 1), 7, 3, itemForEmeraldMultiplier));
        }
    }

    private static StructureProcessorList genVillageHouseProcessor() {
        RuleProcessor mossify = new RuleProcessor(ImmutableList.of(new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState())));
        return new StructureProcessorList(ImmutableList.of(mossify, new VillageHouseProcessor()));
    }
    
    private static StructurePoolElement createWorkstation(String name) {
        return StructurePoolElement.legacy(new ResourceLocation("iceandfire", name).toString()).apply(StructureTemplatePool.Projection.RIGID);
    }

    private static void addStructureToPool(ResourceLocation pool, ResourceLocation toAdd, int weight) {
        StructureTemplatePool old = BuiltinRegistries.TEMPLATE_POOL.get(pool);
        List<StructurePoolElement> shuffled = old != null ? old.getShuffledTemplates(RandomSource.create()) : ImmutableList.of();
        Object2IntMap<StructurePoolElement> recomputedPieces = new Object2IntLinkedOpenHashMap<>();

        // Recompute the weights
        for (StructurePoolElement p : shuffled)
            recomputedPieces.computeInt(p, (StructurePoolElement pTemp, Integer i) -> (i == null ? 0 : i) + 1);

        // Create the needed list
        List<Pair<StructurePoolElement, Integer>> newPieces = recomputedPieces.object2IntEntrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getIntValue()))
                .collect(Collectors.toList());

        // Add the element with the appropriate weight
        newPieces.add(new Pair<>(StructurePoolElement.legacy(toAdd.toString(), HOUSE_PROCESSOR).apply(StructureTemplatePool.Projection.RIGID), weight));

        ResourceLocation name = old.getName();
        int id = BuiltinRegistries.TEMPLATE_POOL.getId(old);
        ((WritableRegistry<StructureTemplatePool>) BuiltinRegistries.TEMPLATE_POOL).registerOrOverride(
                OptionalInt.of(id),
                ResourceKey.create(BuiltinRegistries.TEMPLATE_POOL.key(), name),
                new StructureTemplatePool(pool, name, newPieces),
                Lifecycle.stable());
    }

}
