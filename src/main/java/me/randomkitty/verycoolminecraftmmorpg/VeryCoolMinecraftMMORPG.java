package me.randomkitty.verycoolminecraftmmorpg;

import me.randomkitty.verycoolminecraftmmorpg.commands.*;
import me.randomkitty.verycoolminecraftmmorpg.config.RpgConfig;
import me.randomkitty.verycoolminecraftmmorpg.entities.spawners.CreatureSpawners;
import me.randomkitty.verycoolminecraftmmorpg.events.*;
import me.randomkitty.verycoolminecraftmmorpg.inventory.shop.Shops;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerData;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

public final class VeryCoolMinecraftMMORPG extends JavaPlugin {

    public static final String NAMESPACE = "coolrpg";
    public static VeryCoolMinecraftMMORPG INSTANCE;
    public static Logger LOGGER;
    public static RpgConfig CONFIG;
    public static PlayerAdapter<Player> RANK_PROVIDER;

    @Override
    public void onEnable() {

        INSTANCE = this;
        LOGGER = this.getLogger();
        this.saveResource("config.yml", false);
        CONFIG = new RpgConfig(new File(this.getDataFolder(), "config.yml"));
        RANK_PROVIDER = LuckPermsProvider.get().getPlayerAdapter(Player.class);

        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(new ConnectionEvents(), this);
        manager.registerEvents(new DamageEvents(), this);
        manager.registerEvents(new EntityEvents(), this);
        manager.registerEvents(new InteractEvents(), this);
        manager.registerEvents(new InventoryEvents(), this);
        manager.registerEvents(new PlayerEvents(), this);
        manager.registerEvents(new CommandAndChatEvents(), this);
        manager.registerEvents(new ExplosiveEvents(), this);

        this.getCommand("shop").setExecutor(new ShopCommand());
        this.getCommand("openshop").setExecutor(new OpenShopForPlayerCommand());
        this.getCommand("spawncustomentity").setExecutor(new SpawnCustomEntityCommand());
        this.getCommand("givecustomitem").setExecutor(new CustomItemCommand());
        this.getCommand("modify").setExecutor(new AttributeModifierCommand());
        this.getCommand("fixitem").setExecutor(new FixItemCommand());
        this.getCommand("givebook").setExecutor(new EnchantedBookCommand());
        this.getCommand("loadspawners").setExecutor(new LoadSpawnersCommand());
        this.getCommand("loadconfig").setExecutor(new LoadConfigCommand());
        this.getCommand("conditionaltp").setExecutor(new ConditionalTpCommand());

        //CustomEntityType.register();

        Shops.loadAllShops();
        PlayerData.startAutoSave();
        PlayerAttributes.startUpdateAttributesTask();
        CreatureSpawners.init();
    }

    @Override
    public void onDisable() {
        CreatureSpawners.stopSpawnMobsTask();
        PlayerAttributes.stopDisplayPlayerAttributesTask();
        PlayerData.stopAutoSave();
        PlayerData.saveAll();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LOGGER.info("label");

        if (label.equals("kill") && Arrays.asList(args).contains("@e")) {
            sender.sendMessage(Component.text("Do not /kill @e"));
            return false;
        }

        return true;
    }
}
