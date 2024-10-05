package io.github.linsminecraftstudio.nmsapi;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;

public class NMSAPIPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("NMSAPI is enabled!");
        getServer().getScheduler().runTaskTimerAsynchronously(this, this::checkUpdate, 0, 20 * 60 * 60 * 24); // Check for updates every 24 hours
    }

    @Override
    public void onDisable() {
        getLogger().info("NMSAPI is disabled!");
    }

    private void checkUpdate() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://hangar.papermc.io/api/v1/projects/nms-api/latestrelease"))
                .GET()
                .build();
        try {
            String current = this.getPluginMeta().getVersion();
            HttpResponse<String> ver = client.send(request, HttpResponse.BodyHandlers.ofString());
            String latest = ver.body();
            if (!current.equalsIgnoreCase(latest)) {
                getLogger().warning("A new version of NMSAPI is available! You are running version " + this.getPluginMeta().getVersion() + " and the latest version is " + latest + ".");
                getLogger().warning("Download it at https://hangar.papermc.io/lijinhong11/NMS-API/versions");
            } else {
                if (current.contains("SNAPSHOT")) {
                    HttpRequest request2 = HttpRequest.newBuilder()
                            .uri(URI.create("https://hangar.papermc.io/api/v1/projects/nms-api/latest?channel=Snapshot"))
                            .GET()
                            .build();
                    HttpResponse<String> ver2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
                    String latest2 = ver2.body();
                    if (!latest2.equalsIgnoreCase(latest)) {
                        getLogger().warning("""
                                NMSAPI update available! You are running a snapshot version of NMSAPI.
                                The latest snapshot version is %s.
                                The latest release version is %s.
                                
                                When you are ready to update, please switch to the release version.
                                DON'T use the snapshot version unless you are willing to risk bugs or instability.
                                
                                Download it at https://hangar.papermc.io/lijinhong11/NMS-API/versions
                                """);
                    } else {
                        getLogger().warning("You are running a snapshot version of NMSAPI. Proceed with caution.");
                        getLogger().info("NMSAPI is up to date!");
                    }
                } else {
                    getLogger().info("NMSAPI is up to date!");
                }
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to check NMSAPI updates", e);
        }
    }
}
