package io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.entity;

import io.github.linsminecraftstudio.nmsapi.entity.ai.goal.EntityGoal;
import io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.object.ObjectConverter;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class NMSAPIWrappedGoal extends Goal {
    private final EntityGoal entityGoal;

    public NMSAPIWrappedGoal(EntityGoal entityGoal) {
        this.entityGoal = entityGoal;

        setFlags(getGoalTypes(entityGoal.getFlags()));
    }

    private EnumSet<Flag> getGoalTypes(EnumSet<EntityGoal.Flag> flags) {
        EnumSet<Flag> goals = EnumSet.noneOf(Flag.class);

        for (EntityGoal.Flag type : flags) {
            goals.add(ObjectConverter.toNmsGoalFlag(type));
        }

        return goals;
    }

    public EntityGoal getEntityGoal() {
        return entityGoal;
    }

    @Override
    public void start() {
        entityGoal.start();
    }

    @Override
    public void stop() {
        entityGoal.stop();
    }

    @Override
    public void tick() {
        entityGoal.tick();
    }

    @Override
    public boolean isInterruptable() {
        return entityGoal.isInterruptable();
    }

    @Override
    public boolean canContinueToUse() {
        return entityGoal.canContinueToUse();
    }

    @Override
    public boolean canUse() {
        return entityGoal.canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return entityGoal.requiresUpdateEveryTick();
    }
}
