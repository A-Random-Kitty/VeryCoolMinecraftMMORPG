package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueOutput;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomCow extends Cow implements CustomCreature {
    private final Map<ServerPlayer, Double> damages = new HashMap<>();
    private final Map<ServerPlayer, Integer> damageTicks = new HashMap<>();

    private volatile CraftEntity bukkitEntity;

    public CustomCow(World world) {
        super(EntityType.COW, ((CraftWorld) world).getHandle());
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
        return customAttackByPlayer(player, damage, crit, this);
    }


    @Override
    protected abstract void registerGoals();

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

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

        for (Map.Entry<ServerPlayer, Double> entry : this.damages.entrySet()) {
            damagers.put(entry.getKey().getBukkitEntity(), entry.getValue());
        }

        return damagers;
    }

    public boolean isDead() {
        return dead;
    }

    public Map<ServerPlayer, Double> getDamages() {
        return damages;
    }

    public Map<ServerPlayer, Integer> getDamageTicks() {
        return damageTicks;
    }

    public PathfinderMob getMob() {
        return this;
    }
}
