package io.github.linsminecraftstudio.nmsapi.impl.v1206;

import io.github.linsminecraftstudio.nmsapi.NMSAPI;
import io.github.linsminecraftstudio.nmsapi.block.NMSAPIBlock;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.impl.v1206.entity.NMSAPIEntityImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1206.entity.NMSAPILivingEntityImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1206.entity.NMSAPIMobImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1206.entity.NMSAPIPlayerImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1206.object.NMSAPIBlockImpl;
import io.github.linsminecraftstudio.nmsapi.impl.v1206.object.NMSAPIItemImpl;
import io.github.linsminecraftstudio.nmsapi.item.NMSAPIItem;
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftMob;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings({"deprecation", "removal"})
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
        if (bukkitEntity instanceof CraftPlayer player) {
            return new NMSAPIPlayerImpl(player);
        } else if (bukkitEntity instanceof CraftMob mob) {
            return new NMSAPIMobImpl(mob);
        } else if (bukkitEntity instanceof CraftLivingEntity livingEntity) {
            return new NMSAPILivingEntityImpl(livingEntity);
        } else {
            return new NMSAPIEntityImpl((CraftEntity) bukkitEntity);
        }
    }

    @Override
    public double[] getServerTpsImpl() {
        return MinecraftServer.getServer().recentTps;
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
