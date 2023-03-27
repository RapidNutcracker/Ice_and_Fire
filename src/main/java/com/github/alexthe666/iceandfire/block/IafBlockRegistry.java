package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumDragonType;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.item.BlockItemWithRender;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = IceAndFire.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IafBlockRegistry {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IceAndFire.MODID);

    public static final RegistryObject<Block> LECTERN = registerBlock("lectern", () -> new BlockLectern(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PODIUM_OAK = registerBlock("podium_oak", () -> new BlockPodium(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PODIUM_BIRCH = registerBlock("podium_birch", () -> new BlockPodium(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PODIUM_SPRUCE = registerBlock("podium_spruce", () -> new BlockPodium(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PODIUM_JUNGLE = registerBlock("podium_jungle", () -> new BlockPodium(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PODIUM_DARK_OAK = registerBlock("podium_dark_oak", () -> new BlockPodium(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PODIUM_ACACIA = registerBlock("podium_acacia", () -> new BlockPodium(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FIRE_LILY = registerBlock("fire_lily", () -> new BlockElementalFlower(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> POTTED_FIRE_LILY = BLOCKS.register("potted_fire_lily", () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), IafBlockRegistry.FIRE_LILY, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)));
    public static final RegistryObject<Block> FROST_LILY = registerBlock("frost_lily", () -> new BlockElementalFlower(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> POTTED_FROST_LILY = BLOCKS.register("potted_frost_lily", () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), IafBlockRegistry.FROST_LILY, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)));
    public static final RegistryObject<Block> LIGHTNING_LILY = registerBlock("lightning_lily", () -> new BlockElementalFlower(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> POTTED_LIGHTNING_LILY = BLOCKS.register("potted_lightning_lily", () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), IafBlockRegistry.LIGHTNING_LILY, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)));
    public static final RegistryObject<Block> GOLD_PILE = registerBlock("gold_pile", () -> new BlockGoldPile(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SILVER_PILE = registerBlock("silver_pile", () -> new BlockGoldPile(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> COPPER_PILE = registerBlock("copper_pile", () -> new BlockGoldPile(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new BlockIafOre(3.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new BlockGeneric(Material.METAL, 3.0F, 5.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> COPPER_ORE = registerBlock("copper_ore", () -> new BlockIafOre(3.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> COPPER_BLOCK = registerBlock("copper_block", () -> new BlockGeneric(Material.METAL, 4.0F, 5.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);

    // region Gemstones
    public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block", () -> new BlockGeneric(Material.METAL, 3.0F, 6.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> AMETHYST_ORE = registerBlock("amethyst_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_AMETHYST_ORE = registerBlock("deepslate_amethyst_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_AMETHYST_ORE = registerBlock("charred_amethyst_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_AMETHYST_ORE = registerBlock("frozen_amethyst_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_AMETHYST_ORE = registerBlock("crackled_amethyst_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> RAW_CRYSTAL_BLOCK = registerBlock("raw_crystal_block", () -> new BlockIafGem(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).color(MaterialColor.COLOR_LIGHT_GRAY)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> BUDDING_CRYSTAL = registerBlock("budding_crystal", () -> new BlockBuddingCrystal(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRYSTAL_CLUSTER = registerBlock("crystal_cluster", () -> new BlockIafGemCluster(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> LARGE_CRYSTAL_BUD = registerBlock("large_crystal_bud", () -> new BlockIafGemCluster(5, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MEDIUM_CRYSTAL_BUD = registerBlock("medium_crystal_bud", () -> new BlockIafGemCluster(4, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SMALL_CRYSTAL_BUD = registerBlock("small_crystal_bud", () -> new BlockIafGemCluster(3, 4, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRYSTAL_BLOCK = registerBlock("crystal_block", () -> new BlockGeneric(Material.METAL, 3.0F, 6.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRYSTAL_ORE = registerBlock("crystal_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_CRYSTAL_ORE = registerBlock("deepslate_crystal_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_CRYSTAL_ORE = registerBlock("charred_crystal_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_CRYSTAL_ORE = registerBlock("frozen_crystal_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_CRYSTAL_ORE = registerBlock("crackled_crystal_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> RAW_EMERALD_BLOCK = registerBlock("raw_emerald_block", () -> new BlockIafGem(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).color(MaterialColor.COLOR_LIGHT_GREEN)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> BUDDING_EMERALD = registerBlock("budding_emerald", () -> new BlockBuddingEmerald(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> EMERALD_CLUSTER = registerBlock("emerald_cluster", () -> new BlockIafGemCluster(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> LARGE_EMERALD_BUD = registerBlock("large_emerald_bud", () -> new BlockIafGemCluster(5, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MEDIUM_EMERALD_BUD = registerBlock("medium_emerald_bud", () -> new BlockIafGemCluster(4, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SMALL_EMERALD_BUD = registerBlock("small_emerald_bud", () -> new BlockIafGemCluster(3, 4, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_EMERALD_ORE = registerBlock("charred_emerald_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_EMERALD_ORE = registerBlock("frozen_emerald_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_EMERALD_ORE = registerBlock("crackled_emerald_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> RAW_RUBY_BLOCK = registerBlock("raw_ruby_block", () -> new BlockIafGem(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).color(MaterialColor.COLOR_RED)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> BUDDING_RUBY = registerBlock("budding_ruby", () -> new BlockBuddingRuby(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> RUBY_CLUSTER = registerBlock("ruby_cluster", () -> new BlockIafGemCluster(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> LARGE_RUBY_BUD = registerBlock("large_ruby_bud", () -> new BlockIafGemCluster(5, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MEDIUM_RUBY_BUD = registerBlock("medium_ruby_bud", () -> new BlockIafGemCluster(4, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SMALL_RUBY_BUD = registerBlock("small_ruby_bud", () -> new BlockIafGemCluster(3, 4, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new BlockGeneric(Material.METAL, 3.0F, 6.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_RUBY_ORE = registerBlock("charred_ruby_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_RUBY_ORE = registerBlock("frozen_ruby_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_RUBY_ORE = registerBlock("crackled_ruby_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> RAW_SAPPHIRE_BLOCK = registerBlock("raw_sapphire_block", () -> new BlockIafGem(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).color(MaterialColor.COLOR_BLUE)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> BUDDING_SAPPHIRE = registerBlock("budding_sapphire", () -> new BlockBuddingSapphire(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_CLUSTER = registerBlock("sapphire_cluster", () -> new BlockIafGemCluster(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> LARGE_SAPPHIRE_BUD = registerBlock("large_sapphire_bud", () -> new BlockIafGemCluster(5, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MEDIUM_SAPPHIRE_BUD = registerBlock("medium_sapphire_bud", () -> new BlockIafGemCluster(4, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SMALL_SAPPHIRE_BUD = registerBlock("small_sapphire_bud", () -> new BlockIafGemCluster(3, 4, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block", () -> new BlockGeneric(Material.METAL, 3.0F, 6.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_SAPPHIRE_ORE = registerBlock("charred_sapphire_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_SAPPHIRE_ORE = registerBlock("frozen_sapphire_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_SAPPHIRE_ORE = registerBlock("crackled_sapphire_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> RAW_TOPAZ_BLOCK = registerBlock("raw_topaz_block", () -> new BlockIafGem(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).color(MaterialColor.COLOR_YELLOW)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> BUDDING_TOPAZ = registerBlock("budding_topaz", () -> new BlockBuddingTopaz(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> TOPAZ_CLUSTER = registerBlock("topaz_cluster", () -> new BlockIafGemCluster(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> LARGE_TOPAZ_BUD = registerBlock("large_topaz_bud", () -> new BlockIafGemCluster(5, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MEDIUM_TOPAZ_BUD = registerBlock("medium_topaz_bud", () -> new BlockIafGemCluster(4, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> SMALL_TOPAZ_BUD = registerBlock("small_topaz_bud", () -> new BlockIafGemCluster(3, 4, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> TOPAZ_BLOCK = registerBlock("topaz_block", () -> new BlockGeneric(Material.METAL, 3.0F, 6.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> TOPAZ_ORE = registerBlock("topaz_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_TOPAZ_ORE = registerBlock("deepslate_topaz_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_TOPAZ_ORE = registerBlock("charred_topaz_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_TOPAZ_ORE = registerBlock("frozen_topaz_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_TOPAZ_ORE = registerBlock("crackled_topaz_ore", () -> new BlockIafOre(4.0F, 3.0F), IceAndFire.TAB_BLOCKS);
    // endregion

    public static final RegistryObject<Block> CHARRED_DIRT = registerBlock("charred_dirt", () -> new BlockReturningState(Material.DIRT, 0.5F, 0.0F, SoundType.GRAVEL, Blocks.DIRT.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_GRASS = registerBlock("charred_grass", () -> new BlockReturningState(Material.GRASS, 0.6F, 0.0F, SoundType.GRAVEL, Blocks.GRASS_BLOCK.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_STONE = registerBlock("charred_stone", () -> new BlockReturningState(Material.STONE, 1.5F, 10.0F, SoundType.STONE, Blocks.STONE.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_COBBLESTONE = registerBlock("charred_cobblestone", () -> new BlockReturningState(Material.STONE, 2F, 10.0F, SoundType.STONE, Blocks.COBBLESTONE.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_GRAVEL = registerBlock("charred_gravel", () -> new BlockFallingReturningState(Material.DIRT, 0.6F, 0F, SoundType.GRAVEL, Blocks.GRAVEL.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CHARRED_DIRT_PATH = registerBlock(BlockCharredPath.getNameFromType(EnumDragonType.FIRE), () -> new BlockCharredPath(EnumDragonType.FIRE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> ASH = registerBlock("ash", () -> new BlockFallingGeneric(Material.SAND, 0.5F, 0F, SoundType.SAND), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_DIRT = registerBlock("frozen_dirt", () -> new BlockReturningState(Material.DIRT, 0.5F, 0.0F, SoundType.GLASS, true, Blocks.DIRT.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_GRASS = registerBlock("frozen_grass", () -> new BlockReturningState(Material.GRASS, 0.6F, 0.0F, SoundType.GLASS, true, Blocks.GRASS_BLOCK.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_STONE = registerBlock("frozen_stone", () -> new BlockReturningState(Material.STONE, 1.5F, 1.0F, SoundType.GLASS, true, Blocks.STONE.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_COBBLESTONE = registerBlock("frozen_cobblestone", () -> new BlockReturningState(Material.STONE, 2F, 2.0F, SoundType.GLASS, true, Blocks.COBBLESTONE.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_GRAVEL = registerBlock("frozen_gravel", () -> new BlockFallingReturningState(Material.DIRT, 0.6F, 0F, SoundType.GLASS, true, Blocks.GRAVEL.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_DIRT_PATH = registerBlock(BlockCharredPath.getNameFromType(EnumDragonType.ICE), () -> new BlockCharredPath(EnumDragonType.ICE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> FROZEN_SPLINTERS = registerBlock("frozen_splinters", () -> new BlockGeneric(Material.WOOD, 2.0F, 1.0F, SoundType.GLASS, true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_ICE = registerBlock("dragon_ice", () -> new BlockGeneric(Material.ICE_SOLID, 0.5F, 0F, SoundType.GLASS, true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_ICE_SPIKES = registerBlock("dragon_ice_spikes", () -> new BlockIceSpikes(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_DIRT = registerBlock("crackled_dirt", () -> new BlockReturningState(Material.DIRT, 0.5F, 0.0F, SoundType.GRAVEL, Blocks.DIRT.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_GRASS = registerBlock("crackled_grass", () -> new BlockReturningState(Material.GRASS, 0.6F, 0.0F, SoundType.GRAVEL, Blocks.GRASS_BLOCK.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_STONE = registerBlock("crackled_stone", () -> new BlockReturningState(Material.STONE, 1.5F, 1.0F, SoundType.STONE, Blocks.STONE.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_COBBLESTONE = registerBlock("crackled_cobblestone", () -> new BlockReturningState(Material.STONE, 2F, 2F, SoundType.STONE, Blocks.COBBLESTONE.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_GRAVEL = registerBlock("crackled_gravel", () -> new BlockFallingReturningState(Material.DIRT, 0.6F, 0F, SoundType.GRAVEL, Blocks.GRAVEL.defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> CRACKLED_DIRT_PATH = registerBlock(BlockCharredPath.getNameFromType(EnumDragonType.LIGHTNING), () -> new BlockCharredPath(EnumDragonType.LIGHTNING), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> NEST = registerBlock("nest", () -> new BlockGeneric(Material.PLANT, 0.5F, 0F, SoundType.GRAVEL, false), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> DRAGON_SCALE_RED = registerBlock("dragonscale_red", () -> new BlockDragonScales(EnumDragonEgg.RED), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_GREEN = registerBlock("dragonscale_green", () -> new BlockDragonScales(EnumDragonEgg.GREEN), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_BRONZE = registerBlock("dragonscale_bronze", () -> new BlockDragonScales(EnumDragonEgg.BRONZE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_GRAY = registerBlock("dragonscale_gray", () -> new BlockDragonScales(EnumDragonEgg.GRAY), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_BLUE = registerBlock("dragonscale_blue", () -> new BlockDragonScales(EnumDragonEgg.BLUE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_WHITE = registerBlock("dragonscale_white", () -> new BlockDragonScales(EnumDragonEgg.WHITE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_SAPPHIRE = registerBlock("dragonscale_sapphire", () -> new BlockDragonScales(EnumDragonEgg.SAPPHIRE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_SILVER = registerBlock("dragonscale_silver", () -> new BlockDragonScales(EnumDragonEgg.SILVER), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_ELECTRIC = registerBlock("dragonscale_electric", () -> new BlockDragonScales(EnumDragonEgg.ELECTRIC), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_AMETHYST = registerBlock("dragonscale_amethyst", () -> new BlockDragonScales(EnumDragonEgg.AMETHYST), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_COPPER = registerBlock("dragonscale_copper", () -> new BlockDragonScales(EnumDragonEgg.COPPER), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_SCALE_BLACK = registerBlock("dragonscale_black", () -> new BlockDragonScales(EnumDragonEgg.BLACK), IceAndFire.TAB_BLOCKS);

    public static final RegistryObject<Block> DRAGON_BONE_BLOCK = registerBlock("dragon_bone_block", () -> new BlockDragonBone(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGON_BONE_BLOCK_WALL = registerBlock("dragon_bone_wall", () -> new BlockDragonBoneWall(BlockBehaviour.Properties.copy(IafBlockRegistry.DRAGON_BONE_BLOCK.get())), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_FIRE_BRICK = registerBlock(BlockDragonforgeBricks.name(EnumDragonType.FIRE), () -> new BlockDragonforgeBricks(EnumDragonType.FIRE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_ICE_BRICK = registerBlock(BlockDragonforgeBricks.name(EnumDragonType.ICE), () -> new BlockDragonforgeBricks(EnumDragonType.ICE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_BRICK = registerBlock(BlockDragonforgeBricks.name(EnumDragonType.LIGHTNING), () -> new BlockDragonforgeBricks(EnumDragonType.LIGHTNING), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_FIRE_INPUT = registerBlock(BlockDragonforgeInput.name(EnumDragonType.FIRE), () -> new BlockDragonforgeInput(EnumDragonType.FIRE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_ICE_INPUT = registerBlock(BlockDragonforgeInput.name(EnumDragonType.ICE), () -> new BlockDragonforgeInput(EnumDragonType.ICE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_INPUT = registerBlock(BlockDragonforgeInput.name(EnumDragonType.LIGHTNING), () -> new BlockDragonforgeInput(EnumDragonType.LIGHTNING), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_FIRE_CORE = registerBlock(BlockDragonforgeCore.name(EnumDragonType.FIRE, true), () -> new BlockDragonforgeCore(EnumDragonType.FIRE, true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_ICE_CORE = registerBlock(BlockDragonforgeCore.name(EnumDragonType.ICE, true), () -> new BlockDragonforgeCore(EnumDragonType.ICE, true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_CORE = registerBlock(BlockDragonforgeCore.name(EnumDragonType.LIGHTNING, true), () -> new BlockDragonforgeCore(EnumDragonType.LIGHTNING, true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_FIRE_CORE_DISABLED = registerBlock(BlockDragonforgeCore.name(EnumDragonType.FIRE, false), () -> new BlockDragonforgeCore(EnumDragonType.FIRE, false), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_ICE_CORE_DISABLED = registerBlock(BlockDragonforgeCore.name(EnumDragonType.ICE, false), () -> new BlockDragonforgeCore(EnumDragonType.ICE, false), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_CORE_DISABLED = registerBlock(BlockDragonforgeCore.name(EnumDragonType.LIGHTNING, false), () -> new BlockDragonforgeCore(EnumDragonType.LIGHTNING, false), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> EGG_IN_ICE = registerBlock("egginice", () -> new BlockEggInIce(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PIXIE_HOUSE_MUSHROOM_RED = registerBlockWithRender(BlockPixieHouse.name("mushroom_red"), () -> new BlockPixieHouse(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PIXIE_HOUSE_MUSHROOM_BROWN = registerBlockWithRender(BlockPixieHouse.name("mushroom_brown"), () -> new BlockPixieHouse(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PIXIE_HOUSE_OAK = registerBlockWithRender(BlockPixieHouse.name("oak"), () -> new BlockPixieHouse(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PIXIE_HOUSE_BIRCH = registerBlockWithRender(BlockPixieHouse.name("birch"), () -> new BlockPixieHouse(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PIXIE_HOUSE_SPRUCE = registerBlockWithRender(BlockPixieHouse.name("spruce"), () -> new BlockPixieHouse(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> PIXIE_HOUSE_DARK_OAK = registerBlockWithRender(BlockPixieHouse.name("dark_oak"), () -> new BlockPixieHouse(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> JAR_EMPTY = registerBlock(BlockJar.name(-1), () -> new BlockJar(-1), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> JAR_PIXIE_0 = registerBlock(BlockJar.name(0), () -> new BlockJar(0), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> JAR_PIXIE_1 = registerBlock(BlockJar.name(1), () -> new BlockJar(1), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> JAR_PIXIE_2 = registerBlock(BlockJar.name(2), () -> new BlockJar(2), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> JAR_PIXIE_3 = registerBlock(BlockJar.name(3), () -> new BlockJar(3), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> JAR_PIXIE_4 = registerBlock(BlockJar.name(4), () -> new BlockJar(4), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_DESERT_RESIN = registerBlock(BlockMyrmexResin.name(false, "desert"), () -> new BlockMyrmexResin(false), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_DESERT_RESIN_STICKY = registerBlock(BlockMyrmexResin.name(true, "desert"), () -> new BlockMyrmexResin(true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN = registerBlock(BlockMyrmexResin.name(false, "jungle"), () -> new BlockMyrmexResin(false), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN_STICKY = registerBlock(BlockMyrmexResin.name(true, "jungle"), () -> new BlockMyrmexResin(true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DESERT_MYRMEX_COCOON = registerBlock("desert_myrmex_cocoon", () -> new BlockMyrmexCocoon(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> JUNGLE_MYRMEX_COCOON = registerBlock("jungle_myrmex_cocoon", () -> new BlockMyrmexCocoon(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_DESERT_BIOLIGHT = registerBlock("myrmex_desert_biolight", () -> new BlockMyrmexBiolight(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_JUNGLE_BIOLIGHT = registerBlock("myrmex_jungle_biolight", () -> new BlockMyrmexBiolight(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_DESERT_RESIN_BLOCK = registerBlock(BlockMyrmexConnectedResin.name(false, false), () -> new BlockMyrmexConnectedResin(false, false), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN_BLOCK = registerBlock(BlockMyrmexConnectedResin.name(true, false), () -> new BlockMyrmexConnectedResin(true, false), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_DESERT_RESIN_GLASS = registerBlock(BlockMyrmexConnectedResin.name(false, true), () -> new BlockMyrmexConnectedResin(false, true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN_GLASS = registerBlock(BlockMyrmexConnectedResin.name(true, true), () -> new BlockMyrmexConnectedResin(true, true), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONSTEEL_FIRE_BLOCK = registerBlock("dragonsteel_fire_block", () -> new BlockGeneric(Material.METAL, 10.0F, 1000.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONSTEEL_ICE_BLOCK = registerBlock("dragonsteel_ice_block", () -> new BlockGeneric(Material.METAL, 10.0F, 1000.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DRAGONSTEEL_LIGHTNING_BLOCK = registerBlock("dragonsteel_lightning_block", () -> new BlockGeneric(Material.METAL, 10.0F, 1000.0F, SoundType.METAL), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<BlockDreadBase> DREAD_STONE = registerBlock("dread_stone", () -> new BlockDreadBase(Material.STONE, -1.0F, 100000.0F, SoundType.STONE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS = registerBlock("dread_stone_bricks", () -> new BlockDreadBase(Material.STONE, -1.0F, 100000.0F, SoundType.STONE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS_CHISELED = registerBlock("dread_stone_bricks_chiseled", () -> new BlockDreadBase(Material.STONE, -1.0F, 100000.0F, SoundType.STONE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS_CRACKED = registerBlock("dread_stone_bricks_cracked", () -> new BlockDreadBase(Material.STONE, -1.0F, 100000.0F, SoundType.STONE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS_MOSSY = registerBlock("dread_stone_bricks_mossy", () -> new BlockDreadBase(Material.STONE, -1.0F, 100000.0F, SoundType.STONE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<BlockDreadBase> DREAD_STONE_TILE = registerBlock("dread_stone_tile", () -> new BlockDreadBase(Material.STONE, -1.0F, 100000.0F, SoundType.STONE), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREAD_STONE_FACE = registerBlock("dread_stone_face", () -> new BlockDreadStoneFace(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREAD_TORCH = registerStandingAndWallBlock("dread_torch", () -> new BlockDreadTorch(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREAD_TORCH_WALL = BLOCKS.register("dread_torch_wall", () -> new BlockDreadTorchWall());
    public static final RegistryObject<Block> DREAD_STONE_BRICKS_STAIRS = registerBlock("dread_stone_stairs", () -> new BlockGenericStairs(DREAD_STONE_BRICKS.get().defaultBlockState()), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREAD_STONE_BRICKS_SLAB = registerBlock("dread_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(10F, 10000F)), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREADWOOD_LOG = registerBlock("dreadwood_log", () -> new BlockDreadWoodLog(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<BlockDreadBase> DREADWOOD_PLANKS = registerBlock("dreadwood_planks", () -> new BlockDreadBase(Material.WOOD, -1.0F, 100000.0F, SoundType.WOOD), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREADWOOD_PLANKS_LOCK = registerBlock("dreadwood_planks_lock", () -> new BlockDreadWoodLock(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREAD_PORTAL = registerBlockWithRender("dread_portal", () -> new BlockDreadPortal(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> DREAD_SPAWNER = registerBlock("dread_spawner", () -> new BlockDreadSpawner(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> BURNT_TORCH = registerStandingAndWallBlock("burnt_torch", () -> new BlockBurntTorch(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> BURNT_TORCH_WALL = BLOCKS.register("burnt_torch_wall", () -> new BlockBurntTorchWall());
    public static final RegistryObject<Block> GHOST_CHEST = registerBlockWithRender("ghost_chest", () -> new BlockGhostChest(), IceAndFire.TAB_BLOCKS);
    public static final RegistryObject<Block> GRAVEYARD_SOIL = registerBlock("graveyard_soil", () -> new BlockGraveyardSoil(), IceAndFire.TAB_BLOCKS);


    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<T> registerStandingAndWallBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerStandingAndWallBlockItem(name, toReturn, tab);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<T> registerBlockWithRender(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItemWithRender(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return IafItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<Item> registerStandingAndWallBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return IafItemRegistry.ITEMS.register(name, () -> new StandingAndWallBlockItem(block.get(), ((IWallBlock) block.get()).wallBlock(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItemWithRender(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return IafItemRegistry.ITEMS.register(name, () -> new BlockItemWithRender(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}