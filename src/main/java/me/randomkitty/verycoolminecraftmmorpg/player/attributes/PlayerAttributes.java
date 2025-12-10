package me.randomkitty.verycoolminecraftmmorpg.player.attributes;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

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

    private static BukkitTask displayAttributesTask;

    public static void startDisplayAttributesTask() {
        displayAttributesTask = Bukkit.getScheduler().runTaskTimer(VeryCoolMinecraftMMORPG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for (PlayerAttributes attributes : playerAttributes.values()) {
                    attributes.updateActionbar();
                }
            }
        }, 30, 30);
    }

    public static void stopDisplayPlayerAttributesTask() {

    }

    public static void calculateAttributes(Player player) {
        PlayerAttributes attributes = getAttributes(player);
        if (attributes != null) {
            attributes.calculateAttributes(player.getInventory().getItemInMainHand(), player.getInventory().getArmorContents());
        }
    }

    public static void calculateAttributes(Player player, ItemStack newMainHand) {
        PlayerAttributes attributes = getAttributes(player);
        if (attributes != null) {
            attributes.calculateAttributes(newMainHand, player.getInventory().getArmorContents());
        }
    }

    public static void calculateAttributes(Player player, ItemStack[] newArmorContents) {
        PlayerAttributes attributes = getAttributes(player);
        if (attributes != null ) {
            attributes.calculateAttributes(player.getInventory().getItemInMainHand(), newArmorContents);
        }
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
        return Math.min(maxDisplayHealth, Math.max(minDisplayHealth, health / 50));
    }

    public void calculateAttributes(ItemStack mainHandItemStack, ItemStack[] armorContents) {
        defense = 0; // 🛡
        health = 100; // ❤
        intelligence = 100;  // ☄
        damage = 5;
        critChance = 15;
        critDamage = 30;

        if (mainHandItemStack != null) {
            CustomItemInstance mainHand = CustomItems.fromItemStack(mainHandItemStack);

            if (mainHand != null && mainHand.baseItem.getSlot() == EquipmentSlot.HAND)
                addAttributes(mainHand);
        }

        {
            // Calculate total damage
            totalDamage = damage;
            totalCriticalDamage = damage * (1 + (critDamage / 100));
        }

        player.setHealthScale(getDisplayMaxHealth());
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
        updateActionbar();
    }

    private void addAttributes(CustomItemInstance item) {
        defense += item.baseItem.getDefense();
        health += item.baseItem.getHealth();
        intelligence += item.baseItem.getIntelligence();
        damage += item.baseItem.getDamage();
        critChance = item.baseItem.getCriticalChance();
        critDamage = item.baseItem.getCriticalDamage();
    }

    public void updateActionbar() {
        player.sendActionBar(Component.text(StringUtil.formatedDouble(player.getHealth()) + "/" + StringUtil.formatedDouble(health) + "❤").color(NamedTextColor.RED));
    }
}
