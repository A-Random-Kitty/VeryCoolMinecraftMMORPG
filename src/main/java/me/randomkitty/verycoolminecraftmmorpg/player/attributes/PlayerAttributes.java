package me.randomkitty.verycoolminecraftmmorpg.player.attributes;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.player.data.AttributeModifyingPlayerDataValue;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerData;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerDataValue;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class PlayerAttributes {

    public static final NamespacedKey PROJECTILE_DAMAGE_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "projectile_damage");
    public static final NamespacedKey PROJECTILE_CRITICAL_DAMAGE_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "projectile_critical_damage");
    public static final NamespacedKey PROJECTILE_CRITICAL_CHANCE_KEY = new NamespacedKey(VeryCoolMinecraftMMORPG.NAMESPACE, "projectile_critical_chance");

    private static final Random random = new Random();

    private static final Map<Player, PlayerAttributes> playerAttributes = new HashMap<>();

    public static void initPlayer(Player player) {
        playerAttributes.put(player, new PlayerAttributes(player));
    }
    public static void removePlayer(Player player) {
        playerAttributes.remove(player);
    }
    public static PlayerAttributes getAttributes(Player player) { return playerAttributes.get(player); }

    private static BukkitTask updateAttributesTask;

    public static void startUpdateAttributesTask() {
        updateAttributesTask = Bukkit.getScheduler().runTaskTimer(VeryCoolMinecraftMMORPG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for (PlayerAttributes attributes : playerAttributes.values()) {
                    attributes.player.setHealth(Math.min(attributes.player.getHealth() + attributes.healthRegen, attributes.health));
                    attributes.currentMana = Math.min(attributes.currentMana + attributes.healthRegen, attributes.mana);
                    attributes.updateActionbar();
                }
            }
        }, 20, 20);
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

    private CustomItemInstance mainHandItem;

    public double health;
    public double defense;
    public double mana;
    public double damage;
    public double critChance;
    public double critDamage;

    public double healthRegen;
    public double manaRegen;

    public double totalDamage;
    public double totalCriticalDamage;

    public double currentMana;

    private PlayerAttributes(Player player) {
        this.player = player;
    }

    public void onUseItem(PlayerInteractEvent event) {
        if (mainHandItem != null)
            mainHandItem.baseItem.onUseItem(event);
    }


    public void calculateAttributes(ItemStack mainHandItemStack, ItemStack[] armorContents) {
        defense = 0; // 🛡
        health = 50; // ❤
        mana = 50;  // ☄
        damage = 0; // 🗡
        critChance = 15; // ☠
        critDamage = 30; // ⚔
        PlayerData customPlayerData = PlayerData.getAttributes(player);
        final List<CustomItemInstance> gear = new ArrayList<>();

        {
            if (mainHandItemStack != null) {
                mainHandItem = CustomItems.fromItemStack(mainHandItemStack);

                if (mainHandItem != null && mainHandItem.baseItem.getSlot() == EquipmentSlot.HAND) {
                    gear.add(mainHandItem);
                    mainHandItem.addAttributes(this);
                }

            }

            for (ItemStack armorItem : armorContents) {
                if (armorItem != null) {
                    CustomItemInstance customArmorItem = CustomItems.fromItemStack(armorItem);

                    if (customArmorItem != null) {
                        customArmorItem.addAttributes(this);
                        gear.add(customArmorItem);
                    }
                }
            }

            for (PlayerDataValue value : customPlayerData.getDataValues()) {
                if (value instanceof AttributeModifyingPlayerDataValue attributeModifyingPlayerDataValue) {
                    attributeModifyingPlayerDataValue.applyAdditiveAttributes(this);
                }
            }
        }

        {
            if (damage < 5) {
                damage = 5;
            }

            totalDamage = damage;
            totalCriticalDamage = damage * (1 + (critDamage / 100));
        }

        healthRegen = health / 50;
        manaRegen = mana / 50;

        for (CustomItemInstance gearItem : gear) {
            gearItem.applyItemMultipliers(this);
        }


        for (PlayerDataValue value : customPlayerData.getDataValues()) {
            if (value instanceof AttributeModifyingPlayerDataValue attributeModifyingPlayerDataValue) {
                attributeModifyingPlayerDataValue.applyMultiplicativeAttributes(this);
            }
        }

        if (currentMana > mana)
            currentMana = mana;


        player.setHealthScale(getDisplayMaxHealth());
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
        updateActionbar();
    }

    private double getDisplayMaxHealth() {
        return Math.min(maxDisplayHealth, Math.max(minDisplayHealth, health / 50));
    }

    public void updateActionbar() {
        player.sendActionBar(
                Component.text(StringUtil.formatedDouble(player.getHealth()) + "/" + StringUtil.noDecimalDouble(health) + "❤").color(NamedTextColor.RED)
                .append(Component.text("               "))
                .append(Component.text(StringUtil.noDecimalDouble(mana) + "/" + StringUtil.noDecimalDouble(mana) + "☄").color(NamedTextColor.AQUA))
        );
    }

    public boolean isCrit() {
        return (random.nextDouble() <= (critChance / 100));
    }

    public double getDamageAfterDefence(double damageAmount) {
        return damageAmount / (1 + (defense / 100));
    }
}
