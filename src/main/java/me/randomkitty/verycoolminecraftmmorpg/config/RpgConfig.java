package me.randomkitty.verycoolminecraftmmorpg.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class RpgConfig {

    private final File configFile;
    private final YamlConfiguration config;

    public RpgConfig(File file) {
        this.configFile = file;
        this.config = YamlConfiguration.loadConfiguration(configFile);
        load();
    }

    public void load() {

    }
}
