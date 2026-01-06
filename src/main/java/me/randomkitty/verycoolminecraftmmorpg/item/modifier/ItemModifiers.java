package me.randomkitty.verycoolminecraftmmorpg.item.modifier;


import io.papermc.paper.persistence.PersistentDataContainerView;
import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.SharpnessEnchantModifier;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemModifiers {

    public static final NamespacedKey ITEM_MODIFIERS_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "modifiers");

    private static final Map<String, ItemModifier> modifiers = new HashMap<>();

    public static final SharpnessEnchantModifier SHARPNESS_ENCHANT;

    public static ItemModifier getModifier(String key) { return modifiers.get(key); }

    public static List<ItemModifierInstance> fromDataContainer(PersistentDataContainerView container) {
        List<ItemModifierInstance> containerModifiers = new ArrayList<>();
        PersistentDataContainer modifiersContainer = container.get(ITEM_MODIFIERS_KEY, PersistentDataType.TAG_CONTAINER);

        if (modifiersContainer != null) {

            for (NamespacedKey key : modifiersContainer.getKeys()) {
                ItemModifier modifier = modifiers.get(key.getKey());

                if (modifier != null) {
                    containerModifiers.add(new ItemModifierInstance(modifier, modifiersContainer.get(key, PersistentDataType.INTEGER)));
                }
            }
        }

        return containerModifiers;
    }

    private static ItemModifier register(ItemModifier modifier) {
        modifiers.put(modifier.getKey(), modifier);
        return modifier;
    }


    static {
        SHARPNESS_ENCHANT = (SharpnessEnchantModifier) register(new SharpnessEnchantModifier("sharpness_enchant"));
    }

}
