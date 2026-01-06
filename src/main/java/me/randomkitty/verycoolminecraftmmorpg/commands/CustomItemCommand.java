package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.util.PermissionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player))
            return true;

        if (!(PermissionUtil.hasPermission(player, "coolrpg.item.give"))) {
            sender.sendMessage(Component.text("No permission", NamedTextColor.RED));
            return true;
        }

        if (args.length != 1)
            return true;

        CustomItem item = CustomItems.get(args[0]);
        player.give(item.newItemStack());

        return true;
    }
}
