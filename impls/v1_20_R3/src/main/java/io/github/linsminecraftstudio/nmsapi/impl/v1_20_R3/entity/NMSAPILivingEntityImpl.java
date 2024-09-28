package io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.entity;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPILivingEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
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
    public void animateHurt(float yaw) {
        craftLivingEntity.getHandle().animateHurt(yaw);
    }

    @Override
    public void jumpFromGround() {
        throw new UnsupportedOperationException("Only usable in players!");
    }
}
