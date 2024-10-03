package io.github.linsminecraftstudio.nmsapi.impl.v1211.entity;

import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class NMSAPIPlayerImpl extends NMSAPILivingEntityImpl implements NMSAPIPlayer {
    private final CraftPlayer player;

    public NMSAPIPlayerImpl(CraftPlayer player) {
        super(player);

        this.player = player;
    }

    public CraftPlayer getBukkitPlayer() {
        return player;
    }

    @Override
    public boolean canEat(boolean ignoreHunger) {
        return player.getHandle().canEat(ignoreHunger);
    }

    @Override
    public void jumpFromGround() {
        player.getHandle().jumpFromGround();
    }
}
