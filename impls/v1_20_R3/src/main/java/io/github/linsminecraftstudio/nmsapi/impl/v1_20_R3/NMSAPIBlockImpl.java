package io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3;

import io.github.linsminecraftstudio.nmsapi.block.NMSAPIBlock;
import io.github.linsminecraftstudio.nmsapi.block.NMSAPIFluidState;
import net.minecraft.world.level.LevelAccessor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftBlock;

public class NMSAPIBlockImpl implements NMSAPIBlock {
    private final CraftBlock block;
    private final LevelAccessor level;

    public NMSAPIBlockImpl(Block block) {
        this.block = (CraftBlock) block;
        this.level = this.block.getHandle();
    }

    @Override
    public boolean isAir() {
        return level.getBlockState(block.getPosition()).isAir();
    }

    @Override
    public NMSAPIBlock getRelative(int x, int y, int z) {
        return new NMSAPIBlockImpl(block.getRelative(x, y, z));
    }

    @Override
    public NMSAPIBlock getRelative(BlockFace face) {
        return new NMSAPIBlockImpl(block.getRelative(face));
    }


    @Override
    public Block getBukkitBlock() {
        return block;
    }

    @Override
    public boolean applyBoneMeal(BlockFace face) {
        return block.applyBoneMeal(face);
    }

    @Override
    public NMSAPIFluidState getFluidState() {
        return new NMSAPIFluidStateImpl(block);
    }
}
