package me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemBuilder;

public class SimpleStatBoostModifierBuilder {

    public double damage = 0;
    public double criticalDamage = 0;
    public double criticalChance = 0;

    public double health = 0;
    public double defense = 0;
    public double mana = 0;

    public SimpleStatBoostModifierBuilder setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public SimpleStatBoostModifierBuilder setCriticalDamage(double criticalDamage) {
        this.criticalDamage = criticalDamage;
        return this;
    }

    public SimpleStatBoostModifierBuilder setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
        return this;
    }

    public SimpleStatBoostModifierBuilder setHealth(double health) {
        this.health = health;
        return this;
    }

    public SimpleStatBoostModifierBuilder setDefense(double defense) {
        this.defense = defense;
        return this;
    }

    public SimpleStatBoostModifierBuilder setMana(double mana) {
        this.mana = mana;
        return this;
    }

}
