package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.item.items.ModifiableItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifiers;
import me.randomkitty.verycoolminecraftmmorpg.util.PermissionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AttributeModifierCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You must be a player", NamedTextColor.RED));
            return true;
        }

        if (!(PermissionUtil.hasPermission(player, "coolrpg.item.modify"))) {
            sender.sendMessage(Component.text("No permission", NamedTextColor.RED));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(Component.text("Correct usage: /modify <modifier> <level>", NamedTextColor.RED));
            return true;
        }

        try {
            ItemModifierInstance instance = new ItemModifierInstance(ItemModifiers.getModifier(args[0]), Integer.parseInt(args[1]));

            ItemStack item = player.getInventory().getItemInMainHand();
            CustomItemInstance citem = CustomItems.fromItemStack(item);
            if (citem instanceof ModifiableItemInstance modifiableItem) {
                modifiableItem.modifiers.add(instance);
                player.getInventory().setItemInMainHand(modifiableItem.toItemStack());
            } else {
                player.sendMessage(Component.text("Item is not modifiable", NamedTextColor.RED));
            }

        } catch (NumberFormatException i) {
            sender.sendMessage(Component.text("pls use integre", NamedTextColor.RED));
        }

        return true;
    }
}
