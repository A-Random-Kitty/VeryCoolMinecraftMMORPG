package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.util.PermissionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FixItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You must be a player", NamedTextColor.RED));
            return true;
        }

        CustomItemInstance instance = CustomItems.fromItemStack(player.getInventory().getItemInMainHand());

        if (instance != null) {
            player.getInventory().setItemInMainHand(instance.toItemStack());
        }

        return true;
    }
}
