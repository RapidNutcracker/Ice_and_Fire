package com.github.alexthe666.iceandfire.misc;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;

import static com.github.alexthe666.iceandfire.IceAndFire.MODID;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = IceAndFire.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class IafSoundRegistry {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, IceAndFire.MODID);

    public static final RegistryObject<SoundEvent> BESTIARY_PAGE = SOUND_EVENTS.register("bestiary_page", () -> createSoundEvent("bestiary_page"));

    public static final RegistryObject<SoundEvent> EGG_HATCH = SOUND_EVENTS.register("egg_hatch", () -> createSoundEvent("egg_hatch"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_BREATH = SOUND_EVENTS.register("firedragon_breath", () -> createSoundEvent("firedragon_breath"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_BREATH = SOUND_EVENTS.register("icedragon_breath", () -> createSoundEvent("icedragon_breath"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_IDLE = SOUND_EVENTS.register("firedragon_child_idle", () -> createSoundEvent("firedragon_child_idle"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_HURT = SOUND_EVENTS.register("firedragon_child_hurt", () -> createSoundEvent("firedragon_child_hurt"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_DEATH = SOUND_EVENTS.register("firedragon_child_death", () -> createSoundEvent("firedragon_child_death"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_ROAR = SOUND_EVENTS.register("firedragon_child_roar", () -> createSoundEvent("firedragon_child_roar"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_ROAR = SOUND_EVENTS.register("firedragon_teen_roar", () -> createSoundEvent("firedragon_teen_roar"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_IDLE = SOUND_EVENTS.register("firedragon_teen_idle", () -> createSoundEvent("firedragon_teen_idle"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_DEATH = SOUND_EVENTS.register("firedragon_teen_death", () -> createSoundEvent("firedragon_teen_death"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_HURT = SOUND_EVENTS.register("firedragon_teen_hurt", () -> createSoundEvent("firedragon_teen_hurt"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_ROAR = SOUND_EVENTS.register("firedragon_adult_roar", () -> createSoundEvent("firedragon_adult_roar"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_IDLE = SOUND_EVENTS.register("firedragon_adult_idle", () -> createSoundEvent("firedragon_adult_idle"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_DEATH = SOUND_EVENTS.register("firedragon_adult_death", () -> createSoundEvent("firedragon_adult_death"));

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_HURT = SOUND_EVENTS.register("firedragon_adult_hurt", () -> createSoundEvent("firedragon_adult_hurt"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_IDLE = SOUND_EVENTS.register("icedragon_child_idle", () -> createSoundEvent("icedragon_child_idle"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_HURT = SOUND_EVENTS.register("icedragon_child_hurt", () -> createSoundEvent("icedragon_child_hurt"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_DEATH = SOUND_EVENTS.register("icedragon_child_death", () -> createSoundEvent("icedragon_child_death"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_ROAR = SOUND_EVENTS.register("icedragon_child_roar", () -> createSoundEvent("icedragon_child_roar"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_ROAR = SOUND_EVENTS.register("icedragon_teen_roar", () -> createSoundEvent("icedragon_teen_roar"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_IDLE = SOUND_EVENTS.register("icedragon_teen_idle", () -> createSoundEvent("icedragon_teen_idle"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_DEATH = SOUND_EVENTS.register("icedragon_teen_death", () -> createSoundEvent("icedragon_teen_death"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_HURT = SOUND_EVENTS.register("icedragon_teen_hurt", () -> createSoundEvent("icedragon_teen_hurt"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_ROAR = SOUND_EVENTS.register("icedragon_adult_roar", () -> createSoundEvent("icedragon_adult_roar"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_IDLE = SOUND_EVENTS.register("icedragon_adult_idle", () -> createSoundEvent("icedragon_adult_idle"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_DEATH = SOUND_EVENTS.register("icedragon_adult_death", () -> createSoundEvent("icedragon_adult_death"));

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_HURT = SOUND_EVENTS.register("icedragon_adult_hurt", () -> createSoundEvent("icedragon_adult_hurt"));

    public static final RegistryObject<SoundEvent> DRAGONFLUTE = SOUND_EVENTS.register("dragonflute", () -> createSoundEvent("dragonflute"));

    public static final RegistryObject<SoundEvent> HIPPOGRYPH_IDLE = SOUND_EVENTS.register("hippogryph_idle", () -> createSoundEvent("hippogryph_idle"));

    public static final RegistryObject<SoundEvent> HIPPOGRYPH_HURT = SOUND_EVENTS.register("hippogryph_hurt", () -> createSoundEvent("hippogryph_hurt"));

    public static final RegistryObject<SoundEvent> HIPPOGRYPH_DIE = SOUND_EVENTS.register("hippogryph_die", () -> createSoundEvent("hippogryph_die"));

    public static final RegistryObject<SoundEvent> GORGON_IDLE = SOUND_EVENTS.register("gorgon_idle", () -> createSoundEvent("gorgon_idle"));

    public static final RegistryObject<SoundEvent> GORGON_HURT = SOUND_EVENTS.register("gorgon_hurt", () -> createSoundEvent("gorgon_hurt"));

    public static final RegistryObject<SoundEvent> GORGON_DIE = SOUND_EVENTS.register("gorgon_die", () -> createSoundEvent("gorgon_die"));

    public static final RegistryObject<SoundEvent> GORGON_ATTACK = SOUND_EVENTS.register("gorgon_attack", () -> createSoundEvent("gorgon_attack"));

    public static final RegistryObject<SoundEvent> GORGON_PETRIFY = SOUND_EVENTS.register("gorgon_petrify", () -> createSoundEvent("gorgon_petrify"));

    public static final RegistryObject<SoundEvent> TURN_STONE = SOUND_EVENTS.register("turn_stone", () -> createSoundEvent("turn_stone"));

    public static final RegistryObject<SoundEvent> PIXIE_IDLE = SOUND_EVENTS.register("pixie_idle", () -> createSoundEvent("pixie_idle"));

    public static final RegistryObject<SoundEvent> PIXIE_HURT = SOUND_EVENTS.register("pixie_hurt", () -> createSoundEvent("pixie_hurt"));

    public static final RegistryObject<SoundEvent> PIXIE_DIE = SOUND_EVENTS.register("pixie_die", () -> createSoundEvent("pixie_die"));

    public static final RegistryObject<SoundEvent> PIXIE_TAUNT = SOUND_EVENTS.register("pixie_taunt", () -> createSoundEvent("pixie_taunt"));

    public static final RegistryObject<SoundEvent> GOLD_PILE_STEP = SOUND_EVENTS.register("gold_pile_step", () -> createSoundEvent("gold_pile_step"));

    public static final RegistryObject<SoundEvent> GOLD_PILE_BREAK = SOUND_EVENTS.register("gold_pile_break", () -> createSoundEvent("gold_pile_break"));

    public static final RegistryObject<SoundEvent> DRAGON_FLIGHT = SOUND_EVENTS.register("dragon_flight", () -> createSoundEvent("dragon_flight"));

    public static final RegistryObject<SoundEvent> CYCLOPS_IDLE = SOUND_EVENTS.register("cyclops_idle", () -> createSoundEvent("cyclops_idle"));

    public static final RegistryObject<SoundEvent> CYCLOPS_HURT = SOUND_EVENTS.register("cyclops_hurt", () -> createSoundEvent("cyclops_hurt"));

    public static final RegistryObject<SoundEvent> CYCLOPS_DIE = SOUND_EVENTS.register("cyclops_die", () -> createSoundEvent("cyclops_die"));

    public static final RegistryObject<SoundEvent> CYCLOPS_BITE = SOUND_EVENTS.register("cyclops_bite", () -> createSoundEvent("cyclops_bite"));

    public static final RegistryObject<SoundEvent> CYCLOPS_BLINDED = SOUND_EVENTS.register("cyclops_blinded", () -> createSoundEvent("cyclops_blinded"));

    public static final RegistryObject<SoundEvent> HIPPOCAMPUS_IDLE = SOUND_EVENTS.register("hippocampus_idle", () -> createSoundEvent("hippocampus_idle"));

    public static final RegistryObject<SoundEvent> HIPPOCAMPUS_HURT = SOUND_EVENTS.register("hippocampus_hurt", () -> createSoundEvent("hippocampus_hurt"));

    public static final RegistryObject<SoundEvent> HIPPOCAMPUS_DIE = SOUND_EVENTS.register("hippocampus_die", () -> createSoundEvent("hippocampus_die"));

    public static final RegistryObject<SoundEvent> DEATHWORM_IDLE = SOUND_EVENTS.register("deathworm_idle", () -> createSoundEvent("deathworm_idle"));

    public static final RegistryObject<SoundEvent> DEATHWORM_ATTACK = SOUND_EVENTS.register("deathworm_attack", () -> createSoundEvent("deathworm_attack"));

    public static final RegistryObject<SoundEvent> DEATHWORM_HURT = SOUND_EVENTS.register("deathworm_hurt", () -> createSoundEvent("deathworm_hurt"));

    public static final RegistryObject<SoundEvent> DEATHWORM_DIE = SOUND_EVENTS.register("deathworm_die", () -> createSoundEvent("deathworm_die"));

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_IDLE = SOUND_EVENTS.register("deathworm_giant_idle", () -> createSoundEvent("deathworm_giant_idle"));

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_ATTACK = SOUND_EVENTS.register("deathworm_giant_attack", () -> createSoundEvent("deathworm_giant_attack"));

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_HURT = SOUND_EVENTS.register("deathworm_giant_hurt", () -> createSoundEvent("deathworm_giant_hurt"));

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_DIE = SOUND_EVENTS.register("deathworm_giant_die", () -> createSoundEvent("deathworm_giant_die"));

    public static final RegistryObject<SoundEvent> NAGA_IDLE = SOUND_EVENTS.register("naga_idle", () -> createSoundEvent("naga_idle"));

    public static final RegistryObject<SoundEvent> NAGA_ATTACK = SOUND_EVENTS.register("naga_attack", () -> createSoundEvent("naga_attack"));

    public static final RegistryObject<SoundEvent> NAGA_HURT = SOUND_EVENTS.register("naga_hurt", () -> createSoundEvent("naga_hurt"));

    public static final RegistryObject<SoundEvent> NAGA_DIE = SOUND_EVENTS.register("naga_die", () -> createSoundEvent("naga_die"));

    public static final RegistryObject<SoundEvent> MERMAID_IDLE = SOUND_EVENTS.register("mermaid_idle", () -> createSoundEvent("mermaid_idle"));

    public static final RegistryObject<SoundEvent> MERMAID_HURT = SOUND_EVENTS.register("mermaid_hurt", () -> createSoundEvent("mermaid_hurt"));

    public static final RegistryObject<SoundEvent> MERMAID_DIE = SOUND_EVENTS.register("mermaid_die", () -> createSoundEvent("mermaid_die"));

    public static final RegistryObject<SoundEvent> SIREN_SONG = SOUND_EVENTS.register("siren_song", () -> createSoundEvent("siren_song"));

    public static final RegistryObject<SoundEvent> TROLL_DIE = SOUND_EVENTS.register("troll_die", () -> createSoundEvent("troll_die"));

    public static final RegistryObject<SoundEvent> TROLL_IDLE = SOUND_EVENTS.register("troll_idle", () -> createSoundEvent("troll_idle"));

    public static final RegistryObject<SoundEvent> TROLL_HURT = SOUND_EVENTS.register("troll_hurt", () -> createSoundEvent("troll_hurt"));

    public static final RegistryObject<SoundEvent> TROLL_ROAR = SOUND_EVENTS.register("troll_roar", () -> createSoundEvent("troll_roar"));

    public static final RegistryObject<SoundEvent> COCKATRICE_DIE = SOUND_EVENTS.register("cockatrice_die", () -> createSoundEvent("cockatrice_die"));

    public static final RegistryObject<SoundEvent> COCKATRICE_IDLE = SOUND_EVENTS.register("cockatrice_idle", () -> createSoundEvent("cockatrice_idle"));

    public static final RegistryObject<SoundEvent> COCKATRICE_HURT = SOUND_EVENTS.register("cockatrice_hurt", () -> createSoundEvent("cockatrice_hurt"));

    public static final RegistryObject<SoundEvent> COCKATRICE_CRY = SOUND_EVENTS.register("cockatrice_cry", () -> createSoundEvent("cockatrice_cry"));

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_DIE = SOUND_EVENTS.register("stymphalian_bird_die", () -> createSoundEvent("stymphalian_bird_die"));

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_IDLE = SOUND_EVENTS.register("stymphalian_bird_idle", () -> createSoundEvent("stymphalian_bird_idle"));

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_HURT = SOUND_EVENTS.register("stymphalian_bird_hurt", () -> createSoundEvent("stymphalian_bird_hurt"));

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_ATTACK = SOUND_EVENTS.register("stymphalian_bird_attack", () -> createSoundEvent("stymphalian_bird_attack"));

    public static final RegistryObject<SoundEvent> MYRMEX_DIE = SOUND_EVENTS.register("myrmex_die", () -> createSoundEvent("myrmex_die"));

    public static final RegistryObject<SoundEvent> MYRMEX_IDLE = SOUND_EVENTS.register("myrmex_idle", () -> createSoundEvent("myrmex_idle"));

    public static final RegistryObject<SoundEvent> MYRMEX_HURT = SOUND_EVENTS.register("myrmex_hurt", () -> createSoundEvent("myrmex_hurt"));

    public static final RegistryObject<SoundEvent> MYRMEX_WALK = SOUND_EVENTS.register("myrmex_walk", () -> createSoundEvent("myrmex_walk"));

    public static final RegistryObject<SoundEvent> MYRMEX_BITE = SOUND_EVENTS.register("myrmex_bite", () -> createSoundEvent("myrmex_bite"));

    public static final RegistryObject<SoundEvent> MYRMEX_STING = SOUND_EVENTS.register("myrmex_sting", () -> createSoundEvent("myrmex_sting"));

    public static final RegistryObject<SoundEvent> AMPHITHERE_DIE = SOUND_EVENTS.register("amphithere_die", () -> createSoundEvent("amphithere_die"));

    public static final RegistryObject<SoundEvent> AMPHITHERE_IDLE = SOUND_EVENTS.register("amphithere_idle", () -> createSoundEvent("amphithere_idle"));

    public static final RegistryObject<SoundEvent> AMPHITHERE_HURT = SOUND_EVENTS.register("amphithere_hurt", () -> createSoundEvent("amphithere_hurt"));

    public static final RegistryObject<SoundEvent> AMPHITHERE_BITE = SOUND_EVENTS.register("amphithere_bite", () -> createSoundEvent("amphithere_bite"));

    public static final RegistryObject<SoundEvent> AMPHITHERE_GUST = SOUND_EVENTS.register("amphithere_gust", () -> createSoundEvent("amphithere_gust"));

    public static final RegistryObject<SoundEvent> SEA_SERPENT_DIE = SOUND_EVENTS.register("sea_serpent_die", () -> createSoundEvent("sea_serpent_die"));

    public static final RegistryObject<SoundEvent> SEA_SERPENT_IDLE = SOUND_EVENTS.register("sea_serpent_idle", () -> createSoundEvent("sea_serpent_idle"));

    public static final RegistryObject<SoundEvent> SEA_SERPENT_HURT = SOUND_EVENTS.register("sea_serpent_hurt", () -> createSoundEvent("sea_serpent_hurt"));

    public static final RegistryObject<SoundEvent> SEA_SERPENT_BITE = SOUND_EVENTS.register("sea_serpent_bite", () -> createSoundEvent("sea_serpent_bite"));

    public static final RegistryObject<SoundEvent> SEA_SERPENT_ROAR = SOUND_EVENTS.register("sea_serpent_roar", () -> createSoundEvent("sea_serpent_roar"));

    public static final RegistryObject<SoundEvent> SEA_SERPENT_BREATH = SOUND_EVENTS.register("sea_serpent_breath", () -> createSoundEvent("sea_serpent_breath"));

    public static final RegistryObject<SoundEvent> SEA_SERPENT_SPLASH = SOUND_EVENTS.register("sea_serpent_splash", () -> createSoundEvent("sea_serpent_splash"));

    public static final RegistryObject<SoundEvent> HYDRA_DIE = SOUND_EVENTS.register("hydra_die", () -> createSoundEvent("hydra_die"));

    public static final RegistryObject<SoundEvent> HYDRA_IDLE = SOUND_EVENTS.register("hydra_idle", () -> createSoundEvent("hydra_idle"));

    public static final RegistryObject<SoundEvent> HYDRA_HURT = SOUND_EVENTS.register("hydra_hurt", () -> createSoundEvent("hydra_hurt"));

    public static final RegistryObject<SoundEvent> HYDRA_SPIT = SOUND_EVENTS.register("hydra_spit", () -> createSoundEvent("hydra_spit"));

    public static final RegistryObject<SoundEvent> HYDRA_REGEN_HEAD = SOUND_EVENTS.register("hydra_regen_head", () -> createSoundEvent("hydra_regen_head"));

    public static final RegistryObject<SoundEvent> PIXIE_WAND = SOUND_EVENTS.register("pixie_wand", () -> createSoundEvent("pixie_wand"));

    public static final RegistryObject<SoundEvent> DREAD_LICH_SUMMON = SOUND_EVENTS.register("dread_lich_summon", () -> createSoundEvent("dread_lich_summon"));

    public static final RegistryObject<SoundEvent> DREAD_GHOUL_IDLE = SOUND_EVENTS.register("dread_ghoul_idle", () -> createSoundEvent("dread_ghoul_idle"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_IDLE = SOUND_EVENTS.register("lightningdragon_child_idle", () -> createSoundEvent("lightningdragon_child_idle"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_HURT = SOUND_EVENTS.register("lightningdragon_child_hurt", () -> createSoundEvent("lightningdragon_child_hurt"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_DEATH = SOUND_EVENTS.register("lightningdragon_child_death", () -> createSoundEvent("lightningdragon_child_death"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_ROAR = SOUND_EVENTS.register("lightningdragon_child_roar", () -> createSoundEvent("lightningdragon_child_roar"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_ROAR = SOUND_EVENTS.register("lightningdragon_teen_roar", () -> createSoundEvent("lightningdragon_teen_roar"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_IDLE = SOUND_EVENTS.register("lightningdragon_teen_idle", () -> createSoundEvent("lightningdragon_teen_idle"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_DEATH = SOUND_EVENTS.register("lightningdragon_teen_death", () -> createSoundEvent("lightningdragon_teen_death"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_HURT = SOUND_EVENTS.register("lightningdragon_teen_hurt", () -> createSoundEvent("lightningdragon_teen_hurt"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_ROAR = SOUND_EVENTS.register("lightningdragon_adult_roar", () -> createSoundEvent("lightningdragon_adult_roar"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_IDLE = SOUND_EVENTS.register("lightningdragon_adult_idle", () -> createSoundEvent("lightningdragon_adult_idle"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_DEATH = SOUND_EVENTS.register("lightningdragon_adult_death", () -> createSoundEvent("lightningdragon_adult_death"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_HURT = SOUND_EVENTS.register("lightningdragon_adult_hurt", () -> createSoundEvent("lightningdragon_adult_hurt"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_BREATH = SOUND_EVENTS.register("lightningdragon_breath", () -> createSoundEvent("lightningdragon_breath"));

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_BREATH_CRACKLE = SOUND_EVENTS.register("lightningdragon_breath_crackle", () -> createSoundEvent("lightningdragon_breath_crackle"));

    public static final RegistryObject<SoundEvent> GHOST_IDLE = SOUND_EVENTS.register("ghost_idle", () -> createSoundEvent("ghost_idle"));

    public static final RegistryObject<SoundEvent> GHOST_HURT = SOUND_EVENTS.register("ghost_hurt", () -> createSoundEvent("ghost_hurt"));

    public static final RegistryObject<SoundEvent> GHOST_DIE = SOUND_EVENTS.register("ghost_die", () -> createSoundEvent("ghost_die"));

    public static final RegistryObject<SoundEvent> GHOST_ATTACK = SOUND_EVENTS.register("ghost_attack", () -> createSoundEvent("ghost_attack"));

    public static final RegistryObject<SoundEvent> GHOST_JUMPSCARE = SOUND_EVENTS.register("ghost_jumpscare", () -> createSoundEvent("ghost_jumpscare"));

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(MODID, soundName);
        return new SoundEvent(soundID);
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
