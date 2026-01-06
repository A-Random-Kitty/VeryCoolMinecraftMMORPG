package me.randomkitty.verycoolminecraftmmorpg.inventory.enchanting;

import me.randomkitty.verycoolminecraftmmorpg.inventory.CustomInventory;
import me.randomkitty.verycoolminecraftmmorpg.item.items.EnchantedBookItem;
import me.randomkitty.verycoolminecraftmmorpg.item.items.ModifiableItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifierInstance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RemoveEnchantConfirmMenu implements CustomInventory {

    private static final ItemStack grindStoneItem;

    private static final char[] layout = {
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
            '_', '_', '_', '_', 'E', '_', '_', '_', '_',
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
            '_', '_', '_', '_', 'I', '_', '_', '_', '_',
            '_', '_', '_', '_', 'G', '_', '_', '_', '_',
            '_', '_', '_', '_', '_', '_', '_', '_', '_',
    };

    private static final ItemStack[] contents = new ItemStack[54];

    private static int grindStoneSlot;
    private static int bookSlot;
    private static int itemSlot;

    static {
        grindStoneItem = new ItemStack(Material.GRINDSTONE);
        grindStoneItem.editMeta(meta -> {
            meta.itemName(Component.text("Remove Enchant", NamedTextColor.RED));
            meta.lore(List.of(Component.text("Click to remove enchant", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        });

        for (int i = 0; i < 54; i++) {
            if (layout[i] == '_') {
                contents[i] = EnchantingTableMenu.blankItem;
            } else if (layout[i] == 'G') {
                contents[i] = grindStoneItem;
                grindStoneSlot = i;
            } else if (layout[i] == 'E') {
                bookSlot = i;
            } else if (layout[i] == 'I') {
                itemSlot = i;
            }
        }
    }


    private Inventory inventory;
    private final ModifiableItemInstance item;
    private final ItemModifierInstance enchant;
    private final int itemSlotInPlayerInventory;

    public RemoveEnchantConfirmMenu(ModifiableItemInstance item, ItemModifierInstance enchant, int itemSlotInPlayerInventory) {
        this.item = item;
        this.enchant = enchant;
        this.itemSlotInPlayerInventory = itemSlotInPlayerInventory;

        inventory = Bukkit.createInventory(this, 54, Component.text("Enchanting Table"));

        inventory.setContents(contents);
        inventory.setItem(bookSlot, EnchantedBookItem.fromEnchantInstance(enchant).toItemStack());
        inventory.setItem(itemSlot, item.toItemStack());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);

        Inventory clicked = event.getClickedInventory();

        if (clicked != null && clicked.getHolder() == this) {
            if (event.getSlot() == grindStoneSlot) {

                this.item.modifiers.remove(enchant);
                event.getWhoClicked().getInventory().setItem(itemSlotInPlayerInventory, this.item.toItemStack());
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().getLocation().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_GRINDSTONE_USE, 3f, 1f);


            }
        }
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {

    }

    @Override
    public void handleOpen(InventoryOpenEvent event) {

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
