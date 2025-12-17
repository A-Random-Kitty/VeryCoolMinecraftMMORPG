package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import io.papermc.paper.adventure.PaperAdventure;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityDefaultDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityRareDrop;
import me.randomkitty.verycoolminecraftmmorpg.util.ItemDropUtil;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public interface CustomCreature {


    List<CustomEntityDefaultDrop> defaultDrops = new ArrayList<>();
    List<CustomEntityRareDrop> rareDrops = new ArrayList<>();

    double getBaseCoinDrop();

    double getBaseXpDrop();

    String getBaseName();

    default void updateDisplayName(PathfinderMob mob) {
        mob.getBukkitEntity().setCustomName(getBaseName() + ChatColor.RED + " " + StringUtil.formatedDouble(mob.getHealth()) + "/" + StringUtil.formatedDouble(mob.getMaxHealth()));
    }

    default void onPostSpawn() {

    }

    default EntityDeathEvent dropAllDeathLootCustom(ServerLevel level, DamageSource damageSource, PathfinderMob mob) {
        if (mob.getLastHurtByPlayer() != null) {
            Player player = (Player) mob.getLastHurtByPlayer().getBukkitEntity();

            ItemDropUtil.givePlayerMobLoot(player, rareDrops, defaultDrops, mob);
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