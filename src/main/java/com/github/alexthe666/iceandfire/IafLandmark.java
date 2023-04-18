package com.github.alexthe666.iceandfire;

import com.github.alexthe666.iceandfire.datagen.BiomeTagGenerator;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.IafStructurePieceTypes;
import com.github.alexthe666.iceandfire.world.structure.DragonRoostComponent;
import com.github.alexthe666.iceandfire.world.structure.util.AdvancementLockedStructure;
import com.github.alexthe666.iceandfire.world.structure.util.ControlledSpawns;
import com.github.alexthe666.iceandfire.world.structure.util.DecorationClearance;
import com.github.alexthe666.iceandfire.world.structure.util.StructureHints;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IafLandmark implements StructureHints, AdvancementLockedStructure, DecorationClearance, ControlledSpawns {
    public static final IafLandmark NOTHING = new IafLandmark( 0, "no_feature", false, null, TerrainAdjustment.NONE) { { this.enableDecorations().disableStructure(); } };

    public static final IafLandmark FIRE_DRAGON_ROOST = new IafLandmark( 1, "fire_dragon_roost_landmark", true, BiomeTagGenerator.VALID_FIRE_DRAGON_ROOST_BIOMES, TerrainAdjustment.NONE, true) {
        {
            this.enableDecorations().enableTerrainAlterations();
            this.undergroundDecoAllowed = false;

            this.addMonster(IafEntityRegistry.FIRE_DRAGON.get(), 1, 1, 1);
        }

        @Override
        public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
            return new DragonRoostComponent(IafStructurePieceTypes.IafDragonRoost.get(), this, 0, size, x - 3, y - 2, z - 3);
        }
    };

    public final int size;
    public final String name;
    public final boolean centerBounds;
    protected boolean surfaceDecorationsAllowed = false;
    protected boolean undergroundDecoAllowed = true;
    public boolean isStructureEnabled = true;
    public boolean requiresTerraforming = false; // TODO Terraforming Type? Envelopment vs Flattening maybe?
    private final TagKey<Biome> biomeTag;
    private final TerrainAdjustment beardifierContribution;
    private final ImmutableList<ResourceLocation> requiredAdvancements;
    public boolean hasProtectionAura = true;
    protected boolean adjustToTerrainHeight = false;

    private static int maxPossibleSize;

    private final List<List<MobSpawnSettings.SpawnerData>> spawnableMonsterLists = new ArrayList<>();
    private final List<MobSpawnSettings.SpawnerData> ambientCreatureList = new ArrayList<>();
    private final List<MobSpawnSettings.SpawnerData> waterCreatureList = new ArrayList<>();

    private long lastSpawnedHintMonsterTime;

    private IafLandmark(int size, String name, boolean featureGenerator, @Nullable TagKey<Biome> biomeTag, TerrainAdjustment beardifierContribution, ResourceLocation... requiredAdvancements) {
        this(size, name, featureGenerator, biomeTag, beardifierContribution, false, requiredAdvancements);
    }

    private IafLandmark(int size, String name, boolean featureGenerator, TagKey<Biome> biomeTag, TerrainAdjustment beardifierContribution, boolean centerBounds, ResourceLocation... requiredAdvancements) {
        this.size = size;
        this.name = name;
        this.biomeTag = biomeTag;
        this.beardifierContribution = beardifierContribution;

        this.requiredAdvancements = ImmutableList.copyOf(requiredAdvancements);

        this.centerBounds = centerBounds;

        maxPossibleSize = Math.max(this.size, maxPossibleSize);
    }

    @Deprecated // Not good practice - TODO The root need for this method can be fixed
    public static int getMaxSearchSize() {
        return maxPossibleSize;
    }

    @Override
    public boolean isSurfaceDecorationsAllowed() {
        return this.surfaceDecorationsAllowed;
    }

    @Override
    public boolean isUndergroundDecoAllowed() {
        return this.undergroundDecoAllowed;
    }

    @Override
    public boolean shouldAdjustToTerrain() {
        return this.adjustToTerrainHeight;
    }

    /**
     * Turns on biome-specific decorations like grass and trees near this feature.
     */
    public IafLandmark enableDecorations() {
        this.surfaceDecorationsAllowed = true;
        return this;
    }

    /**
     * Tell the chunkgenerator that we don't have an associated structure.
     */
    public IafLandmark disableStructure() {
        this.enableDecorations();
        this.isStructureEnabled = false;
        return this;
    }

    /**
     * Tell the chunkgenerator that we want the terrain changed nearby.
     */
    public IafLandmark enableTerrainAlterations() {
        this.requiresTerraforming = true;
        return this;
    }

    public IafLandmark disableProtectionAura() {
        this.hasProtectionAura = false;
        return this;
    }

    /**
     * Add a monster to spawn list 0
     */
    public IafLandmark addMonster(EntityType<? extends LivingEntity> monsterClass, int weight, int minGroup, int maxGroup) {
        this.addMonster(0, monsterClass, weight, minGroup, maxGroup);
        return this;
    }

    /**
     * Add a monster to a specific spawn list
     */
    public IafLandmark addMonster(int listIndex, EntityType<? extends LivingEntity> monsterClass, int weight, int minGroup, int maxGroup) {
        List<MobSpawnSettings.SpawnerData> monsterList;
        if (this.spawnableMonsterLists.size() > listIndex) {
            monsterList = this.spawnableMonsterLists.get(listIndex);
        } else {
            monsterList = new ArrayList<>();
            this.spawnableMonsterLists.add(listIndex, monsterList);
        }

        monsterList.add(new MobSpawnSettings.SpawnerData(monsterClass, weight, minGroup, maxGroup));
        return this;
    }
    /**
     * Add a water creature
     */
    public IafLandmark addWaterCreature(EntityType<? extends LivingEntity> monsterClass, int weight, int minGroup, int maxGroup) {
        this.waterCreatureList.add(new MobSpawnSettings.SpawnerData(monsterClass, weight, minGroup, maxGroup));
        return this;
    }

    @Nullable
    public TagKey<Biome> getBiomeTag() {
        return this.biomeTag;
    }

    public TerrainAdjustment getBeardifierContribution() {
        return this.beardifierContribution;
    }

    @Override
    public List<MobSpawnSettings.SpawnerData> getCombinedMonsterSpawnableList() {
        List<MobSpawnSettings.SpawnerData> list = new ArrayList<>();
        spawnableMonsterLists.forEach(l -> {
            if(l != null)
                list.addAll(l);
        });
        return list;
    }

    @Override
    public List<MobSpawnSettings.SpawnerData> getCombinedCreatureSpawnableList() {
        List<MobSpawnSettings.SpawnerData> list = new ArrayList<>();
        list.addAll(ambientCreatureList);
        list.addAll(waterCreatureList);
        return list;
    }

    /**
     * Returns a list of hostile monsters.  Are we ever going to need passive or water creatures?
     */
    @Override
    public List<MobSpawnSettings.SpawnerData> getSpawnableList(MobCategory creatureType) {
        return switch (creatureType) {
            case MONSTER -> this.getSpawnableMonsterList(0);
            case AMBIENT -> this.ambientCreatureList;
            case WATER_CREATURE -> this.waterCreatureList;
            default -> List.of();
        };
    }

    /**
     * Returns a list of hostile monsters in the specified indexed category
     */
    @Override
    public List<MobSpawnSettings.SpawnerData> getSpawnableMonsterList(int index) {
        if (index >= 0 && index < this.spawnableMonsterLists.size()) {
            return this.spawnableMonsterLists.get(index);
        }
        return new ArrayList<>();
    }

    public List<List<MobSpawnSettings.SpawnerData>> getSpawnableMonsterLists() {
        return this.spawnableMonsterLists;
    }

    public List<MobSpawnSettings.SpawnerData> getAmbientCreatureList() {
        return this.ambientCreatureList;
    }

    public List<MobSpawnSettings.SpawnerData> getWaterCreatureList() {
        return this.waterCreatureList;
    }

    @Override
    public List<ResourceLocation> getRequiredAdvancements() {
        return this.requiredAdvancements;
    }

    /**
     * Try several times to spawn a hint monster
     */
    @Override
    public void trySpawnHintMonster(Level world, Player player, BlockPos pos) {
        // check if the timer is valid
        long currentTime = world.getGameTime();

        // if someone set the time backwards, fix the spawn timer
        if (currentTime < this.lastSpawnedHintMonsterTime) {
            this.lastSpawnedHintMonsterTime = 0;
        }

        if (currentTime - this.lastSpawnedHintMonsterTime > 1200) {
            // okay, time is good, try several times to spawn one
            for (int i = 0; i < 20; i++) {
                if (didSpawnHintMonster(world, player, pos)) {
                    this.lastSpawnedHintMonsterTime = currentTime;
                    break;
                }
            }
        }
    }

    @Nullable
    public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
        return null;
    }

    public Optional<Structure.GenerationStub> generateStub(Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();

        boolean dontCenter = this == IafLandmark.FIRE_DRAGON_ROOST; // || this == IafLandmark.TROLL_CAVE || this == IafLandmark.YETI_CAVE;
        int x = (chunkPos.x << 4) + (dontCenter ? 0 : 7);
        int z = (chunkPos.z << 4) + (dontCenter ? 0 : 7);
        int y = Mth.clamp(context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()), context.chunkGenerator().getSeaLevel() + 1, context.chunkGenerator().getSeaLevel() + 7);
