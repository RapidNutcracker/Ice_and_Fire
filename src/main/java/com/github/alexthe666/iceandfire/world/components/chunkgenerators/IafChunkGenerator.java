package com.github.alexthe666.iceandfire.world.components.chunkgenerators;

import com.github.alexthe666.iceandfire.IafLandmark;
import com.github.alexthe666.iceandfire.util.LegacyLandmarkPlacements;
import com.github.alexthe666.iceandfire.util.Vec2i;
import com.github.alexthe666.iceandfire.world.structure.placements.BiomeForcedLandmarkPlacement;
import com.github.alexthe666.iceandfire.world.structure.start.DragonLandmark;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

// TODO override getBaseHeight and getBaseColumn for our advanced structure terraforming
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "SuspiciousNameCombination", "deprecation"})
public class IafChunkGenerator extends ChunkGeneratorWrapper {
	public static final Codec<IafChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ChunkGenerator.CODEC.fieldOf("wrapped_generator").forGetter(o -> o.delegate),
			RegistryOps.retrieveRegistry(Registry.STRUCTURE_SET_REGISTRY).forGetter(o -> o.structureSets),
			RegistryCodecs.homogeneousList(Registry.STRUCTURE_SET_REGISTRY).fieldOf("structures_placements").forGetter(o -> o.structureOverrides),
			NoiseGeneratorSettings.CODEC.fieldOf("noise_generation_settings").forGetter(o -> o.noiseGeneratorSettings),
			Codec.BOOL.fieldOf("generate_dark_forest_canopy").forGetter(o -> o.genDarkForestCanopy),
			Codec.INT.optionalFieldOf("dark_forest_canopy_height").forGetter(o -> o.darkForestCanopyHeight),
			Codec.unboundedMap(ResourceKey.codec(Registry.BIOME_REGISTRY), IafLandmark.CODEC.listOf().xmap(ImmutableSet::copyOf, ImmutableList::copyOf)).fieldOf("landmark_placement_allowed_biomes").forGetter(o -> o.biomeLandmarkOverrides)
	).apply(instance, IafChunkGenerator::new));

	private final Map<ResourceKey<Biome>, ImmutableSet<IafLandmark>> biomeLandmarkOverrides;

	private final Holder<NoiseGeneratorSettings> noiseGeneratorSettings;
	private final boolean genDarkForestCanopy;
	private final Optional<Integer> darkForestCanopyHeight;

	private final BlockState defaultBlock;
	private final BlockState defaultFluid;
	private final Optional<Climate.Sampler> surfaceNoiseGetter;

	private final HolderSet<StructureSet> structureOverrides;

	private static final BlockState[] EMPTY_COLUMN = new BlockState[0];

	public IafChunkGenerator(ChunkGenerator delegate, Registry<StructureSet> structures, HolderSet<StructureSet> structureOverrides, Holder<NoiseGeneratorSettings> noiseGenSettings, boolean genDarkForestCanopy, Optional<Integer> darkForestCanopyHeight, Map<ResourceKey<Biome>, ImmutableSet<IafLandmark>> biomeLandmarkOverrides) {
		super(structures, Optional.of(structureOverrides), delegate);
		this.structureOverrides = structureOverrides;

		this.biomeLandmarkOverrides = biomeLandmarkOverrides;
		this.noiseGeneratorSettings = noiseGenSettings;
		this.genDarkForestCanopy = genDarkForestCanopy;
		this.darkForestCanopyHeight = darkForestCanopyHeight;

		if (delegate instanceof NoiseBasedChunkGenerator noiseGen) {
			this.defaultBlock = noiseGenSettings.value().defaultBlock();
			this.defaultFluid = noiseGenSettings.value().defaultFluid();
			this.surfaceNoiseGetter = Optional.empty(); //Optional.of(noiseGen.sampler);
		} else {
			this.defaultBlock = Blocks.STONE.defaultBlockState();
			this.defaultFluid = Blocks.WATER.defaultBlockState();
			this.surfaceNoiseGetter = Optional.empty();
		}

		//BIOME_FEATURES = new ImmutableMap.Builder<ResourceLocation, IafLandmark>()
		//		.put(BiomeKeys.DARK_FOREST.location(), IafLandmark.KNIGHT_STRONGHOLD)
		//		.put(BiomeKeys.DARK_FOREST_CENTER.location(), IafLandmark.DARK_TOWER)
		//		//.put(BiomeKeys.DENSE_MUSHROOM_FOREST.location(), MUSHROOM_TOWER)
		//		.put(BiomeKeys.ENCHANTED_FOREST.location(), IafLandmark.QUEST_GROVE)
		//		.put(BiomeKeys.FINAL_PLATEAU.location(), IafLandmark.FINAL_CASTLE)
		//		.put(BiomeKeys.FIRE_SWAMP.location(), IafLandmark.HYDRA_LAIR)
		//		.put(BiomeKeys.GLACIER.location(), IafLandmark.ICE_TOWER)
		//		.put(BiomeKeys.HIGHLANDS.location(), IafLandmark.TROLL_CAVE)
		//		.put(BiomeKeys.SNOWY_FOREST.location(), IafLandmark.YETI_CAVE)
		//		.put(BiomeKeys.SWAMP.location(), IafLandmark.LABYRINTH)
		//		.put(BiomeKeys.LAKE.location(), IafLandmark.QUEST_ISLAND)
		//		.build();
	}

	@Override
	protected Codec<? extends ChunkGenerator> codec() {
		return CODEC;
	}


	@Override
	public CompletableFuture<ChunkAccess> createBiomes(Registry<Biome> biomes, Executor executor, RandomState random, Blender blender, StructureManager manager, ChunkAccess chunkAccess) {
		//Mimic behaviour of ChunkGenerator, NoiseBasedChunkGenerator does weird things
		return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
			chunkAccess.fillBiomesFromNoise(this.getBiomeSource(), Climate.empty());
			return chunkAccess;
		}), Util.backgroundExecutor());
	}

