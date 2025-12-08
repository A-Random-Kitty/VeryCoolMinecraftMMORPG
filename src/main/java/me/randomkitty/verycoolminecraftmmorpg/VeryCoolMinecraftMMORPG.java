package me.randomkitty.verycoolminecraftmmorpg;

import me.randomkitty.verycoolminecraftmmorpg.commands.CustomItemCommand;
import me.randomkitty.verycoolminecraftmmorpg.commands.TestCommand;
import me.randomkitty.verycoolminecraftmmorpg.config.RpgConfig;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityType;
import me.randomkitty.verycoolminecraftmmorpg.events.GameEvents;
import me.randomkitty.verycoolminecraftmmorpg.events.MainEvents;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class VeryCoolMinecraftMMORPG extends JavaPlugin {

    public static final String NAMESPACE = "coolrpg";
    public static VeryCoolMinecraftMMORPG INSTANCE;
    public static RpgConfig CONFIG;

    @Override
    public void onEnable() {

        INSTANCE = this;
        this.saveResource("config.yml", false);
        CONFIG = new RpgConfig(new File(this.getDataFolder(), "config.yml"));


        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new GameEvents(), this);
        manager.registerEvents(new MainEvents(), this);

        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("givecustomitem").setExecutor(new CustomItemCommand());

        //CustomEntityType.register();

        PlayerData.startAutoSave();
    }

    @Override
    public void onDisable() {
        PlayerData.stopAutoSave();
        PlayerData.saveAll();
    }
}
