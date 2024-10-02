package io.github.linsminecraftstudio.nmsapi.entity.ai.goal;

import io.github.linsminecraftstudio.nmsapi.NMSAPI;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIMob;
import org.bukkit.entity.Mob;

import java.util.EnumSet;

public class JumpGoal extends EntityGoal {
    private final NMSAPIMob wrappedMob;

    public JumpGoal(Mob mob) {
        this.wrappedMob = (NMSAPIMob) NMSAPI.getEntity(mob);

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return wrappedMob.getEntityRandomSource().nextInt(10) == 0;
    }
}
