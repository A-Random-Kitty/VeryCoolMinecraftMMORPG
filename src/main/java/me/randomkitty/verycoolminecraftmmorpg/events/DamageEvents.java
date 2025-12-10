package me.randomkitty.verycoolminecraftmmorpg.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.entity.EntityDyeEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomCreature;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import net.minecraft.world.entity.item.ItemEntity;
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
                }


            }

            PlayerAttributes.getAttributes(player).updateActionbar();
        } else {
            net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) event.getEntity()).getHandle();

            if (nmsEntity instanceof CustomCreature) {

                if (event instanceof EntityDamageByEntityEvent entityEvent) {

                    if (entityEvent.getDamager() instanceof Player player) {
                        event.setDamage(PlayerAttributes.getAttributes(player).totalDamage);
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

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
    }



}
