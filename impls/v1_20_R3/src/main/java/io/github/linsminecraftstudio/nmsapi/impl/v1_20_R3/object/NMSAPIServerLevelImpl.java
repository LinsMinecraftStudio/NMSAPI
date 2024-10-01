package io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.object;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPILivingEntity;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIPlayer;
import io.github.linsminecraftstudio.nmsapi.entity.ai.targeting.TargetingConditions;
import io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.NMSAPIImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.entity.NMSAPIPlayerImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.entity.random.NMSAPIRandomSourceImpl;
import io.github.linsminecraftstudio.nmsapi.object.NMSAPIRandomSource;
import io.github.linsminecraftstudio.nmsapi.object.NMSAPIServerLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.bukkit.craftbukkit.v1_20_R3.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class NMSAPIServerLevelImpl implements NMSAPIServerLevel {
    private final ServerLevel level;

    public NMSAPIServerLevelImpl(ServerLevel level) {
        this.level = level;
    }

    @Override
    public Entity getNearestEntity(List<? extends NMSAPILivingEntity> entityList, TargetingConditions targetPredicate, @Nullable LivingEntity entity, double x, double y, double z) {
        List<? extends net.minecraft.world.entity.LivingEntity> nmsEntities = entityList.stream().map(e -> ((CraftLivingEntity) e.getBukkitLivingEntity()).getHandle()).toList();
        net.minecraft.world.entity.LivingEntity nmsEntity = null;
        if (entity != null) {
            nmsEntity = ((CraftLivingEntity) entity).getHandle();
        }
        net.minecraft.world.entity.Entity result = level.getNearestEntity(nmsEntities, ObjectConverter.toNmsTC(targetPredicate), nmsEntity, x, y, z);
        if (result == null) {
            return null;
        } else {
            return result.getBukkitEntity();
        }
    }

    @Override
    public NMSAPIPlayer getNearestPlayer(double x, double y, double z, double maxDistance, @Nullable Predicate<Entity> targetPredicate) {
        Player player = level.getNearestPlayer(x, y, z, maxDistance, e -> {
            if (targetPredicate != null) {
                return targetPredicate.test(e.getBukkitEntity());
            }
            return true;
        });
        return player == null ? null : new NMSAPIPlayerImpl((CraftPlayer) player.getBukkitEntity());
    }

    @Override
    public NMSAPIPlayer getNearestPlayer(TargetingConditions targetPredicate, LivingEntity entity, double x, double y, double z) {
        Player player = level.getNearestPlayer(ObjectConverter.toNmsTC(targetPredicate), ((CraftLivingEntity) entity).getHandle(), x, y, z);
        if (player == null) {
            return null;
        } else {
            return new NMSAPIPlayerImpl((CraftPlayer) player.getBukkitEntity());
        }
    }

    @Override
    public <T extends Entity> List<NMSAPIEntity> getEntitiesOfClass(Class<T> entityClass, BoundingBox box, Predicate<T> predicate) {
        List<? extends net.minecraft.world.entity.Entity> entities = level.getEntitiesOfClass(ObjectConverter.ToNmsEntityClass(CraftEntityTypes.getEntityTypeData(entityClass).entityType()), ObjectConverter.toNotchAABB(box), e -> predicate.test((T) e.getBukkitEntity()));
        return entities.stream().map(e -> NMSAPIImpl.getEntity(e.getBukkitEntity())).toList();
    }

    @Override
    public NMSAPIRandomSource getRandomSource() {
        return new NMSAPIRandomSourceImpl(level.getRandom());
    }
}
