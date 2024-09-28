package io.github.linsminecraftstudio.nmsapi.block;

import org.bukkit.block.BlockState;

public interface NMSAPIFluidState {
    boolean isSource();

    boolean isEmpty();

    float getHeight();

    float getOwnHeight();

    int getAmount();

    BlockState createBlockState();
}
