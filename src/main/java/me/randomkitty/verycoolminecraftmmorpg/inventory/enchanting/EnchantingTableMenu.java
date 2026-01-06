package me.randomkitty.verycoolminecraftmmorpg.inventory.enchanting;

import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.item.items.EnchantedBookInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.items.EnchantedBookItem;
import me.randomkitty.verycoolminecraftmmorpg.item.items.ModifiableItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifier;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers.EnchantmentModifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnchantingTableMenu implements CustomInventory
{
    protected static final ItemStack blankItem;
    private static final ItemStack enchantmentSlotItemPlaceholder;
    private static final ItemStack enchantmentSlotItem;
    private static final ItemStack itemSlotItem;
    private static final ItemStack enchantTableItem;

    private static final char[] layout = {
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
            '_', '_', 'E', '_', 'E', '_', 'E', '_', '_',
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
            '_', '_', '_', '_', 'I', '_', '_', '_', '_',
            '_', '_', '_', '_', 'T', '_', '_', '_', '_',
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
    };

    private static final ItemStack[] contents = new ItemStack[54];

    private static final int[] enchantSlotLocations = new int[3];
    private static int itemSlotLocation;



    static {
        blankItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        blankItem.editMeta(itemMeta -> {
            itemMeta.itemName(Component.empty());
        });

        enchantmentSlotItemPlaceholder = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        enchantmentSlotItemPlaceholder.editMeta(itemMeta -> {
            itemMeta.itemName(Component.text("Empty Enchantment Slot", NamedTextColor.WHITE));
            itemMeta.lore(List.of(Component.text("Insert an item to enchant", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        });

        enchantmentSlotItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        enchantmentSlotItem.editMeta(itemMeta -> {
            itemMeta.itemName(Component.text("Empty Enchantment Slot", NamedTextColor.WHITE));
            itemMeta.lore(List.of(Component.text("Click an enchanted book to apply", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        });

        itemSlotItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        itemSlotItem.editMeta(itemMeta -> {
            itemMeta.itemName(Component.text("Insert Item", NamedTextColor.WHITE));
            itemMeta.lore(List.of(Component.text("Click an item in your inventory to insert it", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        });

        enchantTableItem = new ItemStack(Material.ENCHANTING_TABLE);
        enchantTableItem.editMeta(itemMeta -> {
            itemMeta.itemName(Component.text("Enchanting Table", NamedTextColor.LIGHT_PURPLE));
            itemMeta.lore(Arrays.asList(
                    Component.text("Enchant your items!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                    Component.text("Click an item in your inventory", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                    Component.text("Then click an enchant book to apply", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
            ));
        });

        int k = 0;

        for (int i = 0; i < 54; i++) {
            if (layout[i] == '_') {
                contents[i] = blankItem;
            } else if (layout[i] == 'E') {
                contents[i] = enchantmentSlotItemPlaceholder;
                enchantSlotLocations[k] = i;
                k++;
            } else if (layout[i] == 'I') {
                contents[i] = itemSlotItem;
                itemSlotLocation = i;
            } else if (layout[i] == 'T') {
                contents[i] = enchantTableItem;
            }
        }
    }

    private ModifiableItemInstance heldItem;
    private int heldSlot;
    private final List<ItemModifierInstance> enchantments;
    private final Inventory inventory;

    public EnchantingTableMenu() {
        this.inventory = Bukkit.createInventory(this, 54, Component.text("Enchanting Table"));
        inventory.setContents(contents);
        enchantments = new ArrayList<>();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        Inventory clicked = event.getClickedInventory();

        if (clicked != null && clicked.getHolder() == this) {

            int slot = event.getSlot();

            if (layout[slot] == 'E' && this.heldItem != null) {
                // Player clicked an enchantment slot

                int enchantIndex = -1;

                for (int i = 0; i < enchantSlotLocations.length; i++) {
                    if (enchantSlotLocations[i] == event.getSlot()) {
                        enchantIndex = i;
                    }
                }

                if (enchantIndex != -1 && enchantIndex < enchantments.size()) {
                    ItemModifierInstance enchantToRemove = enchantments.get(enchantIndex);

                    if (enchantToRemove != null) {
                        player.openInventory(new RemoveEnchantConfirmMenu(heldItem, enchantToRemove, heldSlot).getInventory());
                    }
                } else if (enchantIndex == -1) {
                    player.sendMessage(Component.text("Enchant index not found (scary error)", NamedTextColor.RED));
                    player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1f, 1f);
                }


            }

        } else if (clicked instanceof PlayerInventory) {

            ItemStack itemStack = clicked.getItem(event.getSlot());

            if (itemStack != null) {
                CustomItemInstance customItem = CustomItems.fromItemStack(itemStack);

                if (customItem instanceof ModifiableItemInstance modifiable) {

                    this.enchantments.clear();
                    this.heldItem = modifiable;
                    this.heldSlot = event.getSlot();

                    inventory.setItem(itemSlotLocation, itemStack);

                    for (ItemModifierInstance modifier : modifiable.modifiers) {
                        if (modifier.modifier instanceof EnchantmentModifier) {
                            enchantments.add(modifier);
                        }
                    }

                    if (enchantments.size() <= 3) {
                        for (int i = 0; i < 3; i++) {
                            if (i < enchantments.size()) {
                                ItemStack enchantBook = EnchantedBookItem.fromEnchantInstance(enchantments.get(i)).toItemStack();
                                enchantBook.editMeta(meta -> {
                                    List<Component> lore = meta.lore();
                                    if (lore == null)
                                        lore = new ArrayList<>();
                                    lore.add(Component.empty());
                                    lore.add(Component.text("Click to destroy", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                                    meta.lore(lore);
                                });
                                inventory.setItem(enchantSlotLocations[i], enchantBook);
                            } else {
                                inventory.setItem(enchantSlotLocations[i], enchantmentSlotItem);
                            }
                        }
                    } else {
                        player.sendMessage(Component.text("Item has too many enchantments to display", NamedTextColor.RED));
                        player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1f, 1f);
                        player.closeInventory();
                    }

                    player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);

                } else if (customItem instanceof EnchantedBookInstance book) {

                    if (heldItem != null) {
                        if (enchantments.size() < 3) {
                            if (book.enchant != null && book.level > 0) {
                                boolean hasEnchant = false;

                                for (ItemModifierInstance e : enchantments) {
                                    if (e.modifier == book.enchant) {
                                        hasEnchant = true;
                                    }
                                }

                                if (!hasEnchant) {
                                    player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                                    player.openInventory(new EnchantingConfirmMenu(heldItem, new ItemModifierInstance((ItemModifier) book.enchant, book.level), heldSlot, event.getSlot()).getInventory());
                                } else {
                                    player.sendMessage(Component.text("Item already has this enchantment", NamedTextColor.RED));
                                    player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1f, 1f);
                                }
                            } else {
                                player.sendMessage(Component.text("Your book appears to be bugged", NamedTextColor.RED));
                                player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1f, 1f);
                            }
                        } else {
                            player.sendMessage(Component.text("This item already has the maximum amount of enchants", NamedTextColor.RED));
                            player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1f, 1f);
                        }
                    } else {
                        player.sendMessage(Component.text("Insert an item first", NamedTextColor.RED));
                        player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1f, 1f);
                    }

                } else {
                    player.sendMessage(Component.text("Item is not enchantable", NamedTextColor.RED));
                    player.getLocation().getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1f, 1f);
                }
            }
        }
    }

//    private ItemStack getDisplayedEnchantment(ItemModifierInstance instance) {
//
//    }

    @Override
    public void handleClose(InventoryCloseEvent event) {

    }

    @Override
    public void handleOpen(InventoryOpenEvent event) {
        event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
    }

    @Override
    public void handleDrag(InventoryDragEvent event) {

    }

    @Override
    public void handleMoveItem(InventoryMoveItemEvent event) {

    }

    @Override
    public void handleDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
