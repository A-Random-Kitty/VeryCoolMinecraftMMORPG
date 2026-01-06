package me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers;

import net.kyori.adventure.text.TextComponent;

import java.util.List;

public interface EnchantmentModifier {
    String getKey();
    String getName();
    List<TextComponent> getLore(int level);
}
