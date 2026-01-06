package me.randomkitty.verycoolminecraftmmorpg.item.items;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemBuilder;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.EnchantmentModifier;
import me.randomkitty.verycoolminecraftmmorpg.util.NumberUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EnchantedBookItem extends CustomItem {

    public static final NamespacedKey ENCHANTED_BOOK_ENCHANT = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "enchanted_book_enchant");
    public static final NamespacedKey ENCHANTED_BOOK_LEVEL = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "enchanted_book_level");

    public static EnchantedBookInstance newBook(EnchantmentModifier modifier, int level) {
        EnchantedBookInstance instance = new EnchantedBookInstance(CustomItems.ENCHANTED_BOOK);
        instance.enchant = modifier;
        instance.level = level;
        return instance;
    }

    public static EnchantedBookInstance fromEnchantInstance(ItemModifierInstance instance) {
        if (instance.modifier instanceof EnchantmentModifier enchant) {
            return newBook(enchant, instance.level);
        } else {
            return new EnchantedBookInstance(CustomItems.ENCHANTED_BOOK);
        }
    }

    public EnchantedBookItem(CustomItemBuilder builder) {
        super(builder);
    }

    @Override
    public CustomItemInstance fromItemStack(ItemStack item) {
        return new EnchantedBookInstance(this, item);
    }

    @Override
    public TextComponent getColoredName(CustomItemInstance instance) {
        if (instance instanceof EnchantedBookInstance bookInstance) {
            if (bookInstance.enchant != null) {
                return Component.text(name + " of " + bookInstance.enchant.getName() + " " + NumberUtil.toRoman(bookInstance.level), rarity.getColor()).decoration(TextDecoration.ITALIC, false);
            } else {
                return Component.text(name, rarity.getColor()).decoration(TextDecoration.ITALIC, false);
            }
        } else {
            return Component.text(name, rarity.getColor()).decoration(TextDecoration.ITALIC, false);
        }
    }

    @Override
    public List<TextComponent> getLore(CustomItemInstance instance) {

        if (instance instanceof EnchantedBookInstance bookInstance) {
            if (bookInstance.enchant != null) {
                return bookInstance.enchant.getLore(bookInstance.level);
            } else {
                return List.of(Component.text("Book is very bugged or something", NamedTextColor.RED));            }
        } else {
            return List.of(Component.text("Book is very bugged or something", NamedTextColor.RED));
        }
    }

}
