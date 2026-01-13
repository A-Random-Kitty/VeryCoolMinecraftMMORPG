package me.randomkitty.verycoolminecraftmmorpg.events;

import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent;
import io.papermc.paper.event.player.PlayerClientLoadedWorldEvent;
import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerScoreboard;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerData;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionEvents implements Listener {

    @EventHandler
    public void onPrePlayerJoin(AsyncPlayerPreLoginEvent event) {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getAttribute(Attribute.ATTACK_SPEED).setBaseValue(1000);

        PlayerData.initPlayer(player);
        PlayerAttributes.initPlayer(player);
        PlayerAttributes.calculateAttributes(player);

        CachedMetaData rankData = VeryCoolMinecraftMMORPG.RANK_PROVIDER.getMetaData(player);
        String prefix = rankData.getPrefix();

        player.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(prefix + player.getName()));

        if (prefix != null) {
            event.joinMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(prefix + player.getName()).append(Component.text(" joined the game", NamedTextColor.GRAY)));
        } else {
            event.joinMessage(Component.text(player.getName()).append(Component.text(" joined the game", NamedTextColor.GRAY)));
        }

        player.setFallDistance(0);
        player.setFireTicks(0);

    }

    @EventHandler
    public void onPlayerLoadWorld(PlayerClientLoadedWorldEvent event) {
        Player player = event.getPlayer();
        player.teleport(VeryCoolMinecraftMMORPG.CONFIG.getSpawnLocation());

        PlayerScoreboard.applyPlayerScoreboard(player);
    }

    @EventHandler
    public void onSpawnLocation(AsyncPlayerSpawnLocationEvent event) {
        event.setSpawnLocation(VeryCoolMinecraftMMORPG.CONFIG.getSpawnLocation());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerData.savePlayer(player);
        PlayerData.removePlayer(player);
        PlayerAttributes.removePlayer(player);

        CachedMetaData rankData = VeryCoolMinecraftMMORPG.RANK_PROVIDER.getMetaData(player);
        String prefix = rankData.getPrefix();

        if (prefix != null) {
            event.quitMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(prefix + player.getName()).append(Component.text(" left the game", NamedTextColor.GRAY)));
        } else {
            event.quitMessage(Component.text(player.getName()).append(Component.text(" left the game", NamedTextColor.GRAY)));
        }
    }
}
