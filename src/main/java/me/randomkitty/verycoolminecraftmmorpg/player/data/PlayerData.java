package me.randomkitty.verycoolminecraftmmorpg.player.data;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerCurrency;
import me.randomkitty.verycoolminecraftmmorpg.skills.combat.CombatSkill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerData {

    private final static File playerDataFolder = new File(VeryCoolMinecraftMMORPG.INSTANCE.getDataFolder(), "playerdata");

    private final static Map<Player, PlayerData> playerDataMap = new HashMap<>();

    public static void initPlayer(Player player) {
        playerDataMap.put(player, new PlayerData(player));
    }
    public static void removePlayer(Player player) {

        PlayerData d = playerDataMap.get(player);

        for (PlayerDataValue value : d.dataValues) {
            value.unload(player.getUniqueId());
        }

        playerDataMap.remove(player);
    }
    public static void savePlayer(Player player) { playerDataMap.get(player).save(); }
    public static PlayerData getAttributes(Player player) { return playerDataMap.get(player); }

    private static BukkitTask autoSaveTask;

    public static void startAutoSave() {
        autoSaveTask =  Bukkit.getScheduler().runTaskTimerAsynchronously(VeryCoolMinecraftMMORPG.INSTANCE, new Runnable() {

            @Override
            public void run() {
                saveAll();
            }
        }, 20 * 60 * 5, 20 * 60 * 5);
    }

    public static void stopAutoSave() {
        autoSaveTask.cancel();
    }

    public static void saveAll() {
        for (PlayerData data : playerDataMap.values()) {
            data.save();
        }
    }

    private final UUID uuid;

    private final File dataFile;
    private final YamlConfiguration data;

    private final Set<PlayerDataValue> dataValues = new HashSet<>();

    private PlayerData (Player player) {
        this.uuid = player.getUniqueId();
        this.dataFile = new File(playerDataFolder, uuid + ".yml");

        if (dataFile.exists()) {
            this.data = YamlConfiguration.loadConfiguration(dataFile);
        } else {
            try {
                playerDataFolder.mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                VeryCoolMinecraftMMORPG.LOGGER.severe("Failed to create data file for player with uuid: " + uuid);
                player.kick(Component.text("Failed to create data file").color(NamedTextColor.RED));
            }

            this.data = new YamlConfiguration();


        }

        {
            // Add Data Values
            dataValues.add(new PlayerCurrency(player));
            dataValues.add(new CombatSkill(player));
        }

        for (PlayerDataValue value : dataValues) {
            value.load(uuid, data);
        }
    }

    public void save() {

        for (PlayerDataValue value : dataValues) {
            value.save(uuid, data);
        }

        try {
            data.save(dataFile);
        } catch (IOException e) {
            VeryCoolMinecraftMMORPG.LOGGER.severe("Failed to save data for player with uuid: " + uuid);
        }
    }
}
