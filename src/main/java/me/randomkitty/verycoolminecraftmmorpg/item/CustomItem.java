package me.randomkitty.verycoolminecraftmmorpg.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.FoodProperties;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {

    protected final Material material;
    protected final int maxStackSize;

    protected final Rarity rarity;
    protected final ItemType type;
    protected final EquipmentSlot slot;

    protected final String name;
    protected final List<String> lore;

    protected final double damage;
    protected final double criticalDamage;
    protected final double criticalChance;

    protected final double health;
    protected final double defense;
    protected final double intelligence;

    public CustomItem (CustomItemBuilder builder) {
        this.material = builder.material;
        this.maxStackSize = builder.maxStackSize;

        this.rarity = builder.rarity;
        this.type = builder.type;
        this.slot = builder.slot;

        this.name = builder.name;
        this.lore = builder.lore;

        this.damage = builder.damage;
        this.criticalDamage = builder.criticalDamage;
        this.criticalChance = builder.criticalChance;

        this.health = builder.health;
        this.defense = builder.defense;
        this.intelligence = builder.intelligence;
    }

    public ItemStack newItemStack() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name, rarity.getColor()).decoration(TextDecoration.ITALIC, false));

        {
            List<Component> lore = new ArrayList<>();
            lore.add(Component.empty());

            if (damage != 0) { lore.add(Component.text("Damage: ").color(NamedTextColor.WHITE).append(Component.text(damage).color(NamedTextColor.RED)).decoration(TextDecoration.ITALIC, false)); }
            if (criticalDamage != 0) { lore.add(Component.text("Critical Damage: ").color(NamedTextColor.WHITE).append(Component.text(criticalDamage).color(NamedTextColor.BLUE)).decoration(TextDecoration.ITALIC, false)); }
            if (criticalChance != 0) { lore.add(Component.text("Critical Chance: ").color(NamedTextColor.WHITE).append(Component.text(criticalChance).color(NamedTextColor.BLUE)).decoration(TextDecoration.ITALIC, false)); }
            if (health != 0) { lore.add(Component.text("Health: ").color(NamedTextColor.WHITE).append(Component.text(health).color(NamedTextColor.RED)).decoration(TextDecoration.ITALIC, false)); }
            if (defense != 0) { lore.add(Component.text("Defence: ").color(NamedTextColor.WHITE).append(Component.text(defense).color(NamedTextColor.GREEN)).decoration(TextDecoration.ITALIC, false)); }
            if (intelligence != 0) { lore.add(Component.text("Intelligence: ").color(NamedTextColor.WHITE).append(Component.text(intelligence).color(NamedTextColor.AQUA)).decoration(TextDecoration.ITALIC, false)); }

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            for (String line : this.lore) {
                lore.add(Component.text(line).color(NamedTextColor.GRAY));
            }

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            lore.add(Component.text(rarity.getText() + " " + type.getText()).color(rarity.getColor()).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));

            meta.lore(lore);
        }

        meta.setMaxStackSize(maxStackSize);

        item.setItemMeta(meta);

        item.unsetData(DataComponentTypes.CONSUMABLE);

        return item;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ItemType getType() {
        return type;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public double getDamage() {
        return damage;
    }

    public double getCriticalDamage() {
        return criticalDamage;
    }

    public double getCriticalChance() {
        return criticalChance;
    }

    public double getHealth() {
        return health;
    }

    public double getDefense() {
        return defense;
    }

    public double getIntelligence() {
        return intelligence;
    }
}
