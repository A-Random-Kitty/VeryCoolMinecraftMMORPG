package me.randomkitty.verycoolminecraftmmorpg.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.entity.EntityDyeEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomCreature;
import me.randomkitty.verycoolminecraftmmorpg.entities.visual.DisappearingTextDisplay;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.minecraft.world.entity.item.ItemEntity;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class DamageEvents implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {

            if (event instanceof EntityDamageByEntityEvent entityEvent) {

                if (entityEvent.getDamager() instanceof Player) {
                    event.setCancelled(true);
                    return;
                } else if (((CraftEntity) event.getEntity()).getHandle() instanceof CustomCreature) {
                    onCustomEntityDamagePlayer(entityEvent, player);;
                } else {
                    onPlayerDamageFromNonCustomEntity(entityEvent, player);
                }
            } else {
                onPlayerDamageNoEntity(event, player);
            }

            PlayerAttributes.getAttributes(player).updateActionbar();
        } else {
            net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) event.getEntity()).getHandle();

            if (nmsEntity instanceof CustomCreature creature) {

                if (event instanceof EntityDamageByEntityEvent entityEvent) {

                    if (entityEvent.getDamager() instanceof Player player) {
                        onPlayerDamageCustomEntity(entityEvent, player, creature);
                    }

                }
            } else {
                // Don't allow non-custom creatures to be hit because there is no reason to

                if (event.getEntity() instanceof ItemEntity) {
                    if (event.getCause() != EntityDamageEvent.DamageCause.VOID) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    private void onPlayerDamageCustomEntity(EntityDamageByEntityEvent event, Player player, CustomCreature creature) {
        PlayerAttributes attributes = PlayerAttributes.getAttributes(player);

        if (attributes.isCrit()) {
            event.setDamage(attributes.totalCriticalDamage);
            DisappearingTextDisplay.spawn(event.getEntity().getLocation(), ChatColor.RED + StringUtil.noDecimalDouble(attributes.totalCriticalDamage) + "⚔", 35);
        } else {
            event.setDamage(attributes.totalDamage);
            DisappearingTextDisplay.spawn(event.getEntity().getLocation(), ChatColor.GRAY + StringUtil.noDecimalDouble(attributes.totalDamage) + "\uD83D\uDDE1", 35);
        }

    }

    private  void onCustomEntityDamagePlayer(EntityDamageByEntityEvent event, Player player) {
        PlayerAttributes attributes = PlayerAttributes.getAttributes(player);

        event.setDamage(attributes.getDamageAfterDefence(event.getDamage()));
    }

    private void onPlayerDamageFromNonCustomEntity(EntityDamageByEntityEvent event, Player player) {
        PlayerAttributes attributes = PlayerAttributes.getAttributes(player);

        event.setDamage(attributes.getDamageAfterDefence(event.getDamage()));
    }

    private void onPlayerDamageNoEntity(EntityDamageEvent event, Player player) {
        PlayerAttributes attributes = PlayerAttributes.getAttributes(player);

        event.setDamage(attributes.getDamageAfterDefence(event.getDamage()));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
    }



}