//                this.shouldAdjustToTerrain()
//                ? Mth.clamp(context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()), context.chunkGenerator().getSeaLevel() + 1, context.chunkGenerator().getSeaLevel() + 7)
//                : context.chunkGenerator().getSeaLevel();

        return Optional.ofNullable(this.provideFirstPiece(context.structureTemplateManager(), context.chunkGenerator(), RandomSource.create(context.seed() + chunkPos.x * 25117L + chunkPos.z * 151121L), x, y, z)).map(piece -> this.getStructurePieceGenerationStubFunction(piece, context, x, y, z));
    }

    @NotNull
    private Structure.GenerationStub getStructurePieceGenerationStubFunction(StructurePiece startingPiece, Structure.GenerationContext context, int x, int y, int z) {
        return new Structure.GenerationStub(new BlockPos(x, y, z), structurePiecesBuilder -> {
            structurePiecesBuilder.addPiece(startingPiece);
            startingPiece.addChildren(startingPiece, structurePiecesBuilder, context.random());

//            structurePiecesBuilder.pieces.stream()
//                    .filter(TFStructureComponentTemplate.class::isInstance)
//                    .map(TFStructureComponentTemplate.class::cast)
//                    .forEach(t -> t.LAZY_TEMPLATE_LOADER.run());
        });
    }

    public GenerationStep.Decoration getDecorationStage() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
    
    public final BoundingBox getComponentToAddBoundingBox(int x, int y, int z, int minX, int minY, int minZ, int spanX, int spanY, int spanZ, @Nullable Direction dir) {
        if(centerBounds) {
            x += (spanX + minX) / 4;
            y += (spanY + minY) / 4;
            z += (spanZ + minZ) / 4;
        }
        return switch (dir) {
            case WEST -> // '\001'
                    new BoundingBox(x - spanZ + minZ, y + minY, z + minX, x + minZ, y + spanY + minY, z + spanX + minX);
            case NORTH -> // '\002'
                    new BoundingBox(x - spanX - minX, y + minY, z - spanZ - minZ, x - minX, y + spanY + minY, z - minZ);
            case EAST -> // '\003'
                    new BoundingBox(x + minZ, y + minY, z - spanX, x + spanZ + minZ, y + spanY + minY, z + minX);
            default -> // '\0'
                    new BoundingBox(x + minX, y + minY, z + minZ, x + spanX + minX, y + spanY + minY, z + spanZ + minZ);
        };
    }

    private static final ImmutableMap<String, IafLandmark> NAME_2_TYPE = Util.make(() -> ImmutableMap.<String, IafLandmark>builder()
            .put("fire_dragon_roost_landmark", IafLandmark.FIRE_DRAGON_ROOST)
            .build());

    public static final Codec<IafLandmark> CODEC = Codec.STRING.comapFlatMap(
            name -> IafLandmark.NAME_2_TYPE.containsKey(name) ? DataResult.success(IafLandmark.NAME_2_TYPE.get(name)) : DataResult.error("Landmark " + name + " not recognized!"),
            tfFeature -> tfFeature.name
    );

    @Override
    public String toString() {
        return "IafLandmark{" +
                "name='" + name + '\'' +
                //", centerBounds=" + centerBounds +
                //", surfaceDecorationsAllowed=" + surfaceDecorationsAllowed +
                //", undergroundDecoAllowed=" + undergroundDecoAllowed +
                //", isStructureEnabled=" + isStructureEnabled +
                //", requiresTerraforming=" + requiresTerraforming +
                //", biomeTag=" + biomeTag +
                //", beardifierContribution=" + beardifierContribution +
                //", requiredAdvancements=" + requiredAdvancements +
                //", hasProtectionAura=" + hasProtectionAura +
                //", adjustToTerrainHeight=" + adjustToTerrainHeight +
                //", spawnableMonsterLists=" + spawnableMonsterLists +
                //", ambientCreatureList=" + ambientCreatureList +
                //", waterCreatureList=" + waterCreatureList +
                //", lastSpawnedHintMonsterTime=" + lastSpawnedHintMonsterTime +
                '}';
    }
}
