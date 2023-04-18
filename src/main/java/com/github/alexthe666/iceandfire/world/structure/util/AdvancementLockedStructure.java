package com.github.alexthe666.iceandfire.world.structure.util;

import com.github.alexthe666.iceandfire.util.PlayerHelper;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface AdvancementLockedStructure {
    default boolean doesPlayerHaveRequiredAdvancements(Player player) {
        return PlayerHelper.playerHasRequiredAdvancements(player, this.getRequiredAdvancements());
    }

    List<ResourceLocation> getRequiredAdvancements();

    record AdvancementLockConfig(List<ResourceLocation> requiredAdvancements) {
        public static Codec<AdvancementLockConfig> CODEC = ResourceLocation.CODEC.listOf().xmap(AdvancementLockConfig::new, AdvancementLockConfig::requiredAdvancements);
    }
}
