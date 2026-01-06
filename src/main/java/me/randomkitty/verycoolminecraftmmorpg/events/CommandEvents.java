package me.randomkitty.verycoolminecraftmmorpg.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvents implements Listener {

    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0];

        if (command.contains("kill") && event.getMessage().contains("@e")) {
            event.getPlayer().sendMessage(Component.text("Do not /kill @e", NamedTextColor.RED));
            event.setCancelled(true);
        }
    }
}
