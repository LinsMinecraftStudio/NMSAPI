package io.github.linsminecraftstudio.nmsapi.impl.v1206.entity;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPILivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class NMSAPILivingEntityImpl extends NMSAPIEntityImpl implements NMSAPILivingEntity {
    private final CraftLivingEntity craftLivingEntity;

    public NMSAPILivingEntityImpl(CraftLivingEntity entity) {
        super(entity);

        this.craftLivingEntity = entity;
    }

    @Override
    public double getVisibilityPercent(@Nullable Entity entity) {
        net.minecraft.world.entity.Entity nmsEntity = null;
        if (entity != null) {
            nmsEntity = ((CraftEntity) entity).getHandle();
        }
        return craftLivingEntity.getHandle().getVisibilityPercent(nmsEntity);
    }

    @Override
    public LivingEntity getBukkitLivingEntity() {
        return craftLivingEntity;
    }

    @Override
    public void animateHurt(float yaw) {
        craftLivingEntity.getHandle().animateHurt(yaw);
    }

    @Override
    public void jumpFromGround() {
        throw new UnsupportedOperationException("Only usable in players!");
    }

    @Override
    public boolean canBeSeenByAnyone() {
        return craftLivingEntity.getHandle().canBeSeenByAnyone();
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return craftLivingEntity.getHandle().canBeSeenAsEnemy();
    }

    @Override
    public boolean canAttackType(EntityType entityType) {
        return craftLivingEntity.getHandle().canAttackType(CraftEntityType.bukkitToMinecraft(entityType));
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        net.minecraft.world.entity.LivingEntity handle = ((CraftLivingEntity) entity).getHandle();
        return craftLivingEntity.getHandle().canAttack(handle);
    }

    @Override
    public double getFollowRange() {
        AttributeInstance attributeinstance = craftLivingEntity.getHandle().getAttribute(Attributes.FOLLOW_RANGE);
        return attributeinstance == null ? 16.0 : attributeinstance.getValue();
    }
}
