package me.randomkitty.verycoolminecraftmmorpg.item;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CustomItems {
    private static final Map<String, CustomItem> items = new HashMap<>();
    private static final NamespacedKey CUSTOM_ITEM_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "KEY");

    public static final CustomItem SHARP_STICK;

    private static CustomItem register(CustomItem item, String id) {
        items.put(id, item);
        return item;
    }

    public @Nullable CustomItem fromItemStack(ItemStack item) {
        String s = item.getPersistentDataContainer().get(CUSTOM_ITEM_KEY, PersistentDataType.STRING);
        if (s != null) {
            return items.get(s);
        }

        return null;
    }

    static {
        // Init items and stuff or something
        SHARP_STICK = register(new CustomItem(new CustomItemBuilder().setDamage(10).setType(ItemType.SWORD)), "sharp_stick");
    }
}
