package me.randomkitty.verycoolminecraftmmorpg.events;

import io.papermc.paper.event.player.PlayerClientLoadedWorldEvent;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        PlayerAttributes.initPlayer(player);

        event.joinMessage(Component.text(player.getName()).color(NamedTextColor.AQUA).append(Component.text(" joined the game").color(NamedTextColor.GREEN)));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerAttributes.removePlayer(player);

        event.quitMessage(Component.text(player.getName()).color(NamedTextColor.AQUA).append(Component.text(" left the game").color(NamedTextColor.GREEN)));
    }
}
