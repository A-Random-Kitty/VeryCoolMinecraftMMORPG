package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.inventory.shop.Shops;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OpenShopForPlayerCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission("coolrpg.shop.openforplayer") || args.length != 2) {
            sender.sendMessage(Component.text("nope", NamedTextColor.RED));
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);

        if (p != null) {
            Shops.openShop(p, args[1]);
        }


        return true;
    }
}
