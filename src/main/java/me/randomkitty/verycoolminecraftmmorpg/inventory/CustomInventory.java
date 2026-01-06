package me.randomkitty.verycoolminecraftmmorpg.inventory;

import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryHolder;

public abstract interface CustomInventory extends InventoryHolder {

    void handleClick(InventoryClickEvent event);
    void handleClose(InventoryCloseEvent event);
    void handleOpen(InventoryOpenEvent event);
    void handleDrag(InventoryDragEvent event);
    void handleMoveItem(InventoryMoveItemEvent event);
    void handleDrop(PlayerDropItemEvent event);

}
