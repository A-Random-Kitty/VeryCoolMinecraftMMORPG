package me.randomkitty.verycoolminecraftmmorpg.item.items;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifiers;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ModifiableItemInstance extends CustomItemInstance {

    public List<ItemModifierInstance> modifiers;

    public ModifiableItemInstance(ModifiableItem base, ItemStack item) {
        super(base, item);
        this.modifiers = ItemModifiers.fromDataContainer(item.getPersistentDataContainer());
    }

    public ModifiableItemInstance(CustomItem item) {
        super(item);
        this.modifiers = new ArrayList<>();
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack item = super.toItemStack();

        item.editPersistentDataContainer(container -> {

            PersistentDataContainer modifiersContainer = container.getAdapterContext().newPersistentDataContainer();

            for (ItemModifierInstance modifierInstance : modifiers) {
                modifiersContainer.set(new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, modifierInstance.modifier.getKey()), PersistentDataType.INTEGER, modifierInstance.level);
            }

            container.set(ItemModifiers.ITEM_MODIFIERS_KEY, PersistentDataType.TAG_CONTAINER, modifiersContainer);
        });

        return item;
    }

    @Override
    public void addAttributes(PlayerAttributes attributes) {
        super.addAttributes(attributes);

        for (ItemModifierInstance modifier : this.modifiers) {
            modifier.modifier.applyAdditiveStatBonuses(attributes, modifier.level);
        }
    }

    @Override
    public void applyItemMultipliers(PlayerAttributes attributes) {
        super.applyItemMultipliers(attributes);

        for (ItemModifierInstance modifier : this.modifiers) {
            modifier.modifier.applyMultiStatBonuses(attributes, modifier.level);
        }
    }

}
