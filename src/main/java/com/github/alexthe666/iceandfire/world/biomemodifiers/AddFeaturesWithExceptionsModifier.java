package com.github.alexthe666.iceandfire.world.biomemodifiers;

import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record AddFeaturesWithExceptionsModifier(HolderSet<Biome> biomes, HolderSet<Biome> exceptions, Holder<PlacedFeature> feature, GenerationStep.Decoration step) implements BiomeModifier {

    public static Codec<AddFeaturesWithExceptionsModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddFeaturesWithExceptionsModifier::biomes),
            Biome.LIST_CODEC.fieldOf("exceptions").forGetter(AddFeaturesWithExceptionsModifier::exceptions),
            PlacedFeature.CODEC.fieldOf("feature").forGetter(AddFeaturesWithExceptionsModifier::feature),
            GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesWithExceptionsModifier::step)
        ).apply(builder, AddFeaturesWithExceptionsModifier::new));

    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        // add a feature to all specified biomes
        if (phase == Phase.ADD && biomes.contains(biome) && !exceptions.contains(biome)){
            builder.getGenerationSettings().getFeatures(step).add(feature);
        }

        if (phase == Phase.REMOVE && exceptions.contains(biome)){
            builder.getGenerationSettings().getFeatures(step).remove(feature);
        }
    }

    public Codec<? extends BiomeModifier> codec() {
        return IafWorldRegistry.ADD_FEATURES_WITH_EXCEPTIONS_MODIFIER.get();
    }
}