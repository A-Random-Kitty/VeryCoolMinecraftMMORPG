package me.randomkitty.verycoolminecraftmmorpg.entities;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CustomEntityRareDrop {

    private static final Random random = new Random();

    private final float chance;
    private final CustomItem item;

    public CustomEntityRareDrop (float chance, CustomItem item) {
        this.chance = chance;
        this.item = item;
    }

    public ItemStack getItem() {
        return item.newItemStack();
    }

    public boolean shouldDrop() {
        return random.nextFloat() > chance;
    }
}
