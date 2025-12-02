package me.randomkitty.verycoolminecraftmmorpg;

import me.randomkitty.verycoolminecraftmmorpg.commands.TestCommand;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityType;
import me.randomkitty.verycoolminecraftmmorpg.events.GameEvents;
import me.randomkitty.verycoolminecraftmmorpg.events.MainEvents;
import org.bukkit.Bukkit;
import org.bukkit.GameEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class VeryCoolMinecraftMMORPG extends JavaPlugin {

    public static final String NAMESPACE = "coolrpg";

    @Override
    public void onEnable() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new GameEvents(), this);
        manager.registerEvents(new MainEvents(), this);

        this.getCommand("test").setExecutor(new TestCommand());

        //CustomEntityType.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
