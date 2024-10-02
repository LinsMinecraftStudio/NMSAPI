package io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.entity;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.enums.MoverType;
import io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.entity.random.NMSAPIRandomSourceImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.object.NMSAPIServerLevelImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3.object.ObjectConverter;
import io.github.linsminecraftstudio.nmsapi.object.NMSAPIRandomSource;
import io.github.linsminecraftstudio.nmsapi.object.NMSAPIServerLevel;
import io.github.linsminecraftstudio.nmsapi.object.NMSBoundingBoxImpl;
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
    public Entity getBukkitEntity() {
        return entity;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        return this.entity.getHandle().isAlliedTo(((CraftEntity) entity).getHandle());
    }

    @Override
    public NMSAPIRandomSource getEntityRandomSource() {
        return new NMSAPIRandomSourceImpl(this.entity.getHandle().random);
    }

    @Override
    public NMSBoundingBoxImpl getBoundingBox() {
        return new NMSBoundingBoxImpl(this.entity.getBoundingBox());
    }

    @Override
    public void setBoundingBox(BoundingBox boundingBox) {
        this.entity.getHandle().setBoundingBox(ObjectConverter.toNotchAABB(boundingBox));
    }

    @Override
    public double getEyeY() {
        return this.entity.getHandle().getEyeY();
    }

    @Override
    public NMSAPIServerLevel level() {
        return new NMSAPIServerLevelImpl(this.entity.getHandle().level().getMinecraftWorld());
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
