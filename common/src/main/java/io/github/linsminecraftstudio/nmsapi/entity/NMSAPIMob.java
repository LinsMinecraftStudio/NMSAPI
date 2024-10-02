package io.github.linsminecraftstudio.nmsapi.entity;

import io.github.linsminecraftstudio.nmsapi.entity.ai.goal.EntityGoal;
import org.bukkit.entity.Entity;

import java.util.function.Predicate;

public interface NMSAPIMob extends NMSAPILivingEntity {
    void addGoal(int priority, EntityGoal goal);

    void removeAllGoals(Predicate<EntityGoal> predicate);

    default void removeAllGoals() {
        removeAllGoals(goal -> true);
    }

    boolean hasLineOfSensingSight(Entity target);

    default boolean hasLineOfSensingSight(NMSAPIEntity target) {
        return hasLineOfSensingSight(target.getBukkitEntity());
    }
}
