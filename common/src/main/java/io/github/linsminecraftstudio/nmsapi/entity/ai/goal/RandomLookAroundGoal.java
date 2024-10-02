package io.github.linsminecraftstudio.nmsapi.entity.ai.goal;

import io.github.linsminecraftstudio.nmsapi.NMSAPI;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIMob;
import org.bukkit.entity.Mob;

import java.util.EnumSet;

public class RandomLookAroundGoal extends EntityGoal {
    private final Mob mob;
    private final NMSAPIMob wrappedMob;

    private double relX;
    private double relZ;
    private int lookTime;

    public RandomLookAroundGoal(Mob mob) {
        this.mob = mob;
        this.wrappedMob = (NMSAPIMob) NMSAPI.getEntity(mob);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.wrappedMob.getEntityRandomSource().nextFloat() < 0.02F;
    }

    @Override
    public boolean canContinueToUse() {
        return this.lookTime >= 0;
    }

    @Override
    public void start() {
        double d = 6.283185307179586 * this.wrappedMob.getEntityRandomSource().nextDouble();
        this.relX = Math.cos(d);
        this.relZ = Math.sin(d);
        this.lookTime = 20 + this.wrappedMob.getEntityRandomSource().nextInt(20);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        --this.lookTime;
        this.mob.lookAt(this.mob.getX() + this.relX, this.wrappedMob.getEyeY(), this.mob.getZ() + this.relZ);
    }
}
