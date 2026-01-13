package me.randomkitty.verycoolminecraftmmorpg.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.entity.EntityDyeEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import io.papermc.paper.persistence.PersistentDataContainerView;
import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomCreature;
import me.randomkitty.verycoolminecraftmmorpg.entities.visual.DisappearingTextDisplay;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import me.randomkitty.verycoolminecraftmmorpg.util.RandomUtil;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class DamageEvents implements Listener {

    private static final List<TextComponent> deathMessages = List.of(
            Component.text(" died", NamedTextColor.GRAY),
            Component.text(" had skill issue", NamedTextColor.GRAY),
            Component.text(" touched grass", NamedTextColor.GRAY),
            Component.text(" fell off", NamedTextColor.GRAY),
            Component.text(" \"lagged\"", NamedTextColor.GRAY),
            Component.text(" pressed alt f4", NamedTextColor.GRAY)
    );

    @EventHandler
    public void onPreAttack(PrePlayerAttackEntityEvent event) {
        net.minecraft.world.entity.Entity attacked = ((CraftEntity)event.getAttacked()).getHandle();
        if (attacked instanceof CustomCreature creature) {
            PlayerAttributes attributes = PlayerAttributes.getAttributes(event.getPlayer());

            if (attributes.isCrit()) {
                boolean didAttack = creature.customAttackByPlayer(((CraftPlayer) event.getPlayer()).getHandle(), attributes.totalCriticalDamage, true);
                if (didAttack) {
                    DisappearingTextDisplay.spawn(event.getAttacked().getLocation(), ChatColor.RED + StringUtil.noDecimalDouble(attributes.totalCriticalDamage) + "⚔", 35);
                }
            } else {
                boolean didAttack = creature.customAttackByPlayer(((CraftPlayer) event.getPlayer()).getHandle(), attributes.totalDamage, false);
                if (didAttack) {
                    DisappearingTextDisplay.spawn(event.getAttacked().getLocation(), ChatColor.GRAY + StringUtil.noDecimalDouble(attributes.totalDamage) + "\uD83D\uDDE1", 35);

                }
            }

            event.setCancelled(true);
        }
        else if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {

            if (event instanceof EntityDamageByEntityEvent entityEvent) {

                if (entityEvent.getDamager() instanceof Player) {
                    event.setCancelled(true);
                    return;
                } else if (((CraftEntity) entityEvent.getDamager()).getHandle() instanceof CustomCreature) {
                    onCustomEntityDamagePlayer(entityEvent, player);;
                } else {
                    onNormalEntityDamagePlayer(entityEvent, player);
                }
            } else {
                onNaturalDamagePlayer(event, player);
            }

            PlayerAttributes.getAttributes(player).updateActionbar();
        } else {
            net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) event.getEntity()).getHandle();

            if (nmsEntity instanceof CustomCreature creature) {

                if (event instanceof EntityDamageByEntityEvent entityEvent) {

                    if (entityEvent.getDamager() instanceof Player) {
                        VeryCoolMinecraftMMORPG.LOGGER.warning("Player somehow actually damaged custom entity");
                        event.setCancelled(true);
                    } else {
                        onEntityDamageCustomEntity(entityEvent, creature);
                    }

                } else {
                    onNaturalDamageCustomEntity(event, creature);
                }
            } else {
                // Don't allow non-custom creatures to be hit because there is no reason to

                if (event.getCause() != EntityDamageEvent.DamageCause.VOID && event.getCause() != EntityDamageEvent.DamageCause.KILL) {
                    event.setCancelled(true);
                }
            }
        }
    }

    private void onNormalEntityDamagePlayer(EntityDamageByEntityEvent event, Player player) {
        PlayerAttributes attributes = PlayerAttributes.getAttributes(player);

        event.setDamage(attributes.getDamageAfterDefence(event.getDamage()));
    }

    private void onNaturalDamagePlayer(EntityDamageEvent event, Player player) {
        PlayerAttributes attributes = PlayerAttributes.getAttributes(player);

        event.setDamage(attributes.getDamageAfterDefence(event.getDamage()));
    }

    private  void onCustomEntityDamagePlayer(EntityDamageByEntityEvent event, Player player) {
        PlayerAttributes attributes = PlayerAttributes.getAttributes(player);

        event.setDamage(attributes.getDamageAfterDefence(event.getDamage()));
    }

    private void onEntityDamageCustomEntity(EntityDamageByEntityEvent event, CustomCreature creature) {
        if (event.getEntity() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player) {
                handleProjectileDamageEntity(event, projectile);
            } else {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }

    private void onNaturalDamageCustomEntity(EntityDamageEvent event, CustomCreature creature) {
        if (event.getCause() != EntityDamageEvent.DamageCause.KILL || event.getCause() != EntityDamageEvent.DamageCause.CUSTOM || event.getCause() != EntityDamageEvent.DamageCause.VOID)
        event.setCancelled(true);
    }

    private void handleProjectileDamageEntity(EntityDamageByEntityEvent event, Projectile projectile) {
        PersistentDataContainerView data =  projectile.getPersistentDataContainer();

        if (data.has(PlayerAttributes.PROJECTILE_DAMAGE_KEY) && data.has(PlayerAttributes.PROJECTILE_CRITICAL_DAMAGE_KEY) && data.has(PlayerAttributes.PROJECTILE_CRITICAL_CHANCE_KEY)) {
            if (RandomUtil.percentChance(data.get(PlayerAttributes.PROJECTILE_CRITICAL_CHANCE_KEY, PersistentDataType.FLOAT))) {
                double damage = data.get(PlayerAttributes.PROJECTILE_CRITICAL_DAMAGE_KEY, PersistentDataType.FLOAT);
                event.setDamage(damage);
                DisappearingTextDisplay.spawn(event.getEntity().getLocation(), ChatColor.RED + StringUtil.noDecimalDouble(damage) + "⚔", 35);

            } else {
                double damage = data.get(PlayerAttributes.PROJECTILE_DAMAGE_KEY, PersistentDataType.FLOAT);
                event.setDamage(damage);
                DisappearingTextDisplay.spawn(event.getEntity().getLocation(), ChatColor.GRAY + StringUtil.noDecimalDouble(damage) + "\uD83D\uDDE1", 35);
            }
        } else {
            event.setCancelled(true); // doesn't have custom damage values
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(true);

        player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getValue());
        player.teleport(VeryCoolMinecraftMMORPG.CONFIG.getSpawnLocation());
        player.setFallDistance(0);
        player.setFireTicks(0);
        player.getActivePotionEffects().forEach(effect -> {player.removePotionEffect(effect.getType());});

        CachedMetaData rankData = VeryCoolMinecraftMMORPG.RANK_PROVIDER.getMetaData(player);
        String prefix = rankData.getPrefix();

        if (prefix != null) {
            Bukkit.broadcast(player.displayName().append(deathMessages.get(RandomUtil.RANDOM.nextInt(deathMessages.size()))).append(Component.text(" ☠", NamedTextColor.DARK_RED)));
        } else {
            Bukkit.broadcast(player.displayName().append(deathMessages.get(RandomUtil.RANDOM.nextInt(deathMessages.size()))).append(Component.text(" ☠", NamedTextColor.DARK_RED)));
        }
    }

}
