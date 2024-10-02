package io.github.linsminecraftstudio.nmsapi.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface NMSAPILivingEntity extends NMSAPIEntity {
    default double getVisibilityPercent(NMSAPIEntity entity) {
        return getVisibilityPercent(entity.getBukkitEntity());
    }

    double getVisibilityPercent(@Nullable Entity entity);

    LivingEntity getBukkitLivingEntity();

    /**
     * Causes the entity to animate a hurt animation.<br>
     *
     * @param yaw the yaw angle to use for the animation.
     */
    void animateHurt(float yaw);

    void jumpFromGround();

    boolean canBeSeenByAnyone();

    boolean canBeSeenAsEnemy();

    boolean canAttackType(EntityType entityType);

    boolean canAttack(LivingEntity entity);

    default boolean canAttack(NMSAPILivingEntity entity) {
        return canAttack(entity.getBukkitLivingEntity());
    }

    double getFollowRange();

    default double distanceToSqr(double x, double y, double z) {
        Location loc = getBukkitLivingEntity().getLocation();
        double d3 = loc.getX() - x;
        double d4 = loc.getY() - y;
        double d5 = loc.getZ() - z;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    default double distanceToSqr(Entity entity) {
        return distanceToSqr(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
    }
}
