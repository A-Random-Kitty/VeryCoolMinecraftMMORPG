package me.randomkitty.verycoolminecraftmmorpg.player.data;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.UUID;

public abstract class PlayerDataValue {

    public abstract String getKey();

    protected abstract void save(UUID uuid, YamlConfiguration data);
    protected abstract void load(UUID uuid, YamlConfiguration data);
    protected abstract void unload(UUID uuid);
}
