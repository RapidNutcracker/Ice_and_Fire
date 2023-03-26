 package com.github.alexthe666.iceandfire.world.structure;

 import com.github.alexthe666.iceandfire.IceAndFire;
 import net.minecraft.data.BuiltinRegistries;
 import net.minecraft.resources.ResourceKey;
 import net.minecraft.resources.ResourceLocation;
 import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
 import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
 import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

 public class Pool {
     static final ResourceKey<StructureTemplatePool> dread_pool = ResourceKey.create(BuiltinRegistries.TEMPLATE_POOL.key(), new ResourceLocation(IceAndFire.MODID, "dread_mausoleum/start_pool"));
     static final ResourceKey<StructureTemplatePool> graveyard_pool = ResourceKey.create(BuiltinRegistries.TEMPLATE_POOL.key(), new ResourceLocation(IceAndFire.MODID, "graveyard/top_pool"));
     static final ResourceKey<StructureTemplatePool> gorgon_pool = ResourceKey.create(BuiltinRegistries.TEMPLATE_POOL.key(), new ResourceLocation(IceAndFire.MODID, "gorgon_temple/top_pool"));

     public static PieceGeneratorSupplier.Context<FeatureConfiguration> replaceContext(PieceGeneratorSupplier.Context<FeatureConfiguration> context, FeatureConfiguration configuration) {
         return new PieceGeneratorSupplier.Context<>(
                 context.chunkGenerator(),
                 context.biomeSource(),
                 context.randomState(),
                 context.seed(),
                 context.chunkPos(),
                 configuration,
                 context.heightAccessor(),
                 context.validBiome(),
                 context.structureTemplateManager(),
                 context.registryAccess()
         );
     }
 }
