package io.github.linsminecraftstudio.nmsapi.impl.v1206.object;

import io.github.linsminecraftstudio.nmsapi.block.NMSAPIFluidState;
import net.minecraft.world.level.material.FluidState;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftBlockStates;

public class NMSAPIFluidStateImpl implements NMSAPIFluidState {
    private final CraftBlock block;
    private final FluidState state;

    public NMSAPIFluidStateImpl(CraftBlock block) {
        this.block = block;
        this.state = block.getNMSFluid();
    }

    @Override
    public boolean isSource() {
        return state.isSource();
    }

    @Override
    public boolean isEmpty() {
        return state.isEmpty();
    }

    @Override
    public float getHeight() {
        return state.getHeight(block.getHandle(), block.getPosition());
    }

    @Override
    public float getOwnHeight() {
        return state.getOwnHeight();
    }

    @Override
    public int getAmount() {
        return state.getAmount();
    }

    @Override
    public BlockState createBlockState() {
        return CraftBlockStates.getBlockState(state.createLegacyBlock(), null);
    }
}