//	private double[] makeAndFillNoiseColumn(RandomState state, int x, int z, int min, int max) {
//		double[] columns = new double[max + 1];
//		this.fillNoiseColumn(state, columns, x, z, min, max);
//		return columns;
//	}
//
//	private void fillNoiseColumn(RandomState state, double[] columns, int x, int z, int min, int max) {
//		this.warper.get().fillNoiseColumn(state, columns, x, z, this.getSeaLevel(), min, max);
//	}

	//Logic based on 1.16. Will only ever get the default Block, Fluid, or Air
	private BlockState generateBaseState(double noiseVal, double level) {
		BlockState state;

		if (noiseVal > 0.0D) {
			state = this.defaultBlock;
		} else if (level < this.getSeaLevel()) {
			state = this.defaultFluid;
		} else {
			state = Blocks.AIR.defaultBlockState();
		}

		return state;
	}

	@Override
	public void buildSurface(WorldGenRegion world, StructureManager manager, RandomState random, ChunkAccess chunk) {
		this.deformTerrainForFeature(world, chunk);

		super.buildSurface(world, manager, random, chunk);

//		this.darkForestCanopyHeight.ifPresent(integer -> this.addDarkForestCanopy(world, chunk, integer));

//		addGlaciers(world, chunk);
	}

