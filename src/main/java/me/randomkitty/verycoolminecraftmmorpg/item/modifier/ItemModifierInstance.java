package me.randomkitty.verycoolminecraftmmorpg.item.modifier;

public class ItemModifierInstance {
    public ItemModifier modifier;
    public int level;

    public ItemModifierInstance(ItemModifier modifier, int level) {
        this.modifier = modifier;
        this.level = level;
    }
}
