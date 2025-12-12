package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Shop implements CustomInventory {

    private final List<ShopEntry> entries = new ArrayList<>();

    private ItemStack[] contents;

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
}
