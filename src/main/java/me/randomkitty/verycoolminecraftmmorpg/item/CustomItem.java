package me.randomkitty.verycoolminecraftmmorpg.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.FoodProperties;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {

    protected final String key;

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
        this.key = builder.key;

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

        meta.displayName(this.getColoredName());

        {
            // The lore (those who lore)
            List<Component> lore = new ArrayList<>();
            lore.add(Component.empty());

            if (damage != 0 || criticalDamage != 0 || criticalChance != 0 || health != 0 || defense != 0 || intelligence != 0)
                lore.add(Component.text("ᴀᴛᴛʀɪʙᴜᴛᴇѕ: ").color(rarity.getColor()).decoration(TextDecoration.ITALIC, false));

            if (damage != 0)
                lore.add(Component.text("  ᴅᴀᴍᴀɢᴇ: ").color(NamedTextColor.WHITE).append(Component.text(StringUtil.longFormatedDouble(damage) + "\uD83D\uDDE1").color(NamedTextColor.RED)).decoration(TextDecoration.ITALIC, false));
            if (criticalDamage != 0)
                lore.add(Component.text("  ᴄʀɪᴛ-ᴅᴀᴍᴀɢᴇ: ").color(NamedTextColor.WHITE).append(Component.text(StringUtil.longFormatedDouble(criticalDamage) + "⚔").color(NamedTextColor.BLUE)).decoration(TextDecoration.ITALIC, false));
            if (criticalChance != 0)
                lore.add(Component.text("  ᴄʀɪᴛ-ᴄʜᴀɴᴄᴇ: ").color(NamedTextColor.WHITE).append(Component.text(StringUtil.longFormatedDouble(criticalChance) + "☠").color(NamedTextColor.BLUE)).decoration(TextDecoration.ITALIC, false));
            if (health != 0)
                lore.add(Component.text("  ʜᴇᴀʟᴛʜ: ").color(NamedTextColor.WHITE).append(Component.text(StringUtil.longFormatedDouble(health) + "❤").color(NamedTextColor.RED)).decoration(TextDecoration.ITALIC, false));
            if (defense != 0)
                lore.add(Component.text("  ᴅᴇꜰᴇɴᴄᴇ: ").color(NamedTextColor.WHITE).append(Component.text(StringUtil.longFormatedDouble(defense) + "\uD83D\uDEE1").color(NamedTextColor.GREEN)).decoration(TextDecoration.ITALIC, false));
            if (intelligence != 0)
                lore.add(Component.text("  ɪɴᴛᴇʟʟɪɢᴇɴᴄᴇ: ").color(NamedTextColor.WHITE).append(Component.text(StringUtil.longFormatedDouble(intelligence) + "☄").color(NamedTextColor.AQUA)).decoration(TextDecoration.ITALIC, false));

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            for (String line : this.lore) {
                lore.add(Component.text(line).color(NamedTextColor.GRAY));
            }

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            lore.add(Component.text(rarity.getText() + " " + type.getText()).color(rarity.getColor()).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));

            meta.lore(lore);
        }

        meta.setMaxStackSize(maxStackSize);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_DYE);

        item.setItemMeta(meta);

        // Data Stuff
        {
            item.unsetData(DataComponentTypes.CONSUMABLE);

            item.editPersistentDataContainer(p -> {
                p.set(CustomItems.CUSTOM_ITEM_KEY, PersistentDataType.STRING, this.key);
            });

        }

        return item;
    }

    public TextComponent getColoredName() {
        return Component.text(name, rarity.getColor()).decoration(TextDecoration.ITALIC, false);
    }



    public String getKey() { return key; }

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
