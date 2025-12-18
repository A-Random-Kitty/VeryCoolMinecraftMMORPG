package me.randomkitty.verycoolminecraftmmorpg.item;

import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifier;

import java.util.List;

public class CustomItemInstance {

    public CustomItem baseItem;
    private List<ItemModifier> modifiers;

    public CustomItemInstance(CustomItem item, List<ItemModifier> modifiers) {
        this.baseItem = item;
        this.modifiers = modifiers;
    }

}
