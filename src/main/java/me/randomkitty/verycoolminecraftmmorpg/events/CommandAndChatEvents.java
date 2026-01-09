package me.randomkitty.verycoolminecraftmmorpg.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandAndChatEvents implements Listener {

    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0];

        if (command.contains("kill") && event.getMessage().contains("@e")) {
            event.getPlayer().sendMessage(Component.text("Do not /kill @e", NamedTextColor.RED));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();

        CachedMetaData rankData = VeryCoolMinecraftMMORPG.RANK_PROVIDER.getMetaData(player);
        String prefix = rankData.getPrefix();

        if (prefix != null) {
            Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(prefix + player.getName()).append(Component.text(": ", NamedTextColor.GRAY).append(event.message()).color(NamedTextColor.WHITE)));
        } else {
            Bukkit.broadcast(Component.text(player.getName()).append(Component.text(": ", NamedTextColor.GRAY).append(event.message()).color(NamedTextColor.WHITE)));
        }
    }
}
