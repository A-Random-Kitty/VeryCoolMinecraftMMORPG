package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;


import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.util.ItemDropUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.Location;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface CustomBoss extends CustomCreature {

    default void announceDefeat(Set<Player> damagers) {
        for (Player player : damagers) {
            player.sendMessage(Component.text("BOSS DOWN! ", NamedTextColor.DARK_RED, TextDecoration.BOLD).append(Component.text(getBaseName())));
        }
    }

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
}
