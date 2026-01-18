package me.randomkitty.verycoolminecraftmmorpg.item;

import io.papermc.paper.persistence.PersistentDataContainerView;
import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.items.EnchantedBookItem;
import me.randomkitty.verycoolminecraftmmorpg.item.items.ModifiableItem;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifiers;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomItems {
    private static final Map<String, CustomItem> items = new HashMap<>();
    public static final NamespacedKey CUSTOM_ITEM_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "custom_item");

    public static final CustomItem MUTTON;
    public static final CustomItem GRASSY_WOOL;
    public static final CustomItem RAM_HORN_FRAGMENT;

    public static final CustomItem WOLF_PELT;
    public static final CustomItem WOLF_TOOTH;
    public static final CustomItem ALPHA_WOLF_PELT;

    public static final ModifiableItem SHARP_STICK;
    public static final ModifiableItem BLADE_OF_GRASS;
    public static final ModifiableItem RAM_HORN;
    public static final ModifiableItem BLADES_OF_GRASS;

    public static final ModifiableItem ANCIENT_WOLF_FANG;

    public static final ModifiableItem SWORD_OF_DIVINE_WRATH;

    public static final ModifiableItem GRASSY_HELMET;
    public static final ModifiableItem GRASSY_CHESTPLATE;
    public static final ModifiableItem GRASSY_LEGGINGS;
    public static final ModifiableItem GRASSY_BOOTS;

    public static final ModifiableItem WOLF_PELT_HELMET;
    public static final ModifiableItem WOLF_PELT_CHESTPLATE;
    public static final ModifiableItem WOLF_PELT_LEGGINGS;
    public static final ModifiableItem WOLF_PELT_BOOTS;

    public static final ModifiableItem ALPHA_WOLF_PELT_HELMET;
    public static final ModifiableItem ALPHA_WOLF_PELT_CHESTPLATE;
    public static final ModifiableItem ALPHA_WOLF_PELT_LEGGINGS;
    public static final ModifiableItem ALPHA_WOLF_PELT_BOOTS;

    public static final EnchantedBookItem ENCHANTED_BOOK;

    public static CustomItem get(String key) {
        return items.get(key);
    }

    private static CustomItem register(CustomItem item) {
        items.put(item.key, item);
        return item;
    }

    public static @Nullable CustomItemInstance fromItemStack(ItemStack item) {
        PersistentDataContainerView container = item.getPersistentDataContainer();
        String key = container.get(CUSTOM_ITEM_KEY, PersistentDataType.STRING);

        if (key != null) {
            CustomItem base = items.get(key);

            return base.fromItemStack(item);
        }

        return null;
    }

    public static @Nullable CustomItem getCustomItem(ItemStack itemStack) {
        return get(itemStack.getPersistentDataContainer().get(CUSTOM_ITEM_KEY, PersistentDataType.STRING));
    }

    static {
        // Init items and stuff or something
        MUTTON = register(new CustomItem(new CustomItemBuilder("mutton").setType(ItemType.MATERIAL).setRarity(Rarity.COMMON).setMaterial(Material.MUTTON).setName("Mutton")));
        GRASSY_WOOL = register(new CustomItem(new CustomItemBuilder("grassy_wool").setType(ItemType.MATERIAL).setRarity(Rarity.COMMON).setMaterial(Material.LIME_WOOL).setName("Grassy Wool")));
        RAM_HORN_FRAGMENT = register(new CustomItem(new CustomItemBuilder("ram_horn_fragment").setType(ItemType.MATERIAL).setRarity(Rarity.UNCOMMON).setMaterial(Material.STONE_BUTTON).setName("Ram Horn Fragment")));
        WOLF_PELT = register(new CustomItem(new CustomItemBuilder("wolf_pelt").setType(ItemType.MATERIAL).setRarity(Rarity.COMMON).setMaterial(Material.LIGHT_GRAY_WOOL).setName("Wolf Pelt")));
        WOLF_TOOTH = register(new CustomItem(new CustomItemBuilder("wolf_tooth").setType(ItemType.MATERIAL).setRarity(Rarity.UNCOMMON).setMaterial(Material.GHAST_TEAR).setName("Wolf Tooth")));
        ALPHA_WOLF_PELT = register(new CustomItem(new CustomItemBuilder("alpha_wolf_pelt").setType(ItemType.MATERIAL).setRarity(Rarity.COMMON).setMaterial(Material.GRAY_WOOL).setName("Alpha Wolf Pelt")));

        SHARP_STICK = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("sharp_stick").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.COMMON).setMaterial(Material.STICK).setMaxStackSize(1).setDamage(10).setName("Sharp Stick")));
        BLADE_OF_GRASS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("blade_of_grass").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.UNCOMMON).setMaterial(Material.BAMBOO).setMaxStackSize(1).setDamage(15).setName("Blade of Grass")));
        RAM_HORN = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("ram_horn").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.UNCOMMON).setMaterial(Material.POINTED_DRIPSTONE).setMaxStackSize(1).setDamage(20).setCriticalDamage(25).setName("Ram Horn")));
        BLADES_OF_GRASS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("blades_of_grass").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.RARE).setMaterial(Material.SUGAR_CANE).setMaxStackSize(1).setDamage(25).setCriticalDamage(50).setCriticalChance(35).setName("Blades of Grass")));

        ANCIENT_WOLF_FANG = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("ancient_wolf_fang").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.RARE).setMaterial(Material.BONE).setMaxStackSize(1).setDamage(30).setCriticalDamage(15).setCriticalChance(15).setName("Ancient Wolf Fang")));

        SWORD_OF_DIVINE_WRATH = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("sword_of_divine_wrath").setType(ItemType.SWORD).setSlot(EquipmentSlot.HAND).setRarity(Rarity.MYTHICAL).setMaterial(Material.NETHERITE_SWORD).setMaxStackSize(1).setDamage(99999).setName("Sword of Divine Wrath")));

        GRASSY_HELMET = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("grassy_helmet").setType(ItemType.HELMET).setSlot(EquipmentSlot.HEAD).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_HELMET).setColor(Color.LIME).setMaxStackSize(1).setDefense(6).setHealth(5).setName("Grassy Helmet")));
        GRASSY_CHESTPLATE = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("grassy_chestplate").setType(ItemType.CHESTPLATE).setSlot(EquipmentSlot.CHEST).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_CHESTPLATE).setColor(Color.LIME).setMaxStackSize(1).setDefense(8).setHealth(5).setName("Grassy Chestplate")));
        GRASSY_LEGGINGS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("grassy_leggings").setType(ItemType.LEGGINGS).setSlot(EquipmentSlot.LEGS).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_LEGGINGS).setColor(Color.LIME).setMaxStackSize(1).setDefense(8).setHealth(5).setName("Grassy Leggings")));
        GRASSY_BOOTS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("grassy_boots").setType(ItemType.BOOTS).setSlot(EquipmentSlot.FEET).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_BOOTS).setColor(Color.LIME).setMaxStackSize(1).setDefense(6).setHealth(5).setName("Grassy Boots")));

        WOLF_PELT_HELMET = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("wolf_pelt_helmet").setType(ItemType.HELMET).setSlot(EquipmentSlot.HEAD).setRarity(Rarity.COMMON).setMaterial(Material.LEATHER_HELMET).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setName("Wolf Pelt Helmet")));
        WOLF_PELT_LEGGINGS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("wolf_pelt_chestplate").setType(ItemType.CHESTPLATE).setSlot(EquipmentSlot.CHEST).setRarity(Rarity.COMMON).setMaterial(Material.LEATHER_CHESTPLATE).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setName("Wolf Pelt Chestplate")));
        WOLF_PELT_CHESTPLATE = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("wolf_pelt_leggings").setType(ItemType.LEGGINGS).setSlot(EquipmentSlot.LEGS).setRarity(Rarity.COMMON).setMaterial(Material.LEATHER_LEGGINGS).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setName("Wolf Pelt Leggings")));
        WOLF_PELT_BOOTS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("wolf_pelt_boots").setType(ItemType.BOOTS).setSlot(EquipmentSlot.FEET).setRarity(Rarity.COMMON).setMaterial(Material.LEATHER_BOOTS).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setName("Wolf Pelt Boots")));

        ALPHA_WOLF_PELT_HELMET = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("alpha_wolf_pelt_helmet").setType(ItemType.HELMET).setSlot(EquipmentSlot.HEAD).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_HELMET).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setCriticalDamage(5).setName("Alpha Wolf Pelt Helmet")));
        ALPHA_WOLF_PELT_LEGGINGS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("alpha_wolf_pelt_chestplate").setType(ItemType.CHESTPLATE).setSlot(EquipmentSlot.CHEST).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_CHESTPLATE).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setCriticalDamage(5).setName("Alpha Wolf Pelt Chestplate")));
        ALPHA_WOLF_PELT_CHESTPLATE = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("alpha_wolf_pelt_leggings").setType(ItemType.LEGGINGS).setSlot(EquipmentSlot.LEGS).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_LEGGINGS).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setCriticalDamage(5).setName("Alpha Wolf Pelt Leggings")));
        ALPHA_WOLF_PELT_BOOTS = (ModifiableItem) register(new ModifiableItem(new CustomItemBuilder("alpha_wolf_pelt_boots").setType(ItemType.BOOTS).setSlot(EquipmentSlot.FEET).setRarity(Rarity.UNCOMMON).setMaterial(Material.LEATHER_BOOTS).setColor(Color.GRAY).setMaxStackSize(1).setDefense(12).setHealth(15).setCriticalDamage(5).setName("Alpha Wolf Pelt Boots")));

        ENCHANTED_BOOK = (EnchantedBookItem) register(new EnchantedBookItem(new CustomItemBuilder("enchanted_book").setType(ItemType.ITEM).setRarity(Rarity.RARE).setMaterial(Material.ENCHANTED_BOOK).setMaxStackSize(1).setName("Enchanted Book")));
    }
}
