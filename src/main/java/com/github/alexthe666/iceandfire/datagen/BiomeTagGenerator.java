package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BiomeTagGenerator extends BiomeTagsProvider {
    public static final TagKey<Biome> VALID_FIRE_DRAGON_ROOST_BIOMES = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IceAndFire.MODID, "valid_fire_dragon_roost_biomes"));
    public static final TagKey<Biome> VALID_ICE_DRAGON_ROOST_BIOMES = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IceAndFire.MODID, "valid_ice_dragon_roost_biomes"));
    public static final TagKey<Biome> VALID_LIGHTNING_DRAGON_ROOST_BIOMES = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IceAndFire.MODID, "valid_lightning_dragon_roost_biomes"));

    public BiomeTagGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, IceAndFire.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(VALID_FIRE_DRAGON_ROOST_BIOMES).addTag(
                Tags.Biomes.IS_DRY
        );

        tag(VALID_ICE_DRAGON_ROOST_BIOMES).addTag(
                Tags.Biomes.IS_SNOWY
        );

        tag(VALID_LIGHTNING_DRAGON_ROOST_BIOMES).addTag(
                Tags.Biomes.IS_WET
        );
    }
}
