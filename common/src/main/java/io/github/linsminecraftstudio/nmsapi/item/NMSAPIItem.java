package io.github.linsminecraftstudio.nmsapi.item;

import org.bukkit.inventory.ItemStack;

public interface NMSAPIItem {
    int hashCode();

    ItemStack getBukkitItemStack();
}
