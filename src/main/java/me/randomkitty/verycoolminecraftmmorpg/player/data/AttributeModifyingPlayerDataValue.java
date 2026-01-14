package me.randomkitty.verycoolminecraftmmorpg.player.data;

import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;

public interface AttributeModifyingPlayerDataValue {
    void applyAdditiveAttributes(PlayerAttributes attributes);
    void applyMultiplicativeAttributes(PlayerAttributes attributes);
}