//	private void addGlaciers(WorldGenRegion primer, ChunkAccess chunk) {
//
//		BlockState glacierBase = Blocks.GRAVEL.defaultBlockState();
//		BlockState glacierMain = Blocks.PACKED_ICE.defaultBlockState();
//		BlockState glacierTop = Blocks.ICE.defaultBlockState();
//
//		for (int z = 0; z < 16; z++) {
//			for (int x = 0; x < 16; x++) {
//				Optional<ResourceKey<Biome>> biome = primer.getBiome(primer.getCenter().getWorldPosition().offset(x, 0, z)).unwrapKey();
//				if (biome.isEmpty() || !BiomeKeys.GLACIER.location().equals(biome.get().location())) continue;
//
//				// find the (current) top block
//				int gBase = -1;
//				for (int y = 127; y >= 0; y--) {
//					Block currentBlock = primer.getBlockState(withY(primer.getCenter().getWorldPosition().offset(x, 0, z), y)).getBlock();
//					if (currentBlock == Blocks.STONE) {
//						gBase = y;
//						primer.setBlock(withY(primer.getCenter().getWorldPosition().offset(x, 0, z), y), glacierBase, 3);
//						break;
//					}
//				}
//
//				// raise the glacier from that top block
//				int gHeight = 32;
//				int gTop = Math.min(gBase + gHeight, 127);
//
//				for (int y = gBase; y < gTop; y++) {
//					primer.setBlock(withY(primer.getCenter().getWorldPosition().offset(x, 0, z), y), glacierMain, 3);
//				}
//				primer.setBlock(withY(primer.getCenter().getWorldPosition().offset(x, 0, z), gTop), glacierTop, 3);
//			}
//		}
//	}

	@Override
	public void addDebugScreenInfo(List<String> p_223175_, RandomState p_223176_, BlockPos p_223177_) {
		//do we do anything with this? we need to implement it for some reason
	}

	// TODO Is there a way we can make a beard instead of making hard terrain shapes?
	protected final void deformTerrainForFeature(WorldGenRegion primer, ChunkAccess chunk) {
		Vec2i featureRelativePos = new Vec2i();
		IafLandmark nearFeature = LegacyLandmarkPlacements.getNearestLandmark(primer.getCenter().x, primer.getCenter().z, primer, featureRelativePos);

		//Optional<StructureStart<?>> structureStart = TFGenerationSettings.locateTFStructureInRange(primer.getLevel(), nearFeature, chunk.getPos().getWorldPosition(), nearFeature.size + 1);

		if (!nearFeature.requiresTerraforming) {
			return;
		}

		final int relativeFeatureX = featureRelativePos.x;
		final int relativeFeatureZ = featureRelativePos.z;

		if (LegacyLandmarkPlacements.isTheseFeatures(nearFeature, IafLandmark.FIRE_DRAGON_ROOST)) { // IafLandmark.SMALL_HILL, IafLandmark.MEDIUM_HILL, IafLandmark.LARGE_HILL, IafLandmark.HYDRA_LAIR)) {
			int hdiam = (nearFeature.size * 2 + 1) * 16;

			for (int xInChunk = 0; xInChunk < 16; xInChunk++) {
				for (int zInChunk = 0; zInChunk < 16; zInChunk++) {
					int featureDX = xInChunk - relativeFeatureX;
					int featureDZ = zInChunk - relativeFeatureZ;

					float dist = (int) Mth.sqrt(featureDX * featureDX + featureDZ * featureDZ);
					float hheight = (int) (Mth.cos(dist / hdiam * Mth.PI) * (hdiam / 3F));
					this.raiseHills(primer, chunk, nearFeature, hdiam, xInChunk, zInChunk, featureDX, featureDZ, hheight);
				}
			}
		}
//		else if (nearFeature == IafLandmark.HEDGE_MAZE || nearFeature == IafLandmark.NAGA_COURTYARD || nearFeature == IafLandmark.QUEST_GROVE) {
//			for (int xInChunk = 0; xInChunk < 16; xInChunk++) {
//				for (int zInChunk = 0; zInChunk < 16; zInChunk++) {
//					int featureDX = xInChunk - relativeFeatureX;
//					int featureDZ = zInChunk - relativeFeatureZ;
//					flattenTerrainForFeature(primer, nearFeature, xInChunk, zInChunk, featureDX, featureDZ);
//				}
//			}
//		} else if (nearFeature == IafLandmark.YETI_CAVE) {
//			for (int xInChunk = 0; xInChunk < 16; xInChunk++) {
//				for (int zInChunk = 0; zInChunk < 16; zInChunk++) {
//					int featureDX = xInChunk - relativeFeatureX;
//					int featureDZ = zInChunk - relativeFeatureZ;
//
//					this.deformTerrainForYetiLair(primer, nearFeature, xInChunk, zInChunk, featureDX, featureDZ);
//				}
//			}
//		} else if (nearFeature == IafLandmark.TROLL_CAVE) {
//			// troll cloud, more like
//			this.deformTerrainForTrollCloud2(primer, chunk, relativeFeatureX, relativeFeatureZ);
//		}

		// done!
	}

	private void flattenTerrainForFeature(WorldGenRegion primer, IafLandmark nearFeature, int x, int z, int dx, int dz) {

		float squishFactor = 0f;
		int mazeHeight = 5;
		final int FEATURE_BOUNDARY = (nearFeature.size * 2 + 1) * 8 - 8;

		if (dx <= -FEATURE_BOUNDARY) {
			squishFactor = (-dx - FEATURE_BOUNDARY) / 8.0f;
		} else if (dx >= FEATURE_BOUNDARY) {
			squishFactor = (dx - FEATURE_BOUNDARY) / 8.0f;
		}

		if (dz <= -FEATURE_BOUNDARY) {
			squishFactor = Math.max(squishFactor, (-dz - FEATURE_BOUNDARY) / 8.0f);
		} else if (dz >= FEATURE_BOUNDARY) {
			squishFactor = Math.max(squishFactor, (dz - FEATURE_BOUNDARY) / 8.0f);
		}

		if (squishFactor > 0f) {
			// blend the old terrain height to arena height
			for (int y = 0; y <= 127; y++) {
				Block currentTerrain = primer.getBlockState(withY(primer.getCenter().getWorldPosition().offset(x, 0, z), y)).getBlock();
				// we're still in ground
				if (currentTerrain != Blocks.STONE) {
					// we found the lowest chunk of earth
					mazeHeight += ((y - mazeHeight) * squishFactor);
					break;
				}
			}
		}
	}

	protected final BlockPos withY(BlockPos old, int y) {
		return new BlockPos(old.getX(), y, old.getZ());
	}

	/**
	 * Raises up and hollows out the hollow hills.
	 */ // TODO Add some surface noise
	// FIXME Make this method process whole chunks instead of columns only
	private void raiseHills(WorldGenRegion world, ChunkAccess chunk, IafLandmark nearFeature, int hdiam, int xInChunk, int zInChunk, int featureDX, int featureDZ, float hillHeight) {
		BlockPos.MutableBlockPos movingPos = world.getCenter().getWorldPosition().offset(xInChunk, 0, zInChunk).mutable();

		// raise the hill
		int groundHeight = chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, movingPos.getX(), movingPos.getZ());
		float noiseRaw = this.surfaceNoiseGetter.map(ns -> {
			// FIXME Once the above FIXME is done, instantiate the noise chunk and build the hill from there

			//ns.baseNoise.instantiate()

			// (movingPos.getX() / 64f, movingPos.getZ() / 64f, 1.0f, 256) * 32f)

			return 0f;
		}).orElse(0f);
		float totalHeightRaw = groundHeight * 0.75f + this.getSeaLevel() * 0.25f + hillHeight + noiseRaw;
		int totalHeight = (int) (((int) totalHeightRaw >> 1) * 0.375f + totalHeightRaw * 0.625f);

		for (int y = groundHeight; y <= totalHeight; y++) {
			world.setBlock(movingPos.setY(y), this.defaultBlock, 3);
		}

		// add the hollow part. Also turn water into stone below that
		int hollow = Math.min((int) hillHeight - 4 - nearFeature.size, totalHeight - 3);

		// hydra lair has a piece missing
