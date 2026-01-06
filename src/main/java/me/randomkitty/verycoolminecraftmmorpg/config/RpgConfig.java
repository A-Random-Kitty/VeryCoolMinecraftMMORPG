package me.randomkitty.verycoolminecraftmmorpg.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RpgConfig {

    private final File configFile;
    private YamlConfiguration config;

    private Location spawnLocation;

    public RpgConfig(File file) {
        this.configFile = file;
        load();
    }

    public void load() {
        this.config = YamlConfiguration.loadConfiguration(configFile);
        spawnLocation = config.getLocation("spawn_location");
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }
}
