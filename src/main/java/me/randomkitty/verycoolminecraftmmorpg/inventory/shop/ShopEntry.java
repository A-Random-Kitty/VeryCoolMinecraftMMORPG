package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopEntry implements ConfigurationSerializable {

    protected String key;

    private CustomItem item;
    int amount;

    private Map<CustomItem, Integer> itemsCost;
    private double coinsCost;

    public boolean buy(Player player) {
        return false;
    }

    public ShopEntry(CustomItem item, int amount, Map<CustomItem, Integer> itemsCost, double coinsCost, String key) {
        this.item = item;
        this.amount = amount;
        this.itemsCost = itemsCost;
        this.coinsCost = coinsCost;
    }

    public ItemStack getGuiItem() {
        ItemStack guiItem = item.newItemStack();

        ItemMeta meta = guiItem.getItemMeta();
        List<Component> lore = meta.lore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(Component.empty());
        lore.add(Component.text("Cost: ").color(NamedTextColor.WHITE));

        if (coinsCost != 0) {
            lore.add(Component.text(String.format("%,1.0f", coinsCost) + " Coins").color(NamedTextColor.GOLD));
        }

        for (Map.Entry<CustomItem, Integer> entry : itemsCost.entrySet()) {
            TextComponent component = entry.getKey().getColoredName();

            if (entry.getValue() > 1) {
                component.append(Component.text("x" + entry.getValue()).color(NamedTextColor.GRAY));
            }

            lore.add(component);
        }

        guiItem.setItemMeta(meta);
        return guiItem;
    }

    public void setItemsCost(Map<CustomItem, Integer> cost) {
        this.itemsCost = cost;
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

        return serializedData;
    }

    public static ShopEntry deserialize(ConfigurationSection section) {
        String key = section.getName();

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

        return new ShopEntry(item, amount, itemsCost, coinsCost, key);
    }
}
