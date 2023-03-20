package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexSwarmer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MyrmexAIFollowSummoner extends Goal {
    private final EntityMyrmexSwarmer tameable;
    Level world;
    float maxDist;
    float minDist;
    private LivingEntity owner;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public MyrmexAIFollowSummoner(EntityMyrmexSwarmer tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.tameable = tameableIn;
        this.world = tameableIn.level;
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity LivingEntity = this.tameable.getSummoner();
        if (tameable.getTarget() != null) {
            return false;
        }
        if (LivingEntity == null) {
            return false;
        } else if (LivingEntity instanceof Player && LivingEntity.isSpectator()) {
            return false;
        } else if (this.tameable.distanceToSqr(LivingEntity) < this.minDist * this.minDist) {
            return false;
        } else {
            this.owner = LivingEntity;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.tameable.getTarget() == null
            && this.tameable.distanceToSqr(this.owner) > this.maxDist * this.maxDist;
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathfindingMalus(BlockPathTypes.WATER);
        this.tameable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.tameable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    private boolean isEmptyBlock(BlockPos pos) {
        BlockState BlockState = this.world.getBlockState(pos);
        return BlockState.getMaterial() == Material.AIR || !BlockState.canOcclude();
    }

    @Override
    public void tick() {
        if (this.tameable.getTarget() != null) {
            return;
        }
        this.tameable.getLookControl().setLookAt(this.owner, 10.0F,
            this.tameable.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            this.tameable.getMoveControl().setWantedPosition(this.owner.getX(), this.owner.getY() + this.owner.getEyeHeight(), this.owner.getZ(), 0.25D);
            if (!this.tameable.isLeashed()) {
                if (this.tameable.distanceToSqr(this.owner) >= 50.0D) {
                    final int i = Mth.floor(this.owner.getX()) - 2;
                    final int j = Mth.floor(this.owner.getZ()) - 2;
                    final int k = Mth.floor(this.owner.getBoundingBox().minY);

                    for (int l = 0; l <= 4; ++l) {
                        for (int i1 = 0; i1 <= 4; ++i1) {
                            if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isEmptyBlock(new BlockPos(i + l, k, j + i1)) && this.isEmptyBlock(new BlockPos(i + l, k + 1, j + i1))) {
                                this.tameable.moveTo(i + l + 0.5F, k + 1.5, j + i1 + 0.5F,
                                    this.tameable.getYRot(), this.tameable.getXRot());
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}