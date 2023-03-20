package com.github.alexthe666.iceandfire.misc;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class IafBannerRegistry {
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registry.BANNER_PATTERN_REGISTRY, IceAndFire.MODID);

    public static final RegistryObject<BannerPattern> PATTERN_FIRE = BANNER_PATTERNS.register("fire", () -> new BannerPattern("fire"));
    public static final RegistryObject<BannerPattern> PATTERN_ICE = BANNER_PATTERNS.register("ice", () -> new BannerPattern("ice"));
    public static final RegistryObject<BannerPattern> PATTERN_LIGHTNING = BANNER_PATTERNS.register("lightning", () -> new BannerPattern("lightning"));
    public static final RegistryObject<BannerPattern> PATTERN_FIRE_HEAD = BANNER_PATTERNS.register("fire_head", () -> new BannerPattern("fire_head"));
    public static final RegistryObject<BannerPattern> PATTERN_ICE_HEAD = BANNER_PATTERNS.register("ice_head", () -> new BannerPattern("ice_head"));
    public static final RegistryObject<BannerPattern> PATTERN_LIGHTNING_HEAD = BANNER_PATTERNS.register("lightning_head", () -> new BannerPattern("lightning_head"));
    public static final RegistryObject<BannerPattern> PATTERN_AMPHITHERE = BANNER_PATTERNS.register("amphithere", () -> new BannerPattern("amphithere"));
    public static final RegistryObject<BannerPattern> PATTERN_BIRD = BANNER_PATTERNS.register("bird", () -> new BannerPattern("bird"));
    public static final RegistryObject<BannerPattern> PATTERN_EYE = BANNER_PATTERNS.register("eye", () -> new BannerPattern("eye"));
    public static final RegistryObject<BannerPattern> PATTERN_FAE = BANNER_PATTERNS.register("fae", () -> new BannerPattern("fae"));
    public static final RegistryObject<BannerPattern> PATTERN_FEATHER = BANNER_PATTERNS.register("feather", () -> new BannerPattern("feather"));
    public static final RegistryObject<BannerPattern> PATTERN_GORGON = BANNER_PATTERNS.register("gorgon", () -> new BannerPattern("gorgon"));
    public static final RegistryObject<BannerPattern> PATTERN_HIPPOCAMPUS = BANNER_PATTERNS.register("hippocampus", () -> new BannerPattern("hippocampus"));
    public static final RegistryObject<BannerPattern> PATTERN_HIPPOGRYPH_HEAD = BANNER_PATTERNS.register("hippogryph_head", () -> new BannerPattern("hippogryph_head"));
    public static final RegistryObject<BannerPattern> PATTERN_MERMAID = BANNER_PATTERNS.register("mermaid", () -> new BannerPattern("mermaid"));
    public static final RegistryObject<BannerPattern> PATTERN_SEA_SERPENT = BANNER_PATTERNS.register("sea_serpent", () -> new BannerPattern("sea_serpent"));
    public static final RegistryObject<BannerPattern> PATTERN_TROLL = BANNER_PATTERNS.register("troll", () -> new BannerPattern("troll"));
    public static final RegistryObject<BannerPattern> PATTERN_WEEZER = BANNER_PATTERNS.register("weezer", () -> new BannerPattern("weezer"));
    public static final RegistryObject<BannerPattern> PATTERN_DREAD = BANNER_PATTERNS.register("dread", () -> new BannerPattern("dread"));

    public static void register(IEventBus eventBus) {
        BANNER_PATTERNS.register(eventBus);
    }
}
