package me.randomkitty.verycoolminecraftmmorpg.entities.spawners;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreatureSpawners {

    private static final List<CreatureSpawner> spawners = new ArrayList<>();

    private static File spawnersFile = new File(VeryCoolMinecraftMMORPG.INSTANCE.getDataFolder(), "spawners");

    private static int spawnMobsTask;

    public static void init() {
        loadSpawners();
        startSpawnMobsTask();
    }

    public static void loadSpawners() {
        spawners.clear();

        if (spawnersFile.exists()) {
            File[] files = spawnersFile.listFiles();

            if (files == null) {
                VeryCoolMinecraftMMORPG.LOGGER.severe("spawners file is not a directory");
                return;
            }

            for (File file : files) {
                if (FilenameUtils.getExtension(file.getName()).equals("yml")) {
                    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                    for (String key : configuration.getKeys(false)) {
                        ConfigurationSection section = configuration.getConfigurationSection(key);

                        if (section != null) {
                            CreatureSpawner<?> spawner = CreatureSpawner.fromConfiguration(section);

                            if (spawner != null) {
                                spawners.add(spawner);
                            }
                        } else {
                            VeryCoolMinecraftMMORPG.LOGGER.warning("section is null while loading spawner");
                        }
                    }
                }
            }
        } else {
            spawnersFile.mkdir();
        }

    }

    public static void startSpawnMobsTask() {
        spawnMobsTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(VeryCoolMinecraftMMORPG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for (CreatureSpawner<?> spawner : spawners) {
                    spawner.spawnMobsWithInterval();
                }
            }
        }, 200, 200);
    }

    public static void stopSpawnMobsTask() {
        Bukkit.getScheduler().cancelTask(spawnMobsTask);
    }
}
