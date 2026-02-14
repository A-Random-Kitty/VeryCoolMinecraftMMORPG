package me.randomkitty.verycoolminecraftmmorpg.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosiveEvents implements Listener {

    @EventHandler
    public void onTntPrime(TNTPrimeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onExplosion(BlockExplodeEvent event) {
        event.blockList().clear();
    }
}
