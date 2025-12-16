package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.inventory.shop.Shops;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length < 1 || !(sender instanceof Player player))
            return true;

        switch (args[0]) {
            case "open":
                if (args.length == 2) {
                    Shops.openShop(player, args[1]);
                    return true;
                }
                break;
            case "load":
                Shops.loadAllShops();
                break;
            case "save":
                Shops.saveAllShops();
                break;

            default:
                break;
        }


        return true;
    }
}
