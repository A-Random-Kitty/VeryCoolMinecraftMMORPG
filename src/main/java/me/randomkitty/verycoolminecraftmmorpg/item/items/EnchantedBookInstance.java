package me.randomkitty.verycoolminecraftmmorpg.item.items;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifier;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifiers;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.EnchantmentModifier;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EnchantedBookInstance extends CustomItemInstance {

    public EnchantmentModifier enchant;
    public int level;

    public EnchantedBookInstance(CustomItem item) {
        super(item);
        this.enchant = null;
        this.level = 0;
    }

    public EnchantedBookInstance(CustomItem item, ItemStack stack) {
        super(item, stack);

        ItemModifier modifier = ItemModifiers.getModifier(stack.getPersistentDataContainer().get(EnchantedBookItem.ENCHANTED_BOOK_ENCHANT, PersistentDataType.STRING));

        if (modifier instanceof EnchantmentModifier) {
            enchant = (EnchantmentModifier) modifier;

            Integer level = stack.getPersistentDataContainer().get(EnchantedBookItem.ENCHANTED_BOOK_LEVEL, PersistentDataType.INTEGER);

            if (level != null) {
                this.level = level;
            } else {
                this.level = 1;
                VeryCoolMinecraftMMORPG.LOGGER.severe("Enchanted book level is invalid");
            }
        } else {
            VeryCoolMinecraftMMORPG.LOGGER.severe("Invalid enchantment for enchanted book");
        }
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack item = super.toItemStack();

        item.editPersistentDataContainer(container -> {

            if (enchant != null) {
                container.set(EnchantedBookItem.ENCHANTED_BOOK_ENCHANT, PersistentDataType.STRING, enchant.getKey());

            }

            container.set(EnchantedBookItem.ENCHANTED_BOOK_LEVEL, PersistentDataType.INTEGER, level);
        });

        return item;
    }
}
