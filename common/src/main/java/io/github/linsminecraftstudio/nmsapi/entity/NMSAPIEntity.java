package io.github.linsminecraftstudio.nmsapi.entity;

import io.github.linsminecraftstudio.nmsapi.enums.MoverType;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.joml.Vector3f;

public interface NMSAPIEntity {
    void move(MoverType moverType, Vector3f movement);

    void setBoundingBox(BoundingBox boundingBox);

    Entity getBukkitEntity();
}
