package me.randomkitty.verycoolminecraftmmorpg.inventory.shop.editor;

import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import me.randomkitty.verycoolminecraftmmorpg.inventory.shop.ShopEntry;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

// might be finished later if I feel like it
public class ShopEntryEditor implements CustomInventory {

    private static final char[] layout = {
            'C', '_', '_', '_', '_', '_', '_', '_', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '#', '#', '#', '#', '#', '#', '#', '_',
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
    };

    private Inventory editorInventory;

    private ShopEditor parent;
    private ShopEntry entry;

    public ShopEntryEditor(ShopEntry entry) {
        this.entry = entry;
        this.editorInventory = Bukkit.createInventory(this, 9 * 6, "Shop Entry Editor");
    }

    public Map<CustomItem, Integer> getItemCost() {
        Map<CustomItem, Integer> cost = new HashMap<>();

        for (int i = 0; i < editorInventory.getSize(); i++) {
            if (layout[i] == '#') {
                ItemStack item = editorInventory.getItem(i);

                if (item != null && item.getType() != Material.AIR) {
                    CustomItem customItem = CustomItems.getCustomItem(item);

                    if (customItem != null) {
                        cost.put(customItem, item.getAmount());
                    }
                }
            }
        }

        return cost;
    }

    @Override
    public @NotNull Inventory getInventory() {


        return editorInventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {

    }

    @Override
    public void handleClose(InventoryCloseEvent event) {
        this.entry.setItemsCost(getItemCost());
    }

    @Override
    public void handleOpen(InventoryOpenEvent event) {

    }

    @Override
    public void handleDrag(InventoryDragEvent event) {

    }

    @Override
    public void handleMoveItem(InventoryMoveItemEvent event) {

    }

    @Override
    public void handleDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
