package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shops {


    private final static File shopsFolder = new File(VeryCoolMinecraftMMORPG.INSTANCE.getDataFolder(), "shops");

    private final static Map<String, Shop> shops = new HashMap<>();

    public static void openShop(Player player, String string) {
        Shop shop = shops.get(string);

        if (shop != null) {
            player.openInventory(shop.getInventory());

        }
    }

    public static void loadAllShops() {
        if (!shops.isEmpty())
            shops.clear();

        if (shopsFolder.exists()) {
            File[] files = shopsFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (FilenameUtils.getExtension(file.getName()).equals(".yml")) {
                        Shop shop = Shop.fromFile(file);

                        if (shop != null) {
                            shops.put(FilenameUtils.removeExtension(file.getName()), shop);
                        } else {
                            Bukkit.getLogger().warning("Failed to load shop from file: " + file.getName());
                        }
                    }
                }
            }
        } else {
            shopsFolder.mkdir();
        }
    }

    public static void saveAllShops() {
        for (Map.Entry<String, Shop> entry : shops.entrySet()) {
            File file = new File(shopsFolder, entry.getKey() + ".yml");

            if (file.exists()) {
                entry.getValue().saveToFile(file);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                entry.getValue().saveToFile(file);
            }
        }
    }
}
