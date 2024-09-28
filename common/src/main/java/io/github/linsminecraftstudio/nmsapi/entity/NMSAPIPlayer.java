package io.github.linsminecraftstudio.nmsapi.entity;

import org.bukkit.entity.Player;

public interface NMSAPIPlayer extends NMSAPILivingEntity {
    Player getBukkitPlayer();

    boolean canEat(boolean ignoreHunger);
}
