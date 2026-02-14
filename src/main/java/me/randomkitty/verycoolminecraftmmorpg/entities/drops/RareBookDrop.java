package me.randomkitty.verycoolminecraftmmorpg.entities.drops;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.item.items.EnchantedBookItem;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.EnchantmentModifier;
import org.bukkit.inventory.ItemStack;

public class RareBookDrop extends RareLootDrop {

    private EnchantmentModifier enchant;
    private int level;

    public RareBookDrop(float chance, EnchantmentModifier enchant, int level) {
        super(chance, CustomItems.ENCHANTED_BOOK);
        this.enchant = enchant;
        this.level = level;
    }

    public RareBookDrop(float chance, EnchantmentModifier enchant, int level, boolean tellPlayer) {
        super(chance, CustomItems.ENCHANTED_BOOK, tellPlayer);
        this.enchant = enchant;
        this.level = level;
    }

    public ItemStack getItem() {
        return EnchantedBookItem.newBook(enchant, level).toItemStack();
    }

}
