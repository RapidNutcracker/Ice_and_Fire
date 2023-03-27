package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.enums.EnumDragonType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDragonFlesh extends ItemGenericFood {

    EnumDragonType dragonType;

    public ItemDragonFlesh(EnumDragonType dragonType) {
        super(8, 0.8F, true, false, false);
        this.dragonType = dragonType;
    }

    static String getNameForType(EnumDragonType dragonType) {
        return switch (dragonType) {
            case FIRE -> "fire_dragon_flesh";
            case ICE -> "ice_dragon_flesh";
            case LIGHTNING -> "lightning_dragon_flesh";
            default -> "fire_dragon_flesh";
        };
    }

    @Override
    public void onFoodEaten(ItemStack stack, Level worldIn, LivingEntity livingEntity) {
        if (!worldIn.isClientSide) {
            if (dragonType == EnumDragonType.FIRE) {
                livingEntity.setSecondsOnFire(5);
            } else if (dragonType == EnumDragonType.ICE) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            } else {
                if (!livingEntity.level.isClientSide) {
                    LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(livingEntity.level);
                    lightningBoltEntity.moveTo(livingEntity.position());
                    if (!livingEntity.level.isClientSide) {
                        livingEntity.level.addFreshEntity(lightningBoltEntity);
                    }
                }
            }
        }
    }
}
