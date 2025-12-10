package me.randomkitty.verycoolminecraftmmorpg.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

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
}
