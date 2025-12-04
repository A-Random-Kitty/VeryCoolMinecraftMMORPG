package me.randomkitty.verycoolminecraftmmorpg.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {


    public boolean givePlayerItemOrDrop(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(item);
        }

    }

}
