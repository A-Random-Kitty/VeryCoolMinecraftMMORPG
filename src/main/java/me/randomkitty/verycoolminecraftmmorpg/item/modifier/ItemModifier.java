package me.randomkitty.verycoolminecraftmmorpg.item.modifier;

import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;

public abstract class ItemModifier {

    private final String key;

    public ItemModifier(String key) {
        this.key = key;
    }

    public String getKey() { return key; }

    public abstract void applyAdditiveStatBonuses(PlayerAttributes attributes, int level);
    public abstract void applyMultiStatBonuses(PlayerAttributes attributes, int level);

}
