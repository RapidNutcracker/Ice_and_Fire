package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AquaticAIGetInWater extends Goal {

    private final Mob creature;
    private final double movementSpeed;
    private final Level world;
    private double shelterX;
    private double shelterY;
    private double shelterZ;

    public AquaticAIGetInWater(Mob theCreatureIn, double movementSpeedIn) {
        this.creature = theCreatureIn;
        this.movementSpeed = movementSpeedIn;
        this.world = theCreatureIn.level;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    protected boolean isAttackerInWater() {
        return creature.getTarget() != null && !creature.getTarget().isInWater();
    }

    @Override
    public boolean canUse() {
        if (creature.isVehicle() || creature instanceof TamableAnimal && ((TamableAnimal) creature).isTame()
            || creature.isInWater() || isAttackerInWater() || creature instanceof EntitySiren
            && (((EntitySiren) creature).isSinging() || ((EntitySiren) creature).wantsToSing())) {
            return false;
        } else {
            Vec3 Vector3d = this.findPossibleShelter();

            if (Vector3d == null) {
                return false;
            } else {
                this.shelterX = Vector3d.x;
                this.shelterY = Vector3d.y;
                this.shelterZ = Vector3d.z;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.creature.getNavigation().isDone();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.creature.getNavigation().moveTo(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    @Nullable
    public Vec3 findPossibleShelter() {
        return findPossibleShelter(10, 3);
    }

    @Nullable
    protected Vec3 findPossibleShelter(int xz, int y) {
        RandomSource random = this.creature.getRandom();
        BlockPos blockpos = new BlockPos(this.creature.getX(), this.creature.getBoundingBox().minY,
            this.creature.getZ());

        for (int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.offset(random.nextInt(xz * 2) - xz, random.nextInt(y * 2) - y,
                random.nextInt(xz * 2) - xz);

            if (this.world.getBlockState(blockpos1).getMaterial() == Material.WATER) {
                return new Vec3(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
            }
        }

        return null;
    }
}
