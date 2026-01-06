package me.randomkitty.verycoolminecraftmmorpg.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public enum ItemType {

    MATERIAL("MATERIAL"),
    ITEM("ITEM"),
    SWORD("SWORD"),
    BOW("BOW"),
    WAND("WAND"),
    AXE("AXE"),
    PICKAXE("PICKAXE"),
    SHOVEL("SHOVEL"),
    HELMET("HELMET"),
    CHESTPLATE("CHESTPLATE"),
    LEGGINGS("LEGGINGS"),
    BOOTS("BOOTS");

    private final String text;

    ItemType(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public TextComponent getComponent() {
        return Component.text(text);
    }
}
