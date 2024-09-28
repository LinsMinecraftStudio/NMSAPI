package io.github.linsminecraftstudio.nmsapi;

import io.github.linsminecraftstudio.nmsapi.block.NMSAPIBlock;
import io.github.linsminecraftstudio.nmsapi.entity.NMSAPIEntity;
import io.github.linsminecraftstudio.nmsapi.item.NMSAPIItem;
import jdk.internal.loader.BootLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public abstract class NMSAPI {
    private static final NMSAPI impl;

    protected NMSAPI() {}

    static {
        try {
            String mcVersion = Bukkit.getBukkitVersion().split("-")[0];
            int mcvInt = Integer.parseInt(mcVersion.replaceAll("\\.", ""));

            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

            String clazzName = "io.github.linsminecraftstudio.nmsapi.impl." + version + ".NMSAPIImpl";
            if (BootLoader.getDefinedPackage("com.destroystokyo.paper") != null) {
                if (mcvInt >= 1205) {
                    mcvInt = getFallbackIntMCVersionIfNotFound(mcvInt);
                    clazzName = "io.github.linsminecraftstudio.nmsapi.impl." + mcvInt + ".NMSAPIImpl";
                }
            }

            Class<?> clazz = Class.forName(clazzName);
            impl = (NMSAPI) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize NMSAPI, the server mc version is not supported", e);
        }
    }

    private static int getFallbackIntMCVersionIfNotFound(int current) {
        String mcVersion = Bukkit.getBukkitVersion().split("-")[0];
        int mcvInt = Integer.parseInt(mcVersion.replaceAll("\\.", ""));
        if (mcvInt != current) {
            mcvInt = current;
        }
        try {
            String num = "v" + mcvInt;
            String clazzName = "io.github.linsminecraftstudio.nmsapi.impl." + num + ".NMSAPIImpl";
            Class.forName(clazzName);
            return mcvInt;
        } catch (ClassNotFoundException e) {
            return getFallbackIntMCVersionIfNotFound(mcvInt - 1);
        }
    }

    public static NMSAPIBlock getBlock(Location location) {
        return getBlock(location.getBlock());
    }

    public static NMSAPIBlock getBlock(World world, int x, int y, int z) {
        return impl.getBlockImpl(world, x, y, z);
    }

    public static NMSAPIBlock getBlock(Block block) {
        return impl.getBlockImpl(block);
    }

    public static NMSAPIItem getItem(ItemStack itemStack) {
        return impl.getItemImpl(itemStack);
    }

    public static String getMinecraftVersionDirectly() {
        return impl.getSharedConstantsGameVersion();
    }

    public static int getProtocolVersion() {
        return impl.getSharedConstantsProtocolVersion();
    }

    public static NMSAPIEntity getEntity(Entity bukkitEntity) {
        return impl.getEntityImpl(bukkitEntity);
    }

    // Abstract methods

    public abstract NMSAPIBlock getBlockImpl(Block block);

    public abstract NMSAPIBlock getBlockImpl(World world, int x, int y, int z);

    public abstract NMSAPIItem getItemImpl(ItemStack itemStack);

    public abstract NMSAPIEntity getEntityImpl(Entity bukkitEntity);

    public abstract String getSharedConstantsGameVersion();

    public abstract int getSharedConstantsProtocolVersion();
}
