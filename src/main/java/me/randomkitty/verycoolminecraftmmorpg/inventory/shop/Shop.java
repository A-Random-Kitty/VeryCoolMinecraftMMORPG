package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop implements CustomInventory, ConfigurationSerializable {

    private final static Map<String, Shop> shops = new HashMap<>();

    private static final char[] layout = {
        '_', '_', '_', '_', '_', '_', '_', '_', '_',
        '_', '#', '#', '#', '#', '#', '#', '#', '_',
        '_', '#', '#', '#', '#', '#', '#', '#', '_',
        '_', '#', '#', '#', '#', '#', '#', '#', '_',
        '_', '#', '#', '#', '#', '#', '#', '#', '_',
        '_', '_', '_', '_', 'E', '_', '_', '_', '_',
    };

    private final List<ShopEntry> entries = new ArrayList<>();

    private final ItemStack[] contents = new ItemStack[9 * 6];

    public Shop() {

        int i = 0;

        for (ShopEntry entry : entries) {
            while (layout[i] != '#' && i != contents.length) {
                if (i == )
                i++;
            }

            if
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

    public static Shop deserialize() {

    }
}
