package me.randomkitty.verycoolminecraftmmorpg.skills.gathering;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ResourceNodeConfig {

    private final File configFile;
    private final YamlConfiguration config;

    public ResourceNodeConfig (File file) {
        this.configFile = file;
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void load() {



    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            VeryCoolMinecraftMMORPG.LOGGER.severe("Failed to save resource nodes");
        }
    }

}
