package me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers;

public class SimpleStatBoostModifier {

    protected final double damage;
    protected final double damageMulti;
    protected final double criticalDamage;
    protected final double criticalChance;

    protected final double health;
    protected final double defense;
    protected final double mana;
    protected final double speed;

    public SimpleStatBoostModifier(SimpleStatBoostModifierBuilder builder) {
        this.damage = builder.damage;
        this.damageMulti = builder.damageMulti;
        this.criticalDamage = builder.criticalDamage;
        this.criticalChance = builder.criticalChance;

        this.health = builder.health;
        this.defense = builder.defense;
        this.mana = builder.mana;
        this.speed = builder.speed;
    }

    public double getDamage() {
        return damage;
    }

    public double getDamageMulti() {
        return damageMulti;
    }

    public double getCriticalDamage() {
        return criticalDamage;
    }

    public double getCriticalChance() {
        return criticalChance;
    }

    public double getHealth() {
        return health;
    }

    public double getDefense() {
        return defense;
    }

    public double getMana() {
        return mana;
    }

    public double getSpeed() {
        return speed;
    }
}
