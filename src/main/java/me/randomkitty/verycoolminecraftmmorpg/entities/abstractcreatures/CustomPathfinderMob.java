package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueOutput;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        updateDisplayName();
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
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if (damageSource.eventEntityDamager() != null) {
            if (damageSource.eventEntityDamager() instanceof Player player) {
                if (damageTicks.containsKey(player)) {
                    if (tickCount >= damageTicks.get(player) + 10) {
                        this.hurtTime = 0;
                    }
                } else {
                    this.hurtTime = 0;
                }
            } else {
                this.hurtTime = 0;
            }

        }

        boolean idk = super.hurtServer(level, damageSource, amount);
        updateDisplayName();
        return idk;
    }

    @Override
    public boolean actuallyHurt(ServerLevel level, DamageSource damageSource, float amount, EntityDamageEvent event) {
        boolean temp = super.actuallyHurt(level, damageSource, amount, event);
        if (temp) {
            if (damageSource.eventEntityDamager() instanceof Player player) {
                damageTicks.put(player, tickCount);
                if (damages.containsKey(player)) {
                    damages.put(player, damages.get(player) + amount);
                } else {
                    damages.put(player, (double) amount);
                }
            }
        }
        return temp;
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
}
