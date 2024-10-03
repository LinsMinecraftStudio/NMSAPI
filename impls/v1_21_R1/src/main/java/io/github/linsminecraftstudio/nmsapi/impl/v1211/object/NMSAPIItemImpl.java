package io.github.linsminecraftstudio.nmsapi.impl.v1211.object;

import io.github.linsminecraftstudio.nmsapi.item.NMSAPIItem;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NMSAPIItemImpl implements NMSAPIItem {
    private final net.minecraft.world.item.ItemStack nmsItemStack;

    public NMSAPIItemImpl(ItemStack itemStack) {
        this.nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    }

    @Override
    public ItemStack getBukkitItemStack() {
        return nmsItemStack.asBukkitMirror();
    }
}
