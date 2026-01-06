package me.randomkitty.verycoolminecraftmmorpg.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;

public class InventoryEvents implements Listener {

    @EventHandler
    public void onPlayerHeldItem(PlayerItemHeldEvent event) {
        PlayerAttributes.calculateAttributes(event.getPlayer(), event.getPlayer().getInventory().getItem(event.getNewSlot()));
    }

    @EventHandler
    public void onInventorySlotChange(PlayerInventorySlotChangeEvent event) {
        if (event.getSlot() == event.getPlayer().getInventory().getHeldItemSlot()) {
            PlayerAttributes.calculateAttributes(event.getPlayer(), event.getPlayer().getInventory().getItem(event.getSlot()));
        }
    }

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {

        PlayerAttributes.calculateAttributes(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof CustomInventory cinv) {
            cinv.handleClick(event);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof CustomInventory cinv) {
            cinv.handleOpen(event);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof CustomInventory cinv) {
            cinv.handleClose(event);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof CustomInventory cinv) {
            cinv.handleDrag(event);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getOpenInventory() instanceof Inventory inventory) {
            if (inventory instanceof CustomInventory cinv) {
                cinv.handleDrop(event);
            }
        }
    }
}
