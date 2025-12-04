package me.randomkitty.verycoolminecraftmmorpg.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {


    public static int givePlayerItemOrDrop(Player player, ItemStack item, Location dropLocation) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(item);
        }



        return 0;
    }

}
