package me.randomkitty.verycoolminecraftmmorpg.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityEvents implements Listener {

    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {
        if (event.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM) {
            event.setCancelled(true);
            return;
        }
    }
}
