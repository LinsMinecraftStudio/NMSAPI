package io.github.linsminecraftstudio.nmsapi.impl.v1_20_R3;

import io.github.linsminecraftstudio.nmsapi.NMSAPI;
import io.github.linsminecraftstudio.nmsapi.block.NMSAPIBlock;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.item.NMSAPIItem;
import net.minecraft.SharedConstants;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class NMSAPIImpl extends NMSAPI {
    @Override
    public NMSAPIBlock getBlockImpl(Block block) {
        return new NMSAPIBlockImpl(block);
    }

    @Override
    public NMSAPIBlock getBlockImpl(World world, int x, int y, int z) {
        return new NMSAPIBlockImpl(world.getBlockAt(x, y, z));
    }

    @Override
    public NMSAPIItem getItemImpl(ItemStack itemStack) {
        return new NMSAPIItemImpl(itemStack);
    }

    @Override
    public NMSAPIEntity getEntityImpl(Entity bukkitEntity) {
        return null;
    }

    @Override
    public String getSharedConstantsGameVersion() {
        return SharedConstants.VERSION_STRING;
    }

    @Override
    public int getSharedConstantsProtocolVersion() {
        return SharedConstants.RELEASE_NETWORK_PROTOCOL_VERSION;
    }
}
