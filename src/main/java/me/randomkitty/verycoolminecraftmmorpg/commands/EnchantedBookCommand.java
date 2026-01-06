package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.item.items.EnchantedBookInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.items.EnchantedBookItem;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifier;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifiers;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.EnchantmentModifier;
import me.randomkitty.verycoolminecraftmmorpg.util.PermissionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnchantedBookCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You must be a player", NamedTextColor.RED));
            return true;
        }

        if (!(PermissionUtil.hasPermission(player, "coolrpg.item.book.give"))) {
            sender.sendMessage(Component.text("No permission", NamedTextColor.RED));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(Component.text("Correct usage: /givebook <enchant> <level>", NamedTextColor.RED));
            return true;
        }

        ItemModifier modifier = ItemModifiers.getModifier(args[0]);

        if (modifier instanceof EnchantmentModifier enchantmentModifier) {

            try {
                Integer level = Integer.parseInt(args[1]);

                EnchantedBookInstance book = EnchantedBookItem.newBook(enchantmentModifier, level);
                player.give(book.toItemStack());
            } catch (NumberFormatException e) {
                sender.sendMessage(Component.text("pls use integre", NamedTextColor.RED));
            }

        } else {
            sender.sendMessage(Component.text("Provide a valid enchantment"));
            return true;
        }

        return true;
    }
}
