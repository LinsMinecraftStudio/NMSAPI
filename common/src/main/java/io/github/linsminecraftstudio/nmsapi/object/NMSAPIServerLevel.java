package io.github.linsminecraftstudio.nmsapi.object;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPILivingEntity;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIPlayer;
import io.github.linsminecraftstudio.nmsapi.entity.ai.targeting.TargetingConditions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BoundingBox;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public interface NMSAPIServerLevel {
    @Nullable
    Entity getNearestEntity(List<? extends NMSAPILivingEntity> entityList, TargetingConditions targetPredicate, @Nullable LivingEntity entity, double x, double y, double z);

    @Nullable
    NMSAPIPlayer getNearestPlayer(double x, double y, double z, double maxDistance, @Nullable Predicate<Entity> targetPredicate);

    @Nullable
    NMSAPIPlayer getNearestPlayer(TargetingConditions targetPredicate, LivingEntity entity, double x, double y, double z);

    default NMSAPIPlayer getNearestPlayer(TargetingConditions targetPredicate, NMSAPILivingEntity entity, double x, double y, double z) {
        return getNearestPlayer(targetPredicate, entity.getBukkitLivingEntity(), x, y, z);
    }

    <T extends Entity> List<NMSAPIEntity> getEntitiesOfClass(Class<T> entityClass, BoundingBox box, Predicate<T> predicate);

    NMSAPIRandomSource getRandomSource();
}
