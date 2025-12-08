package me.randomkitty.verycoolminecraftmmorpg.player.attributes;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributes {

    private static Map<Player, PlayerAttributes> playerAttributes = new HashMap<>();

    public static void initPlayer(Player player) {
        playerAttributes.put(player, new PlayerAttributes(player));
    }

    public static void removePlayer(Player player) {
        playerAttributes.remove(player);


    }

    public static PlayerAttributes getAttributes(Player player) { return playerAttributes.get(player); }

    public static void calculateAttributes(Player player) {
        PlayerAttributes attributes = getAttributes(player);
        attributes.calculateAttributes();
    }

    private static final int minDisplayHealth = 20;
    private static final int maxDisplayHealth = 40;

    private final Player player;

    public double health;
    public double defense;
    public double intelligence;
    public double damage;
    public double critChance;
    public double critDamage;

    public double totalDamage;
    public double totalCriticalDamage;

    private PlayerAttributes(Player player) {
        this.player = player;
    }

    private double getDisplayMaxHealth() {
        return Math.max(maxDisplayHealth, Math.min(minDisplayHealth, health / 50));
    }

    public void calculateAttributes() {
        defense = 0;
        health = 0;
        intelligence = 0;
        damage = 0;
        critChance = 0;
        critDamage = 0;

        CustomItemInstance mainHand = CustomItems.fromItemStack(player.getInventory().getItemInMainHand());
        if (mainHand.baseItem.getSlot() == EquipmentSlot.HAND)
            addAttributes(mainHand);

        {
            // Calculate total damage
            totalDamage = damage;
            totalCriticalDamage = damage * (1 + critDamage);
        }


        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(getDisplayMaxHealth());
    }

    private void addAttributes(CustomItemInstance item) {
        defense += item.baseItem.getDefense();
        health += item.baseItem.getHealth();
        intelligence += item.baseItem.getIntelligence();
        damage += item.baseItem.getDamage();
        critChance = item.baseItem.getCriticalChance();
        critDamage = item.baseItem.getCriticalDamage();
    }
}
