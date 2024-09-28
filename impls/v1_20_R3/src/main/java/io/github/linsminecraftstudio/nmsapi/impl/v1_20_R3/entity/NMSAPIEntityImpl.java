package io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.entity;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.enums.MoverType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.joml.Vector3f;

public class NMSAPIEntityImpl implements NMSAPIEntity {
    private final CraftEntity entity;

    public NMSAPIEntityImpl(CraftEntity entity) {
        this.entity = entity;
    }

    @Override
    public void move(MoverType moverType, Vector3f movement) {
        this.entity.getHandle().move(toNotchMoverType(moverType), new Vec3(movement));
    }

    @Override
    public void setBoundingBox(BoundingBox boundingBox) {
        this.entity.getHandle().setBoundingBox(toNotchAABB(boundingBox));
    }

    @Override
    public Entity getBukkitEntity() {
        return entity;
    }

    private AABB toNotchAABB(BoundingBox boundingBox) {
        return new AABB(
                boundingBox.getMinX(),
                boundingBox.getMinY(),
                boundingBox.getMinZ(),
                boundingBox.getMaxX(),
                boundingBox.getMaxY(),
                boundingBox.getMaxZ()
        );
    }

    private net.minecraft.world.entity.MoverType toNotchMoverType(MoverType moverType) {
        return switch (moverType) {
            case PLAYER -> net.minecraft.world.entity.MoverType.PLAYER;
            case PISTON -> net.minecraft.world.entity.MoverType.PISTON;
            case SELF -> net.minecraft.world.entity.MoverType.SELF;
            case SHULKER -> net.minecraft.world.entity.MoverType.SHULKER;
            case SHULKER_BOX -> net.minecraft.world.entity.MoverType.SHULKER_BOX;
        };
    }
}