//		if (nearFeature == IafLandmark.HYDRA_LAIR) {
//			int mx = featureDX + 16;
//			int mz = featureDZ + 16;
//			int mdist = (int) Mth.sqrt(mx * mx + mz * mz);
//			int mheight = (int) (Mth.cos(mdist / (hdiam / 1.5f) * Mth.PI) * (hdiam / 1.5f));
//
//			hollow = Math.max(mheight - 4, hollow);
//		}

		// hollow out the hollow parts
		int hollowFloor = this.getSeaLevel() - 5 - (hollow >> 3);

		for (int y = hollowFloor + 1; y < hollowFloor + hollow; y++) {
			world.setBlock(movingPos.setY(y), Blocks.AIR.defaultBlockState(), 3);
		}
	}

	static void forceHeightMapLevel(ChunkAccess chunk, Heightmap.Types type, BlockPos pos, int dY) {
//		chunk.getOrCreateHeightmapUnprimed(type).setHeight(pos.getX() & 15, pos.getZ() & 15, dY + 1);
	}

	private static int getSpawnListIndexAt(StructureStart start, BlockPos pos) {
		int highestFoundIndex = -1;
		for (StructurePiece component : start.getPieces()) {
			if (component.getBoundingBox().isInside(pos)) {
//				if (component instanceof TFStructureComponent tfComponent) {
//					if (tfComponent.spawnListIndex > highestFoundIndex)
//						highestFoundIndex = tfComponent.spawnListIndex;
//				} else
					return 0;
			}
		}
		return highestFoundIndex;
	}

	@Nullable
	public static List<MobSpawnSettings.SpawnerData> gatherPotentialSpawns(StructureManager structureManager, MobCategory classification, BlockPos pos) {
		for (Structure structure : structureManager.registryAccess().ownedRegistryOrThrow(Registry.STRUCTURE_REGISTRY)) {
			if (structure instanceof DragonLandmark landmark) {
				StructureStart start = structureManager.getStructureAt(pos, landmark);
				if (!start.isValid())
					continue;

				if (classification != MobCategory.MONSTER)
					return landmark.getSpawnableList(classification);
//				if ((start instanceof TFStructureStart<?> s && s.isConquered()))
//					return null;
				final int index = getSpawnListIndexAt(start, pos);
				if (index < 0)
					return null;
				return landmark.getSpawnableMonsterList(index);
			}
		}
		return null;
	}

	@Override
	public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Holder<Biome> biome, StructureManager structureManager, MobCategory mobCategory, BlockPos pos) {
		List<MobSpawnSettings.SpawnerData> potentialStructureSpawns = gatherPotentialSpawns(structureManager, mobCategory, pos);
		if (potentialStructureSpawns != null)
			return WeightedRandomList.create(potentialStructureSpawns);

		return super.getMobsAt(biome, structureManager, mobCategory, pos);
	}

	public IafLandmark pickLandmarkForChunk(final ChunkPos chunk, final WorldGenLevel world) {
		return this.pickLandmarkForChunk(chunk.x, chunk.z, world);
	}

	public IafLandmark pickLandmarkForChunk(int x, int z, final WorldGenLevel world) {
		return LegacyLandmarkPlacements.pickLandmarkForChunk(x, z, world);
	}

	public boolean isLandmarkPickedForChunk(IafLandmark landmark, Holder<Biome> biome, int chunkX, int chunkZ, long seed) {
		if (!LegacyLandmarkPlacements.chunkHasLandmarkCenter(chunkX, chunkZ)) return false;

		var biomeKey = biome.unwrapKey();
		if (biomeKey.isEmpty()) return false;

		return this.biomeLandmarkOverrides.containsKey(biomeKey.get())
				? this.biomeGuaranteedLandmark(biomeKey.get(), landmark)
				: landmark == LegacyLandmarkPlacements.pickVarietyLandmark(chunkX, chunkZ, seed);
	}

	public boolean biomeGuaranteedLandmark(ResourceKey<Biome> biome, IafLandmark landmark) {
		if (!this.biomeLandmarkOverrides.containsKey(biome)) return false;
		return this.biomeLandmarkOverrides.getOrDefault(biome, ImmutableSet.of()).contains(landmark);
	}

