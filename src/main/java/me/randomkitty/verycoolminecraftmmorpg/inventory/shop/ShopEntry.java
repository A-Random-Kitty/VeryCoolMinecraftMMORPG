package me.randomkitty.verycoolminecraftmmorpg.inventory.shop;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerCurrency;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

    public ShopEntry(CustomItem item, int amount, Map<CustomItem, Integer> itemsCost, double coinsCost, String key) {
        this.key = key;
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
        lore.add(Component.text("Cost: ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));

        if (coinsCost > 0) {
            lore.add(Component.text(String.format("  %,1.0f", coinsCost) + " Coins").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        }

        for (Map.Entry<CustomItem, Integer> entry : itemsCost.entrySet()) {
            TextComponent component = Component.text("  ").append(entry.getKey().getColoredName());

            if (entry.getValue() >= 1) {
                component = component.append(Component.text(" x" + entry.getValue()).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            }

            lore.add(component);
        }

        meta.lore(lore);

        guiItem.setItemMeta(meta);
        return guiItem;
    }

    public void attemptPurchase(Player player) {
        // this logic is way too complicated and probably can be improved
        PlayerCurrency currency = PlayerCurrency.getCurrency(player.getUniqueId());

        if (coinsCost > currency.getCoins()) {
            player.sendMessage(Component.text("You are too broke to buy this!").color(NamedTextColor.RED));
            return;
        }

        Map<CustomItem, Integer> availableItems = new HashMap<>();
        Map<Integer, CustomItem> slotItemType = new HashMap<>(); // for optimization (probably helps)

        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            CustomItem item = CustomItems.getCustomItem(contents[i]);

            if (item != null) {
                slotItemType.put(i, item);
                if (availableItems.containsKey(item)) {
                    availableItems.put(item, availableItems.get(item) + contents[i].getAmount());
                } else {
                    availableItems.put(item, contents[i].getAmount());
                }
            }
        }

        boolean canAfford = true;

        for (Map.Entry<CustomItem, Integer> entry : itemsCost.entrySet()) {
            if (availableItems.containsKey(entry.getKey())) {
                if (!(availableItems.get(entry.getKey()) >= entry.getValue())) {
                    canAfford = false;
                    break;
                }
            } else {
                canAfford = false;
                break;
            }
        }

        if (canAfford) {
            for (Map.Entry<CustomItem, Integer> entry : itemsCost.entrySet()) {
                int remaining = entry.getValue();

                for (int i = 0; i < contents.length; i++) {

                    if (slotItemType.get(i) == entry.getKey()) {
                        int amount = contents[i].getAmount();
                        if (amount > remaining) {
                            remaining -= amount;
                            contents[i].setAmount(amount - remaining);
                            break;
                        } else if (remaining == amount) {
                            remaining -= amount;
                            player.getInventory().setItem(i, null);
                            break;
                        } else {
                            remaining -= amount;
                            player.getInventory().setItem(i, null);
                        }

                    }
                }

                if (remaining > 0) {
                    throw new RuntimeException("Very big scary shop error (should not happen very bad)");
                }

            }

            ItemStack newItem = item.newItemStack();
            newItem.setAmount(amount);
            player.give(newItem);
        }

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
