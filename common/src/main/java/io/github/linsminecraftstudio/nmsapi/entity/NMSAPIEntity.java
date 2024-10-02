package io.github.linsminecraftstudio.nmsapi.entity;

import io.github.linsminecraftstudio.nmsapi.enums.MoverType;
import io.github.linsminecraftstudio.nmsapi.object.NMSAPIRandomSource;
import io.github.linsminecraftstudio.nmsapi.object.NMSAPIServerLevel;
import io.github.linsminecraftstudio.nmsapi.object.NMSBoundingBoxImpl;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.joml.Vector3f;

public interface NMSAPIEntity {
    void move(MoverType moverType, Vector3f movement);

    Entity getBukkitEntity();

    boolean isAlliedTo(Entity entity);

    NMSAPIRandomSource getEntityRandomSource();

    NMSBoundingBoxImpl getBoundingBox();

    void setBoundingBox(BoundingBox boundingBox);

    double getEyeY();

    NMSAPIServerLevel level();

    default NMSAPIServerLevel getServerLevel() {
        return level();
    }

    default boolean isAlliedTo(NMSAPIEntity entity) {
        return isAlliedTo(entity.getBukkitEntity());
    }
}
