package me.randomkitty.verycoolminecraftmmorpg.entities;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CustomEntityDefaultDrop {

    private static final Random random = new Random();

    private final int minDrops;
    private final int maxDrops;
    private final CustomItem drop;

    public CustomEntityDefaultDrop(int minDrops, int maxDrops, CustomItem drop) {
        this.minDrops = minDrops;
        this.maxDrops = maxDrops + 1; // add 1 because randInt is exclusive of max
        this.drop = drop;
    }

    public ItemStack getDrop() {
        ItemStack item = drop.newItemStack();
        item.setAmount(random.nextInt(minDrops, maxDrops));
        return item;
    }

}
