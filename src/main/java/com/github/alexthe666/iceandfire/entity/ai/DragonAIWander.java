package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class DragonAIWander extends Goal {
    private EntityDragonBase dragon;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int executionChance;
    private boolean mustUpdate;

    public DragonAIWander(EntityDragonBase creatureIn, double speedIn) {
        this(creatureIn, speedIn, 20);
    }

    public DragonAIWander(EntityDragonBase creatureIn, double speedIn, int chance) {
        this.dragon = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));

    }

    @Override
    public boolean shouldExecute() {
        if (!dragon.canMove()) {
            return false;
        }
        if (dragon.getControllingPassenger() != null) {
            return false;
        }
        if (dragon.isFlying() || dragon.isHovering()) {
            return false;
        }
        if (!this.mustUpdate) {
            if (this.dragon.getRNG().nextInt(executionChance) != 0) {
                return false;
            }
        }
        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.dragon, 10, 7);
        if (vec3d == null) {
            return false;
        } else {
            this.xPosition = vec3d.x;
            this.yPosition = vec3d.y;
            this.zPosition = vec3d.z;
            this.mustUpdate = false;

            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.dragon.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.dragon.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    public void makeUpdate() {
        this.mustUpdate = true;
    }

    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }
}