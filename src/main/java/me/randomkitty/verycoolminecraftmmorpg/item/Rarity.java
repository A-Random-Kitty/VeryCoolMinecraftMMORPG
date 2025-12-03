package me.randomkitty.verycoolminecraftmmorpg.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum Rarity {

    // DO NOT REORDER THESE

    COMMON("COMMON", NamedTextColor.WHITE),
    UNCOMMON("UNCOMMON", NamedTextColor.GREEN),
    RARE("RARE", NamedTextColor.BLUE),
    VERY_RARE("VERY RARE", NamedTextColor.DARK_BLUE),
    EPIC("EPIC", NamedTextColor.DARK_PURPLE),
    LEGENDARY("LEGENDARY", NamedTextColor.GOLD),
    FABLED("FABLED", NamedTextColor.RED),
    MYTHICAL("MYTHICAL", NamedTextColor.LIGHT_PURPLE),

    STRANGE("STRANGE", NamedTextColor.YELLOW);

    private final String text;
    private final TextColor color;

    Rarity(String text, TextColor color) {
        this.text = text;
        this.color = color;
    }

    public String getText(){
        return text;
    }

    public TextColor getColor() {
        return color;
    }

    public TextComponent getComponent() {
        return Component.text(text, color);
    }
}
