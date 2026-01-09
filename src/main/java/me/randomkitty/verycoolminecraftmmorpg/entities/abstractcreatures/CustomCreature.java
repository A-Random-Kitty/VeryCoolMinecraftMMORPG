package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.util.ItemDropUtil;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.nio.file.Path;
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

    default EntityDeathEvent dropAllDeathLootCustom(ServerLevel level, DamageSource damageSource, PathfinderMob mob) {
        if (mob.getLastHurtByPlayer() != null) {
            Player player = (Player) mob.getLastHurtByPlayer().getBukkitEntity();
            Location dropLocation = new Location(mob.level().getWorld(), mob.getX(), mob.getY(), mob.getZ());

            for (RareLootDrop rareDrop : getRareDrops()) {
                if (rareDrop.shouldDrop()) {
                    ItemDropUtil.givePlayerLootOrDrop(player, rareDrop.getItem(), dropLocation);
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

    boolean customAttackByPlayer(ServerPlayer player, double damage, boolean crit);

    double getBaseCoinDrop();
    double getBaseXpDrop();
    List<DefaultLootDrop> getDefaultDrops();
    List<RareLootDrop> getRareDrops();
    String getBaseName();

    Map<Player, Double> getDamagers();

    PathfinderMob getMob();
}