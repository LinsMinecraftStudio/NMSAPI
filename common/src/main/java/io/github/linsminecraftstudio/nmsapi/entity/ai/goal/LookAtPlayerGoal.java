package io.github.linsminecraftstudio.nmsapi.entity.ai.goal;

import io.github.linsminecraftstudio.nmsapi.NMSAPI;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPILivingEntity;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIMob;
import io.github.linsminecraftstudio.nmsapi.entity.ai.targeting.TargetingConditions;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;

@Getter
public class LookAtPlayerGoal extends EntityGoal {
    protected final float lookDistance;
    protected final float probability;
    protected final Class<? extends LivingEntity> lookAtType;
    protected final TargetingConditions lookAtContext;
    private final Mob mob;
    private final NMSAPIMob wrappedMob;
    private final boolean onlyHorizontal;
    private final Location eyeLocation;
    @Nullable
    protected Entity lookAt;
    protected NMSAPIEntity wrappedLookAt;
    private int lookTime;

    public LookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> targetType, float range) {
        this(mob, targetType, range, 0.02F);
    }

    public LookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> targetType, float range, float chance) {
        this(mob, targetType, range, chance, false);
    }

    public LookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> targetType, float range, float chance, boolean lookForward) {
        this.mob = mob;
        this.wrappedMob = (NMSAPIMob) NMSAPI.getEntity(mob);
        this.lookAtType = targetType;
        this.lookDistance = range;
        this.probability = chance;
        this.onlyHorizontal = lookForward;
        this.eyeLocation = mob.getEyeLocation();
        this.setFlags(EnumSet.of(Flag.LOOK));
        if (targetType == Player.class) {
            this.lookAtContext = TargetingConditions.forNonCombat().range(range).selector((entity) -> entity.getVehicle() == null);
        } else {
            this.lookAtContext = TargetingConditions.forNonCombat().range(range);
        }
    }

    @Override
    public boolean canUse() {
        if (this.wrappedMob.getEntityRandomSource().nextFloat() >= this.probability) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                this.lookAt = this.mob.getTarget();
            }

            if (this.lookAtType == Player.class) {
                Optional.ofNullable(this.wrappedMob.level().getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), eyeLocation.getY(), this.mob.getZ())).ifPresentOrElse((player) -> this.lookAt = player.getBukkitPlayer(), () -> this.lookAt = null);
            } else {
                this.lookAt = this.wrappedMob.level().getNearestEntity(this.wrappedMob.level().getEntitiesOfClass(this.lookAtType, this.wrappedMob.getBoundingBox().inflate(this.lookDistance, 3.0, this.lookDistance), (livingEntity) -> true).stream().map(e -> (NMSAPILivingEntity) e).toList(), this.lookAtContext, this.mob, this.mob.getX(), eyeLocation.getY(), this.mob.getZ());
            }

            return this.lookAt != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.lookAt.isDead()) {
            return false;
        } else if (this.wrappedMob.distanceToSqr(this.lookAt) > (double) (this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0;
        }
    }

    @Override
    public void start() {
        this.lookTime = this.adjustedTickDelay(40 + this.wrappedMob.getEntityRandomSource().nextInt(40));
    }

    @Override
    public void stop() {
        this.lookAt = null;
    }

    @Override
    public void tick() {
        if (!this.lookAt.isDead()) {
            this.wrappedLookAt = NMSAPI.getEntity(this.lookAt);
            double d = this.onlyHorizontal ? this.wrappedMob.getEyeY() : this.wrappedLookAt.getEyeY();
            this.mob.lookAt(this.lookAt.getX(), d, this.lookAt.getZ());
            --this.lookTime;
        }
    }
}
