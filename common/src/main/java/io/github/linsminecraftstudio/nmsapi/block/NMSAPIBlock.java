package io.github.linsminecraftstudio.nmsapi.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public interface NMSAPIBlock {
    boolean isAir();

    NMSAPIBlock getRelative(int x, int y, int z);

    NMSAPIBlock getRelative(BlockFace face);

    Block getBukkitBlock();

    /**
     * Apply bone meal to the block.
     * @param face the face to apply bone meal to.
     * @return true if bone meal was consumed, false otherwise.
     */
    boolean applyBoneMeal(BlockFace face);

    NMSAPIFluidState getFluidState();
}
