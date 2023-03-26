package com.github.alexthe666.iceandfire.config.biome;

public enum BiomeEntryType {
    REGISTRY_NAME(false), BIOME_TAG(false), BIOME_DICT(true), BIOME_CATEGORY(true);

    private boolean deprecated;

    BiomeEntryType(boolean deprecated){
        this.deprecated = deprecated;
    }

    public boolean isDeprecated() {
        return deprecated;
    }
}