//	@Nullable
//	@Override
//	public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel level, HolderSet<Structure> targetStructures, BlockPos pos, int searchRadius, boolean skipKnownStructures) {
//		RandomState randomState = level.getChunkSource().randomState();
//
//		@Nullable
//		Pair<BlockPos, Holder<Structure>> nearest = super.findNearestMapStructure(level, targetStructures, pos, searchRadius, skipKnownStructures);
//
//		Map<BiomeForcedLandmarkPlacement, Set<Holder<Structure>>> placementSetMap = new Object2ObjectArrayMap<>();
//		for (Holder<Structure> holder : targetStructures) {
//			for (StructurePlacement structureplacement : this.getPlacementsForStructure(holder, randomState)) {
//				if (structureplacement instanceof BiomeForcedLandmarkPlacement landmarkPlacement) {
//					placementSetMap.computeIfAbsent(landmarkPlacement, v -> new ObjectArraySet<>()).add(holder);
//				}
//			}
//		}
//
//		if (placementSetMap.isEmpty()) return nearest;
//
//		double distance = nearest == null ? Double.MAX_VALUE : nearest.getFirst().distSqr(pos);
//
//		for (BlockPos landmarkCenterPosition : LegacyLandmarkPlacements.landmarkCenterScanner(pos, Mth.ceil(Mth.sqrt(searchRadius)))) {
//			for (Map.Entry<BiomeForcedLandmarkPlacement, Set<Holder<Structure>>> landmarkPlacement : placementSetMap.entrySet()) {
//				if (!landmarkPlacement.getKey().isPlacementChunk(this, randomState, randomState.legacyLevelSeed(), landmarkCenterPosition.getX() >> 4, landmarkCenterPosition.getZ() >> 4)) continue;
//
//				for (Holder<Structure> targetStructure : targetStructures) {
//					if (landmarkPlacement.getValue().contains(targetStructure)) {
//						final double newDistance = landmarkCenterPosition.distToLowCornerSqr(pos.getX(), landmarkCenterPosition.getY(), pos.getZ());
//
//						if (newDistance < distance) {
//							nearest = new Pair<>(landmarkCenterPosition, targetStructure);
//							distance = newDistance;
//						}
//					}
//				}
//			}
//		}
//
//		return nearest;
//	}
}
