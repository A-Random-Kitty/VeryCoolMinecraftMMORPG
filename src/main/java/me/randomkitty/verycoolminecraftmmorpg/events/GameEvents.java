package me.randomkitty.verycoolminecraftmmorpg.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.entity.EntityDyeEvent;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomSheep;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.Set;

public class GameEvents implements Listener {

    private final Set<BlockType> cancelInteractBlocks = Set.of(
            BlockType.CRAFTING_TABLE, BlockType.CRAFTER,
            BlockType.BLACK_BED, BlockType.BLUE_BED, BlockType.BROWN_BED, BlockType.LIGHT_BLUE_BED, BlockType.CYAN_BED, BlockType.GRAY_BED, BlockType.GREEN_BED, BlockType.LIGHT_GRAY_BED, BlockType.LIME_BED, BlockType.MAGENTA_BED, BlockType.ORANGE_BED, BlockType.PINK_BED, BlockType.PURPLE_BED, BlockType.RED_BED, BlockType.WHITE_BED, BlockType.YELLOW_BED,
            BlockType.FURNACE, BlockType.BLAST_FURNACE, BlockType.SMOKER,
            BlockType.CHEST, BlockType.TRAPPED_CHEST, BlockType.BARREL, BlockType.ENDER_CHEST,
            BlockType.ANVIL, BlockType.CHIPPED_ANVIL, BlockType.DAMAGED_ANVIL,
            BlockType.SMITHING_TABLE, BlockType.CARTOGRAPHY_TABLE, BlockType.LOOM,
            BlockType.ENCHANTING_TABLE, BlockType.BEACON, BlockType.END_PORTAL_FRAME
    );

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

    }

    public void onPlayerRightClickAir(PlayerInteractEvent event) {

    }

    public void onPlayerRightClickBlock(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block != null && cancelInteractBlocks.contains(block.getType().asBlockType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHeldItem(PlayerItemHeldEvent event) {
        PlayerAttributes.calculateAttributes(event.getPlayer());
    }

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        PlayerAttributes.calculateAttributes(event.getPlayer());
    }

    @EventHandler
    public void onEntityDyed(EntityDyeEvent event) {
        event.setCancelled(true);
    }
}
