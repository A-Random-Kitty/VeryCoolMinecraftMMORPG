package me.randomkitty.verycoolminecraftmmorpg.player.data;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class PlayerDataValue {

    public Player player;

    public PlayerDataValue(Player player) {
        this.player = player;
    }

    public abstract String getKey();

    protected abstract void save(UUID uuid, YamlConfiguration data);
    protected abstract void load(UUID uuid, YamlConfiguration data);
    protected abstract void unload(UUID uuid);
}
