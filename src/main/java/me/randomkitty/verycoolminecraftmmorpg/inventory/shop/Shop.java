package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop implements CustomInventory {

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

    public static Shop deserialize(YamlConfiguration configuration) {
        String name = configuration.getName();

        ConfigurationSection entriesSection = configuration.getConfigurationSection("entries");

        if (entriesSection != null) {
            List<ShopEntry> entries = new ArrayList<>();
            for (String s : entriesSection.getKeys(false)) {
                entries.add(entriesSection.getSerializable(s, ShopEntry.class));
            }

            return new Shop(name, entries);
        } else {
            return new Shop(name, new ArrayList<>());
        }
    }

    public static Shop fromFile(File file) {
        if (file.exists()) {
            return deserialize(YamlConfiguration.loadConfiguration(file));
        } else {
            return null;
        }
    }


    public List<ShopEntry> entries;
    public String name;

    private ItemStack[] contents = new ItemStack[9 * 6];

    public Shop(String name, List<ShopEntry> shopEntries) {
        this.entries = shopEntries;
        this.name = name;
        generateContents();
    }

    public void generateContents() {
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
        Inventory inventory = Bukkit.createInventory(this, 9 * 6, name);
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

    public void saveToFile(File file) {
        YamlConfiguration configuration = new YamlConfiguration();

        configuration.set("name", name);

        for (ShopEntry entry : entries) {
            configuration.set("entries." + entry.key, entry.serialize());
        }

        try {
            configuration.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save shop");
            e.printStackTrace();
        }

    }
}
