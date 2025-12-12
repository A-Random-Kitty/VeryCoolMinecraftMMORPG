package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ShopEntry implements ConfigurationSerializable {

    private CustomItem item;
    int amount;

    private Map<CustomItem, Integer> itemsCost = new HashMap<>();
    private double coinsCost;

    public boolean buy(Player player) {
        return false;
    }

    public ShopEntry(CustomItem item, int amount, Map<CustomItem, Integer> itemsCost, double coinsCost) {
        this.item = item;
        this.amount = amount;
        this.itemsCost = itemsCost;
        this.coinsCost = coinsCost;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> serializedData = new HashMap<>();

        serializedData.put("reward.item", item.getKey());
        serializedData.put("reward.amount", item.getKey());

        serializedData.put("cost.coins", coinsCost);

        for (Map.Entry<CustomItem, Integer> entry : itemsCost.entrySet()) {
            serializedData.put("cost.item." + entry.getKey().getKey(), entry.getValue());
        }

        return Map.of();
    }

    public static ShopEntry deserialize(ConfigurationSection section) {
        CustomItem item = CustomItems.get(section.getString("reward.item"));
        int amount = section.getInt("reward.amount");

        Map<CustomItem, Integer> itemsCost = new HashMap<>();
        ConfigurationSection itemsSection = section.getConfigurationSection("cost.item");

        if (itemsSection != null) {
            for (String s : itemsSection.getKeys(false)) {
                CustomItem itemCost = CustomItems.get(s);
                int itemCostAmount = itemsSection.getInt(s);
                if (itemCost != null && itemCostAmount != 0) {
                    itemsCost.put(itemCost, itemCostAmount);
                }
            }
        }

        double coinsCost = section.getDouble("cost.coins");

        return new ShopEntry(item, amount, itemsCost, coinsCost);
    }
}
