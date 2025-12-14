package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop implements CustomInventory, ConfigurationSerializable {

    private final static File shopsFolder = new File(VeryCoolMinecraftMMORPG.INSTANCE.getDataFolder(), "shops");

    private final static Map<String, Shop> shops = new HashMap<>();

    private static final ItemStack blankItem;

    private static final char[] layout = {
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
    };

    static {
        blankItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta m = blankItem.getItemMeta();
        m.itemName(Component.empty());
        blankItem.setItemMeta(m);
    }


    public final List<ShopEntry> entries;

    private final ItemStack[] contents = new ItemStack[9 * 6];

    public Shop(String name, List<ShopEntry> shopEntries) {
        this.entries = shopEntries;

        int i = 0;

        for (ShopEntry entry : entries) {
            while (layout[i] != '#' && i < contents.length) {
                if (layout[i] == '_') {
                    contents[i] = blankItem.clone();
                }

                i++;
            }

            if (i < contents.length) {
                contents[i] = entry.getGuiItem();
            } else {
                break;
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 9 * 6);
        inventory.setContents(contents);

        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {

    }

    @Override
    public void handleClose(InventoryCloseEvent event) {

    }

    @Override
    public void handleOpen(InventoryOpenEvent event) {

    }

    @Override
    public void handleDrag(InventoryDragEvent event) {
    }

    @Override
    public void handleMoveItem(InventoryMoveItemEvent event) {
        if (event.getSource().getHolder() == this || event.getDestination().getHolder() == this) {
            event.setCancelled(true);
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> serializedData = new HashMap<>();

        for (ShopEntry entry : entries) {
            serializedData.put("entries." + entry.key, entry.serialize());
        }

        return serializedData;
    }

    public static Shop deserialize(ConfigurationSection configuration) {
        String name = configuration.getName();

        ConfigurationSection entriesSection = configuration.getConfigurationSection("entries");

        if (entriesSection != null) {
            List<ShopEntry> entries = new ArrayList<>();
            for (String s : entriesSection.getKeys(false)) {

            }
        } else {
            return new Shop(new ArrayList<>());
        }


    }

    public static Shop fromFile(File file) {

    }
}
