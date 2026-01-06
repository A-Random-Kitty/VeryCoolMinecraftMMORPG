package me.randomkitty.verycoolminecraftmmorpg.item;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifier;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifiers;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CustomItemInstance {

    public CustomItem baseItem;
    int amount;

    public CustomItemInstance(CustomItem item) {
        this.baseItem = item;
        this.amount = 1;
    }

    public CustomItemInstance(CustomItem item, ItemStack stack) {
        this.baseItem = item;
        this.amount = stack.getAmount();
    }

    public ItemStack toItemStack() {
        ItemStack item =  baseItem.toItemStack(this);
        item.setAmount(amount);
        return item;
    }

    public void addAttributes(PlayerAttributes attributes) {
        attributes.defense += this.baseItem.getDefense();
        attributes.health += this.baseItem.getHealth();
        attributes.mana += this.baseItem.getMana();
        attributes.damage += this.baseItem.getDamage();
        attributes.critChance += this.baseItem.getCriticalChance();
        attributes.critDamage += this.baseItem.getCriticalDamage();
    }

    public void applyItemMultipliers(PlayerAttributes attributes) {

    }
}
