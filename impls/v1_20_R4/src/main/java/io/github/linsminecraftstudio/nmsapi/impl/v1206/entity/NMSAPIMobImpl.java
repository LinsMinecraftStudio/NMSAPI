package io.github.linsminecraftstudio.nmsapi.impl.v1206.entity;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIMob;
import io.github.linsminecraftstudio.nmsapi.entity.ai.goal.EntityGoal;
import io.github.linsminecraftstudio.nmsapi.impl.v1206.object.ObjectConverter;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftMob;
import org.bukkit.entity.Entity;

import java.util.function.Predicate;

public class NMSAPIMobImpl extends NMSAPILivingEntityImpl implements NMSAPIMob {
    private final CraftMob craftMob;

    public NMSAPIMobImpl(CraftMob mob) {
        super(mob);

        this.craftMob = mob;
    }

    @Override
    public void addGoal(int priority, EntityGoal goal) {
        craftMob.getHandle().goalSelector.addGoal(priority, new NMSAPIWrappedGoal(goal));
    }

    @Override
    public void removeAllGoals(Predicate<EntityGoal> predicate) {
        GoalSelector selector = craftMob.getHandle().goalSelector;
        selector.removeAllGoals(goal -> {
            if (goal instanceof NMSAPIWrappedGoal goal1) {
                return predicate.test(goal1.getEntityGoal());
            } else {
                return predicate.test(ObjectConverter.toEntityGoal(goal));
            }
        });
    }

    @Override
    public boolean hasLineOfSensingSight(Entity target) {
        return craftMob.getHandle().getSensing().hasLineOfSight(((CraftEntity) target).getHandle());
    }
}
