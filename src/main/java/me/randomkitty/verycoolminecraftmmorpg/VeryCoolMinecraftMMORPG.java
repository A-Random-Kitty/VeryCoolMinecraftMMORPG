package me.randomkitty.verycoolminecraftmmorpg;

import me.randomkitty.verycoolminecraftmmorpg.commands.CustomItemCommand;
import me.randomkitty.verycoolminecraftmmorpg.commands.ShopCommand;
import me.randomkitty.verycoolminecraftmmorpg.commands.TestCommand;
import me.randomkitty.verycoolminecraftmmorpg.config.RpgConfig;
import me.randomkitty.verycoolminecraftmmorpg.events.DamageEvents;
import me.randomkitty.verycoolminecraftmmorpg.events.ConnectionEvents;
import me.randomkitty.verycoolminecraftmmorpg.events.InteractEvents;
import me.randomkitty.verycoolminecraftmmorpg.events.InventoryEvents;
import me.randomkitty.verycoolminecraftmmorpg.inventory.shop.Shops;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerData;
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

        manager.registerEvents(new ConnectionEvents(), this);
        manager.registerEvents(new DamageEvents(), this);
        manager.registerEvents(new InteractEvents(), this);
        manager.registerEvents(new InventoryEvents(), this);

        this.getCommand("shop").setExecutor(new ShopCommand());
        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("givecustomitem").setExecutor(new CustomItemCommand());

        //CustomEntityType.register();

        Shops.loadAllShops();
        PlayerData.startAutoSave();
        PlayerAttributes.startDisplayAttributesTask();
    }

    @Override
    public void onDisable() {
        PlayerAttributes.stopDisplayPlayerAttributesTask();
        PlayerData.stopAutoSave();
        PlayerData.saveAll();
    }
}
