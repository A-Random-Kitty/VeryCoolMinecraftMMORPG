package me.randomkitty.verycoolminecraftmmorpg.item;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CustomItems {
    private static final Map<String, CustomItem> items = new HashMap<>();
    public static final NamespacedKey CUSTOM_ITEM_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "custom_item");

    public static final CustomItem MUTTON;

    public static final CustomItem BLADE_OF_GRASS;
    public static final CustomItem SHARP_STICK;

    public static final CustomItem SWORD_OF_DIVINE_WRATH;

    public static CustomItem get(String key) {
        return items.get(key);
    }

    private static CustomItem register(CustomItem item) {
        items.put(item.key, item);
        return item;
    }

    public static @Nullable CustomItemInstance fromItemStack(ItemStack item) {
        String key = item.getPersistentDataContainer().get(CUSTOM_ITEM_KEY, PersistentDataType.STRING);
        if (key != null) {
            CustomItem base = items.get(key);
            // get the modifiers here once they are added
            return new CustomItemInstance(base);
        }

        return null;
    }

    public static @Nullable CustomItem getCustomItem(ItemStack itemStack) {
        return get(itemStack.getPersistentDataContainer().get(CUSTOM_ITEM_KEY, PersistentDataType.STRING));
    }

    static {
        // Init items and stuff or something
        MUTTON = register(new CustomItem(new CustomItemBuilder("mutton").setType(ItemType.MATERIAL).setRarity(Rarity.COMMON).setMaterial(Material.MUTTON).setName("Mutton")));

        BLADE_OF_GRASS = register(new CustomItem(new CustomItemBuilder("blade_of_grass").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.UNCOMMON).setMaterial(Material.BAMBOO).setMaxStackSize(1).setDamage(15).setName("Blade of Grass")));
        SHARP_STICK = register(new CustomItem(new CustomItemBuilder("sharp_stick").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.COMMON).setMaterial(Material.STICK).setMaxStackSize(1).setDamage(10).setName("Sharp Stick")));

        SWORD_OF_DIVINE_WRATH = register(new CustomItem(new CustomItemBuilder("sword_of_divine_wrath").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.MYTHICAL).setMaterial(Material.NETHERITE_SWORD).setMaxStackSize(1).setDamage(99999).setName("Sword of Divine Wrath")));

    }
}
