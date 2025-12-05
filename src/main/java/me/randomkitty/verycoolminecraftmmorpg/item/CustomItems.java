package me.randomkitty.verycoolminecraftmmorpg.item;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CustomItems {
    private static final Map<String, CustomItem> items = new HashMap<>();
    private static final NamespacedKey CUSTOM_ITEM_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "is_custom");

    public static final CustomItem MUTTON;

    public static final CustomItem BLADE_OF_GRASS;
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
        MUTTON = register(new CustomItem(new CustomItemBuilder().setType(ItemType.MATERIAL).setRarity(Rarity.COMMON).setMaterial(Material.MUTTON).setName("Mutton")), "mutton");

        BLADE_OF_GRASS = register(new CustomItem(new CustomItemBuilder().setType(ItemType.SWORD).setRarity(Rarity.UNCOMMON).setMaterial(Material.BAMBOO).setMaxStackSize(1).setDamage(15).setName("Blade of Grass")), "blade_of_grass");
        SHARP_STICK = register(new CustomItem(new CustomItemBuilder().setType(ItemType.SWORD).setRarity(Rarity.COMMON).setMaterial(Material.STICK).setMaxStackSize(1).setDamage(10).setName("Sharp Stick")), "sharp_stick");

    }
}
