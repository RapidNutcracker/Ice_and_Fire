package com.github.alexthe666.iceandfire.tags;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class IafTags {

    public static void initTags() {}

    public static TagKey<Structure> NO_LAKES = TagKey.create(Registry.STRUCTURE_REGISTRY,
            new ResourceLocation(IceAndFire.MODID, "no_lakes"));

}
