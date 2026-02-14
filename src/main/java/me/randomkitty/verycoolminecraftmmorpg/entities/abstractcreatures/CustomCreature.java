package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.util.ItemDropUtil;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CustomCreature {

    default void updateDisplayName(PathfinderMob mob) {
        mob.getBukkitEntity().setCustomName(getBaseName() + ChatColor.RED + " " + StringUtil.formatedDouble(mob.getHealth()) + "/" + StringUtil.formatedDouble(mob.getMaxHealth()));
    }

    default void spawn(Location location, PathfinderMob mob) {
        mob.setPos(location.getX(), location.getY(), location.getZ());
        mob.level().addFreshEntity(mob, CreatureSpawnEvent.SpawnReason.CUSTOM);
        onPostSpawn();
    }

    default void onPostSpawn() {}

    default EntityDeathEvent dropAllDeathLootCustom(ServerLevel level, DamageSource damageSource, PathfinderMob mob) {;
        if (damageSource.getEntity() instanceof net.minecraft.world.entity.player.Player p) {
            Player player = (Player) p.getBukkitEntity();
            Location dropLocation = new Location(mob.level().getWorld(), mob.getX(), mob.getY(), mob.getZ());

            for (RareLootDrop rareDrop : getRareDrops()) {
                if (rareDrop.shouldDrop()) {
                    ItemDropUtil.givePlayerLootOrDrop(player, rareDrop.getItem(), dropLocation);

                    if (rareDrop.shouldTellPlayer()) {
                        player.sendMessage(rareDrop.getMessage());
                    }
                }
            }

            for (DefaultLootDrop defaultDrop : getDefaultDrops()) {
                ItemDropUtil.givePlayerLootOrDrop(player, defaultDrop.getDrop(), dropLocation);
            }

            ItemDropUtil.givePlayerCoinsAndDrop(player, getBaseCoinDrop(), mob);
            ItemDropUtil.givePlayerCombatXpAndDrop(player, getBaseXpDrop(), mob);
        }

        // Don't pass drops to EntityDeathEvent because we want to drop items in a custom way
        EntityDeathEvent deathEvent = CraftEventFactory.callEntityDeathEvent(mob, damageSource, mob.drops, () -> {
            LivingEntity killer = mob.getKillCredit();
            if (killer != null) {
                killer.awardKillScore(mob, damageSource);
            }
        });

        mob.drops = new ArrayList<>();
        return deathEvent;
    }

    default boolean customAttackByPlayer(ServerPlayer player, double damage, boolean crit, PathfinderMob mob) {
        org.bukkit.entity.Player bukkitPlayer = player.getBukkitEntity();
        Integer damageTick = getDamageTicks().get(player);

        if ((damageTick != null && damageTick + 10 > mob.tickCount) || mob.isDeadOrDying()) {
            return false;
        }

        getDamageTicks().put(player, mob.tickCount);

        DamageSource source = player.damageSources().playerAttack(player);

        {
            if (mob.hurtTime <= 0) {
                double kbMulti = (player.isSprinting() ? 2.0 : 1.0);
                mob.knockback(kbMulti * 0.5F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)), player, io.papermc.paper.event.entity.EntityKnockbackEvent.Cause.ENTITY_ATTACK);

                mob.handleDamageEvent(source);
            } else {
                // if the mob is already in a hurt animation just like play the sound or something
                SoundEvent hurtSound = mob.getHurtSound(source);
                if (hurtSound != null) {
                    mob.playSound(hurtSound, mob.getSoundVolume(), (mob.random.nextFloat() - mob.random.nextFloat()) * 0.2F + 1.0F);
                }
            }

            mob.getBukkitLivingEntity().playHurtAnimation(player.getBukkitYaw());

            if (crit) {
                bukkitPlayer.getWorld().playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
            } else {
                bukkitPlayer.getWorld().playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
            }
        }

        {
            if (getDamages().containsKey(player)) {
                getDamages().put(player, getDamages().get(player) + damage);
            } else {
                getDamages().put(player, damage);
            }
        }

        mob.setHealth(mob.getHealth() - (float) damage);

        if (mob.isDeadOrDying() && !isDead()) {
            mob.die(source);
        }

        updateDisplayName(mob);

        return true;
    }

    boolean customAttackByPlayer(ServerPlayer player, double damage, boolean crit);

    boolean isDead();
    Map<ServerPlayer, Double> getDamages();
    Map<ServerPlayer, Integer> getDamageTicks();

    double getBaseCoinDrop();
    double getBaseXpDrop();
    List<DefaultLootDrop> getDefaultDrops();
    List<RareLootDrop> getRareDrops();
    String getBaseName();

    Map<Player, Double> getDamagers();

    PathfinderMob getMob();
}