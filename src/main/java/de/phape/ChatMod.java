package de.phape;

import java.io.File;

import de.phape.EventListeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatMod extends JavaPlugin {

    File configFile = new File(getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    private PlayerJoinListener playerJoinListener;

    @Override
    public void onEnable() {
        // This method is called when the plugin is enabled
        Bukkit.getLogger().info(config.getString("enabled-message"));

        playerJoinListener = new PlayerJoinListener();
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
    }

    @Override
    public void onDisable() {
        // This method is called when the plugin is disabled
        Bukkit.getLogger().info(config.getString("disabled-message"));
        HandlerList.unregisterAll(playerJoinListener);
    }

}
