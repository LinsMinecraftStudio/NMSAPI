package io.github.linsminecraftstudio.nmsapi.entity;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface NMSAPILivingEntity extends NMSAPIEntity {
    default double getVisibilityPercent(NMSAPIEntity entity) {
        return getVisibilityPercent(entity.getBukkitEntity());
    }

    double getVisibilityPercent(@Nullable Entity entity);

    /**
     * Causes the entity to animate a hurt animation.<br>
     * @param yaw the yaw angle to use for the animation.
     */
    void animateHurt(float yaw);

    void jumpFromGround();
}
