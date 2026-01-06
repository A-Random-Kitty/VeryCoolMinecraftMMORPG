package me.randomkitty.verycoolminecraftmmorpg.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.FoodProperties;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifier;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.EnchantmentModifier;
import me.randomkitty.verycoolminecraftmmorpg.util.NumberUtil;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CustomItem {

    protected final String key;

    protected final Material material;
    protected final Color color;
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
    protected final double mana;

    public CustomItem (CustomItemBuilder builder) {
        this.key = builder.key;

        this.material = builder.material;
        this.color = builder.color;
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
        this.mana = builder.mana;
    }

    // Creates an instance of this item
    public CustomItemInstance fromItemStack(ItemStack item) {
        return new CustomItemInstance(this, item);
    }

    // Creates a new itemstack from this item
    public ItemStack newItemStack() {
        return toItemStack(null);
    }

    // Creates an itemstack from an instance
    public ItemStack toItemStack(CustomItemInstance instance) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(this.getColoredName(instance));

        {
            // The lore (those who lore)
            List<Component> lore = new ArrayList<>();
            lore.add(Component.empty());

            addLoreBeforeStats(lore, instance);

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            if (damage != 0 || criticalDamage != 0 || criticalChance != 0 || health != 0 || defense != 0 || mana != 0)
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
            if (mana != 0)
                lore.add(Component.text("  ᴍᴀɴᴀ: ").color(NamedTextColor.WHITE).append(Component.text(StringUtil.longFormatedDouble(mana) + "☄").color(NamedTextColor.AQUA)).decoration(TextDecoration.ITALIC, false));

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            addLoreAfterStats(lore, instance);

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

           lore.addAll(this.getLore(instance));

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            addLoreAfterTheLore(lore, instance);

            if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }

            lore.add(Component.text(rarity.getText() + " " + type.getText()).color(rarity.getColor()).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));

            meta.lore(lore);
        }

        meta.setMaxStackSize(maxStackSize);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_DYE);

        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            leatherArmorMeta.setColor(color);
        }

        item.setItemMeta(meta);

        // Data Stuff
        {
            item.unsetData(DataComponentTypes.CONSUMABLE);
            item.unsetData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            item.unsetData(DataComponentTypes.INSTRUMENT);

            item.editPersistentDataContainer(p -> {
                p.set(CustomItems.CUSTOM_ITEM_KEY, PersistentDataType.STRING, this.key);
                addCustomData(p, instance);
            });

        }

        return item;
    }

    protected void addLoreBeforeStats(List<Component> lore, CustomItemInstance instance) {}
    protected void addLoreAfterStats(List<Component> lore, CustomItemInstance instance) {}
    protected void addLoreAfterTheLore(List<Component> lore, CustomItemInstance instance) {}
    protected void addCustomData(PersistentDataContainer container, CustomItemInstance instance) {}

    public TextComponent getColoredName() {
        return Component.text(name, rarity.getColor()).decoration(TextDecoration.ITALIC, false);
    }

    public TextComponent getColoredName(CustomItemInstance instance) {
        return Component.text(name, rarity.getColor()).decoration(TextDecoration.ITALIC, false);
    }

    public List<TextComponent> getLore(CustomItemInstance instance) {
        List<TextComponent> components = new ArrayList<>();

        for (String l : lore) {
            components.add(Component.text(l, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        }

        return components;
    }

    public void onUseItem(PlayerInteractEvent event) {

    }

    public String getKey() {
        return key;
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

    public double getMana() {
        return mana;
    }
}
