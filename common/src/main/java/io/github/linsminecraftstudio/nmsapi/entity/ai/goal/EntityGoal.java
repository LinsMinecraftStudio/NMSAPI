package io.github.linsminecraftstudio.nmsapi.entity.ai.goal;

import java.util.EnumSet;

public abstract class EntityGoal {
    private final EnumSet<Flag> goalTypes = EnumSet.noneOf(Flag.class);

    protected static int reducedTickDelay(int serverTicks) {
        return -Math.floorDiv(-serverTicks, 2);
    }

    public abstract boolean canUse();

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public boolean isInterruptable() {
        return true;
    }

    public void start() {
    }

    public void stop() {
    }

    public boolean requiresUpdateEveryTick() {
        return false;
    }

    public void tick() {
    }

    public boolean fromNms() {
        return false;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public EnumSet<Flag> getFlags() {
        return this.goalTypes;
    }

    public void setFlags(EnumSet<Flag> controls) {
        this.goalTypes.clear();
        this.goalTypes.addAll(controls);
        if (this.goalTypes.isEmpty()) {
            this.goalTypes.add(Flag.UNKNOWN_BEHAVIOR);
        }
    }

    protected int adjustedTickDelay(int ticks) {
        return this.requiresUpdateEveryTick() ? ticks : reducedTickDelay(ticks);
    }

    public enum Flag {
        UNKNOWN_BEHAVIOR,
        MOVE,
        LOOK,
        JUMP,
        TARGET;

        Flag() {
        }
    }
}
