package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.entities.CustomCreatureType;
import me.randomkitty.verycoolminecraftmmorpg.util.PermissionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCustomEntityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player))
            return true;

        if (args.length != 1) {
            sender.sendMessage(Component.text("/spawncustomentity <entity>"));
            return true;
        }

        if (!(PermissionUtil.hasPermission(sender, "coolrpg.creature.spawn"))) {
            sender.sendMessage(Component.text("No permission", NamedTextColor.RED));
            return true;
        }

        CustomCreatureType<?> type = CustomCreatureType.fromString(args[0]);

        if (type != null) {
            type.spawnNewCreature(player.getLocation());
        }

        return true;
    }
}
