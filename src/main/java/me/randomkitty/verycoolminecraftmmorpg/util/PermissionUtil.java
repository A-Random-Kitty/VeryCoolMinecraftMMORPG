package me.randomkitty.verycoolminecraftmmorpg.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionUtil {

    public static boolean hasPermission(CommandSender player, String permission) {
        return player.hasPermission("coolrpg.*") || player.hasPermission(permission);
    }

}
