package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopEntry {

    private CustomItem item;
    int amount;

    private static Map<ItemStack, Integer> cost = new HashMap<>();

    public boolean buy(Player player) {
        return false;
    }
}
