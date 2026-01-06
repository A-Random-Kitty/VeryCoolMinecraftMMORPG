package me.randomkitty.verycoolminecraftmmorpg.events;

import io.papermc.paper.event.entity.EntityDyeEvent;
import me.randomkitty.verycoolminecraftmmorpg.inventory.enchanting.EnchantingTableMenu;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItemInstance;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class InteractEvents implements Listener {

    private final Set<BlockType> cancelInteractBlocks = Set.of(
            BlockType.CRAFTING_TABLE, BlockType.CRAFTER,
            BlockType.FURNACE, BlockType.BLAST_FURNACE, BlockType.SMOKER, BlockType.CAMPFIRE, BlockType.SOUL_CAMPFIRE,
            BlockType.CHEST, BlockType.TRAPPED_CHEST, BlockType.BARREL, BlockType.ENDER_CHEST, BlockType.DECORATED_POT,
            BlockType.NOTE_BLOCK, BlockType.JUKEBOX,
            BlockType.ANVIL, BlockType.CHIPPED_ANVIL, BlockType.DAMAGED_ANVIL,
            BlockType.SMITHING_TABLE, BlockType.CARTOGRAPHY_TABLE, BlockType.LOOM, BlockType.GRINDSTONE, BlockType.STONECUTTER,
            BlockType.ENCHANTING_TABLE, BlockType.BREWING_STAND, BlockType.BEACON, BlockType.END_PORTAL_FRAME, BlockType.CHISELED_BOOKSHELF,
            BlockType.BLACK_BED, BlockType.BLUE_BED, BlockType.BROWN_BED, BlockType.LIGHT_BLUE_BED, BlockType.CYAN_BED, BlockType.GRAY_BED, BlockType.GREEN_BED, BlockType.LIGHT_GRAY_BED, BlockType.LIME_BED, BlockType.MAGENTA_BED, BlockType.ORANGE_BED, BlockType.PINK_BED, BlockType.PURPLE_BED, BlockType.RED_BED, BlockType.WHITE_BED, BlockType.YELLOW_BED,
            BlockType.BLACK_SHULKER_BOX, BlockType.BLUE_SHULKER_BOX, BlockType.BROWN_SHULKER_BOX, BlockType.LIGHT_BLUE_SHULKER_BOX, BlockType.CYAN_SHULKER_BOX, BlockType.GRAY_SHULKER_BOX, BlockType.GREEN_SHULKER_BOX, BlockType.LIGHT_GRAY_SHULKER_BOX, BlockType.LIME_SHULKER_BOX, BlockType.MAGENTA_SHULKER_BOX, BlockType.ORANGE_SHULKER_BOX, BlockType.PINK_SHULKER_BOX, BlockType.PURPLE_SHULKER_BOX, BlockType.RED_SHULKER_BOX, BlockType.WHITE_SHULKER_BOX, BlockType.YELLOW_SHULKER_BOX
    );

    private final Set<Material> cancelInteractItems = Set.of(Material.BONE_MEAL, Material.FLINT_AND_STEEL);

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            onPlayerLeftClick(event);
            onPlayerLeftClickBlock(event);
        }
        else if (event.getAction() == Action.LEFT_CLICK_AIR) {
            onPlayerLeftClick(event);
            onPlayerLeftClickAir(event);
        }
        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            onPlayerRightClick(event);
            onPlayerRightClickBlock(event);
        }
        else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            onPlayerRightClick(event);
            onPlayerRightClickAir(event);
        }
    }

    public void onPlayerLeftClick(PlayerInteractEvent event) {

    }

    public void onPlayerLeftClickAir(PlayerInteractEvent event) {

    }

    public void onPlayerLeftClickBlock(PlayerInteractEvent event) {

    }

    public void onPlayerRightClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item != null) {
            if (cancelInteractItems.contains(event.getItem().getType()) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setUseItemInHand(Event.Result.DENY);
            }
        }

        PlayerAttributes attributes = PlayerAttributes.getAttributes(event.getPlayer());

        if (attributes != null) {
            attributes.onUseItem(event);
        }
    }

    public void onPlayerRightClickAir(PlayerInteractEvent event) {

    }

    public void onPlayerRightClickBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block != null) {
            if (cancelInteractBlocks.contains(block.getType().asBlockType()) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }

            if (block.getType() == Material.ENCHANTING_TABLE) {
                player.openInventory(new EnchantingTableMenu().getInventory());
            }
        }


    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDyed(EntityDyeEvent event) {
        event.setCancelled(true);
    }
}
