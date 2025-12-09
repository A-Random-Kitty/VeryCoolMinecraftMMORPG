package me.randomkitty.verycoolminecraftmmorpg.events;

import io.papermc.paper.event.player.PlayerClientLoadedWorldEvent;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerScoreboard;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerData;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainEvents implements Listener {

    @EventHandler
    public void onPrePlayerJoin(AsyncPlayerPreLoginEvent event) {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        PlayerData.initPlayer(player);
        PlayerAttributes.initPlayer(player);


        event.joinMessage(Component.text(player.getName()).color(NamedTextColor.AQUA).append(Component.text(" joined the game").color(NamedTextColor.GREEN)));
    }

    @EventHandler
    public void onPlayerLoadWorld(PlayerClientLoadedWorldEvent event) {
        Player player = event.getPlayer();

        PlayerAttributes.calculateAttributes(player);
        PlayerScoreboard.applyPlayerScoreboard(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerData.savePlayer(player);
        PlayerData.removePlayer(player);
        PlayerAttributes.removePlayer(player);

        event.quitMessage(Component.text(player.getName()).color(NamedTextColor.AQUA).append(Component.text(" left the game").color(NamedTextColor.GREEN)));
    }
}
