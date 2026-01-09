package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueOutput;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomPathfinderMob extends PathfinderMob implements CustomCreature {

    private final Map<Player, Double> damages = new HashMap<>();
    private final Map<Player, Integer> damageTicks = new HashMap<>();

    private volatile CraftEntity bukkitEntity;

    public CustomPathfinderMob(EntityType<? extends PathfinderMob> type, World world) {
        super(type, ((CraftWorld) world).getHandle());
        this.setCustomNameVisible(true);
        this.persist = false;
    }

    public void updateDisplayName() {
        updateDisplayName(this);
    }

    public void spawn(Location location) {
        this.spawn(location, this);
    }

    @Override
    public @NotNull CraftEntity getBukkitEntity() {
        if (this.bukkitEntity == null) {
            synchronized(this) {
                if (this.bukkitEntity == null) {
                    return bukkitEntity = new CraftLivingEntity((CraftServer) Bukkit.getServer(), this);
                }
            }
        }

        return this.bukkitEntity;
    }

    @Override
    public @NotNull CraftLivingEntity getBukkitLivingEntity() {
        return (CraftLivingEntity) this.getBukkitEntity();
    }

    @Override
    protected EntityDeathEvent dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        return dropAllDeathLootCustom(level, damageSource, this);
    }

    @Override
    public boolean customAttackByPlayer(ServerPlayer player, double damage, boolean crit) {
        org.bukkit.entity.Player bukkitPlayer = player.getBukkitEntity();
        Integer damageTick = damageTicks.get(player);

        if ((damageTick != null && damageTick + 10 > tickCount) || this.isDeadOrDying()) {
            return false;
        }

        DamageSource source = this.damageSources().playerAttack(player);

        double kbMulti = (player.isSprinting() ? 2.0 : 1.0);
        this.knockback(kbMulti * 0.5F, Mth.sin(player.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float)Math.PI / 180F)), player, io.papermc.paper.event.entity.EntityKnockbackEvent.Cause.ENTITY_ATTACK);

        this.getBukkitLivingEntity().playHurtAnimation(player.getBukkitYaw());
        if (crit) {
            bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
        } else {
            bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
        }
        ;
        handleDamageEvent(source);

        this.setHealth(getHealth() - (float) damage);

        if (this.isDeadOrDying() && !this.dead) {
            this.die(source);
        }

        damageTicks.put(player, tickCount);
        if (damages.containsKey(player)) {
            damages.put(player, damages.get(player) + damage);
        } else {
            damages.put(player, damage);
        }

        updateDisplayName();

        return true;
    }

    @Override
    protected abstract void registerGoals();

    @Override
    public void heal(float amount, EntityRegainHealthEvent.RegainReason regainReason, boolean isFastRegen) {
        super.heal(amount, regainReason, isFastRegen);
        updateDisplayName();

    }

    @Override
    public void saveWithoutId(ValueOutput output, boolean includeAll, boolean includeNonSaveable, boolean forceSerialization) {
        // stop the entity from being saved because its easier than figuring how how the heck to save it properly
    }

    @Override
    public Map<org.bukkit.entity.Player, Double> getDamagers() {
        Map<org.bukkit.entity.Player, Double> damagers = new HashMap<>();

        for (Map.Entry<Player, Double> entry : this.damages.entrySet()) {
            damagers.put((org.bukkit.entity.Player) entry.getKey().getBukkitEntity(), entry.getValue());
        }

        return damagers;
    }

    public PathfinderMob getMob() {
        return this;
    }
}
