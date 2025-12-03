package me.randomkitty.verycoolminecraftmmorpg.item;

import me.randomkitty.funnyminecraftmmo.items.ItemType;
import me.randomkitty.funnyminecraftmmo.items.Rarity;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class CustomItemBuilder {

    public Material material = Material.BARRIER;
    public int maxStackSize = 64;

    public Rarity rarity = Rarity.STRANGE;
    public ItemType type = ItemType.ITEM;
    public EquipmentSlot slot = EquipmentSlot.HAND;

    public String name = "Default Name";
    public List<String> lore = new ArrayList<>();

    public double damage = 0;
    public double criticalDamage = 0;
    public double criticalChance = 0;

    public double health = 0;
    public double defense = 0;
    public double intelligence = 0;

    public CustomItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public CustomItemBuilder setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public CustomItemBuilder setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public CustomItemBuilder setType(ItemType type) {
        this.type = type;
        return this;
    }

    public CustomItemBuilder setSlot(EquipmentSlot slot) {
        this.slot = slot;
        return this;
    }

    public CustomItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CustomItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public CustomItemBuilder addLore(String line) {
        this.lore.add(line);
        return this;
    }

    public CustomItemBuilder setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public CustomItemBuilder setCriticalDamage(double criticalDamage) {
        this.criticalDamage = criticalDamage;
        return this;
    }

    public CustomItemBuilder setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
        return this;
    }

    public CustomItemBuilder setHealth(double health) {
        this.health = health;
        return this;
    }

    public CustomItemBuilder setDefense(double defense) {
        this.defense = defense;
        return this;
    }

    public CustomItemBuilder setIntelligence(double intelligence) {
        this.intelligence = intelligence;
        return this;
    }
}
