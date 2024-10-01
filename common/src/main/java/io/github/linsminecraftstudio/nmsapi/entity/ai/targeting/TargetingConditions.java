package io.github.linsminecraftstudio.nmsapi.entity.ai.targeting;

import io.github.linsminecraftstudio.nmsapi.NMSAPI;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPILivingEntity;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIMob;
import lombok.Getter;
import org.bukkit.Difficulty;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import javax.annotation.Nullable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Getter
public class TargetingConditions implements BiPredicate<LivingEntity, LivingEntity> {
    private final boolean isCombat;

    private double range = -1;
    private Predicate<LivingEntity> selector;
    private boolean checkLineOfSight = true;
    private boolean testInvisible = true;
    private boolean useFollowRange = true;

    private TargetingConditions(boolean isCombat) {
        this.isCombat = isCombat;
    }

    public static TargetingConditions forCombat() {
        return new TargetingConditions(true);
    }

    public static TargetingConditions forNonCombat() {
        return new TargetingConditions(false);
    }

    public TargetingConditions selector(@Nullable Predicate<LivingEntity> predicate) {
        this.selector = predicate;
        return this;
    }

    public TargetingConditions range(double baseMaxDistance) {
        this.range = baseMaxDistance;
        return this;
    }

    public TargetingConditions ignoreLineOfSight() {
        this.checkLineOfSight = false;
        return this;
    }

    public TargetingConditions ignoreInvisibilityTesting() {
        this.testInvisible = false;
        return this;
    }

    public TargetingConditions useFollowRange() {
        this.useFollowRange = true;
        return this;
    }

    public TargetingConditions copy() {
        TargetingConditions targetingConditions = this.isCombat ? forCombat() : forNonCombat();
        targetingConditions.range = this.range;
        targetingConditions.checkLineOfSight = this.checkLineOfSight;
        targetingConditions.testInvisible = this.testInvisible;
        targetingConditions.selector = this.selector;
        return targetingConditions;
    }

    @Override
    public boolean test(LivingEntity base, LivingEntity target) {
        NMSAPILivingEntity baseEntity = (NMSAPILivingEntity) NMSAPI.getEntity(base);
        NMSAPILivingEntity targetEntity = (NMSAPILivingEntity) NMSAPI.getEntity(target);

        if (base.equals(target)) {
            return false;
        } else if (!targetEntity.canBeSeenByAnyone()) {
            return false;
        } else if (this.selector != null && !this.selector.test(target)) {
            return false;
        } else {
            if (baseEntity == null) {
                return !this.isCombat || (targetEntity.canBeSeenAsEnemy() && target.getWorld().getDifficulty() != Difficulty.PEACEFUL);
            } else {
                if (this.isCombat && (!baseEntity.canAttack(targetEntity) || !baseEntity.canAttackType(target.getType()) || baseEntity.isAlliedTo(targetEntity))) {
                    return false;
                }

                if (this.range > 0.0) {
                    double d = this.testInvisible ? targetEntity.getVisibilityPercent(baseEntity) : 1.0;
                    double e = Math.max((this.useFollowRange ? baseEntity.getFollowRange() : this.range) * d, 2.0);
                    double f = baseEntity.distanceToSqr(target.getX(), target.getY(), target.getZ());
                    if (f > e * e) {
                        return false;
                    }
                }

                if (this.checkLineOfSight && base instanceof Mob mob) {
                    NMSAPIMob baseMob = (NMSAPIMob) NMSAPI.getEntity(mob);
                    return baseMob.hasLineOfSensingSight(target);
                }
            }

            return true;
        }
    }
}
