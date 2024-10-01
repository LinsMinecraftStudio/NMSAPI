package io.github.linsminecraftstudio.nmsapi.impl.v1206.object;

import com.destroystokyo.paper.util.set.OptimizedSmallEnumSet;
import io.github.linsminecraftstudio.nmsapi.entity.ai.goal.EntityGoal;
import io.github.linsminecraftstudio.nmsapi.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class ObjectConverter {
    private ObjectConverter() {}
    
    public static EntityGoal toEntityGoal(Goal nmsGoal) {
        return new EntityGoalImpl(nmsGoal);
    }
    
    public static <T extends Enum<T>> EnumSet<T> paperEnumSetToJavaEnumSet(Class<T> enumClass, OptimizedSmallEnumSet<T> paperEnumSet) {
        EnumSet<T> javaEnumSet = EnumSet.noneOf(enumClass);
        for (T enumValue : enumClass.getEnumConstants()) {
            if (paperEnumSet.contains(enumValue)) {
                javaEnumSet.add(enumValue);
            }
        }
        return javaEnumSet;
    }
    
    public static EntityGoal.Flag toEntityGoalFlag(Goal.Flag nmsFlag) {
        return switch (nmsFlag) {
            case MOVE -> EntityGoal.Flag.MOVE;
            case LOOK -> EntityGoal.Flag.LOOK;
            case UNKNOWN_BEHAVIOR -> EntityGoal.Flag.UNKNOWN_BEHAVIOR;
            case JUMP -> EntityGoal.Flag.JUMP;
            case TARGET -> EntityGoal.Flag.TARGET;
        };
    }

    public static Goal.Flag toNmsGoalFlag(EntityGoal.Flag entityGoalFlag) {
        return switch (entityGoalFlag) {
            case MOVE -> Goal.Flag.MOVE;
            case LOOK -> Goal.Flag.LOOK;
            case UNKNOWN_BEHAVIOR -> Goal.Flag.UNKNOWN_BEHAVIOR;
            case JUMP -> Goal.Flag.JUMP;
            case TARGET -> Goal.Flag.TARGET;
        };
    }

    public static Class<? extends Entity> ToNmsEntityClass(EntityType bukkitEntityType) {
        return CraftEntityType.bukkitToMinecraft(bukkitEntityType).getBaseClass();
    }

    public static AABB toNotchAABB(BoundingBox boundingBox) {
        return new AABB(
                boundingBox.getMinX(),
                boundingBox.getMinY(),
                boundingBox.getMinZ(),
                boundingBox.getMaxX(),
                boundingBox.getMaxY(),
                boundingBox.getMaxZ()
        );
    }

    public static net.minecraft.world.entity.ai.targeting.TargetingConditions toNmsTC(TargetingConditions targetPredicate) {
        net.minecraft.world.entity.ai.targeting.TargetingConditions base = targetPredicate.isCombat() ?
                net.minecraft.world.entity.ai.targeting.TargetingConditions.forCombat() :
                net.minecraft.world.entity.ai.targeting.TargetingConditions.forNonCombat();

        if (!targetPredicate.isCheckLineOfSight()) {
            base.ignoreLineOfSight();
        }

        if (targetPredicate.isUseFollowRange()) {
            base.useFollowRange();
        }

        if (targetPredicate.isTestInvisible()) {
            base.ignoreInvisibilityTesting();
        }

        base.selector(targetPredicate.getSelector() == null ? null : le -> targetPredicate.getSelector().test(le.getBukkitLivingEntity()));
        base.range(targetPredicate.getRange());

        return base;
    }

    private static class EntityGoalImpl extends EntityGoal {
        private final Goal nmsGoal;
        
        public EntityGoalImpl(Goal nmsGoal) {
            this.nmsGoal = nmsGoal;
        }

        @Override
        public boolean canUse() {
            return nmsGoal.canUse();
        }

        @Override
        public void stop() {
            nmsGoal.stop();
        }

        @Override
        public void start() {
            nmsGoal.start();
        }

        @Override
        public boolean isInterruptable() {
            return nmsGoal.isInterruptable();
        }

        @Override
        public boolean canContinueToUse() {
            return nmsGoal.canContinueToUse();
        }



        @Override
        public EnumSet<Flag> getFlags() {
            EnumSet<Goal.Flag> javaEnumSet = paperEnumSetToJavaEnumSet(Goal.Flag.class, nmsGoal.getFlags());
            return javaEnumSet.stream().map(ObjectConverter::toEntityGoalFlag).collect(Collectors.toCollection(() -> EnumSet.noneOf(Flag.class)));
        }
    }
}
