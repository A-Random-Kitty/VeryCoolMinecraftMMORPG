package me.randomkitty.verycoolminecraftmmorpg.item.items;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemBuilder;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.EnchantmentModifier;
import me.randomkitty.verycoolminecraftmmorpg.util.NumberUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModifiableItem extends CustomItem {

    public ModifiableItem(CustomItemBuilder builder) {
        super(builder);
    }

    @Override
    public CustomItemInstance fromItemStack(ItemStack item) {
        return new ModifiableItemInstance(this, item);
    }

    @Override
    protected void addLoreAfterStats(List<Component> lore, CustomItemInstance instance) {
        addModifiersToLore(lore, instance);
    }

    private void addModifiersToLore(List<Component> lore, CustomItemInstance instance) {

        if (!(instance instanceof ModifiableItemInstance minstance))
            return;

        List<ItemModifierInstance> enchants = new ArrayList<>();

        for (ItemModifierInstance modifierInstance : minstance.modifiers) {
            if (modifierInstance.modifier instanceof EnchantmentModifier) {
                enchants.add(modifierInstance);
            }
        }

        if (!enchants.isEmpty()) {
            lore.add(Component.text("ᴇɴᴄʜᴀɴᴛᴍᴇɴᴛѕ: ").color(rarity.getColor()).decoration(TextDecoration.ITALIC, false));

            for (ItemModifierInstance enchant : enchants) {
                lore.add(Component.text("  " + ((EnchantmentModifier) enchant.modifier).getName() + " " + NumberUtil.toRoman(enchant.level), NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            }
        }

        if (!lore.getLast().equals(Component.empty())) { lore.add(Component.empty()); }
    }
}
