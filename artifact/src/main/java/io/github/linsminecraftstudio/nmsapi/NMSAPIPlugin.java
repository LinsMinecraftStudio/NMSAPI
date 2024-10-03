package io.github.linsminecraftstudio.nmsapi;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class NMSAPIPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("NMSAPI is enabled!");
        checkUpdate();
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
        CompletableFuture<HttpResponse<String>> ver = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            ver.whenCompleteAsync((h, e) -> {
               if (e != null) {
                   getLogger().log(Level.SEVERE, "Failed to check NMSAPI updates", e);
               } else {
                   String latest = h.body();
                   if (!this.getPluginMeta().getVersion().equalsIgnoreCase(latest)) {
                       getLogger().warning("A new version of NMSAPI is available! You are running version " + this.getPluginMeta().getVersion() + " and the latest version is " + latest + ".");
                       getLogger().warning("Download it at https://hangar.papermc.io/lijinhong11/NMS-API/versions");
                   } else {
                       getLogger().info("NMSAPI is up to date!");
                   }
               }
            }).get();
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to check NMSAPI updates", e);
        }
    }
}
