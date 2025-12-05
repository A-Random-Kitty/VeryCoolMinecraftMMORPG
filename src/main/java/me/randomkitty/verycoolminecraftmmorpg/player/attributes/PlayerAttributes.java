package me.randomkitty.verycoolminecraftmmorpg.player.attributes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    private static final int minDisplayHealth = 20;
    private static final int maxDisplayHealth = 40;

    private final Player player;

    public double defense;
    public double maxHealth;
    public double health;
    public double damage;
    public double critChance;
    public double critDamage;

    private PlayerAttributes(Player player) {
        this.player = player;
        calculateAttributes();
    }

    public double getDisplayMaxHealth() {
        return Math.max(maxDisplayHealth, Math.min(minDisplayHealth, maxHealth / 50));
    }

    public double getDisplayHealth() {
        return Math.ceil(health/maxHealth * getDisplayMaxHealth());
    }

    public static void calculateAttributes(Player player) {
        PlayerAttributes

        ItemStack hand = player
    }
}
