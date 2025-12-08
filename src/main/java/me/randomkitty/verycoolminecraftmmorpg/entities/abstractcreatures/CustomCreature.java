package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;


import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityDefaultDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityRareDrop;
import me.randomkitty.verycoolminecraftmmorpg.util.ItemDropUtil;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueOutput;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomCreature extends PathfinderMob {

    protected String baseName;

    protected List<CustomEntityDefaultDrop> defaultDrops = new ArrayList<>();
    protected List<CustomEntityRareDrop> rareDrops = new ArrayList<>();
    protected double baseCoinDrop;
    protected double baseXpDrop;

    private volatile CraftEntity bukkitEntity;

    public CustomCreature(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.setCustomNameVisible(true);
        updateDisplayName();
        this.persist = false;
    }

    public void updateDisplayName() {
        this.setCustomName(Component.literal(baseName + ChatColor.RED + " " + StringUtil.formatedDouble(this.getHealth()) + "/" + StringUtil.formatedDouble(this.getMaxHealth())));
    }

    public void spawn(Location location) {
        this.setPos(location.getX(), location.getY(), location.getZ());
        this.level().addFreshEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    protected EntityDeathEvent dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {

        if (this.getLastHurtByPlayer() != null) {
            Player player = (Player) this.getLastHurtByPlayer().getBukkitEntity();

            ItemDropUtil.givePlayerMobLoot(player, rareDrops, defaultDrops, this);
            ItemDropUtil.givePlayerCoinsAndDrop(player, baseCoinDrop, this);
        }

        // Don't pass drops to EntityDeathEvent because we want to drop items in a custom way
        EntityDeathEvent deathEvent = CraftEventFactory.callEntityDeathEvent(this, damageSource, this.drops, () -> {
            LivingEntity killer = this.getKillCredit();
            if (killer != null) {
                killer.awardKillScore(this, damageSource);
            }
        });

        this.drops = new ArrayList<>();
        return deathEvent;
    }


    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean idk = super.hurtServer(level, damageSource, amount);
        updateDisplayName();
        return idk;
    }

    @Override
    protected abstract void registerGoals();

    @Override
    public void heal(float amount, EntityRegainHealthEvent.RegainReason regainReason, boolean isFastRegen) {
        super.heal(amount, regainReason, isFastRegen);
    }

    @Override
    public void saveWithoutId(ValueOutput output, boolean includeAll, boolean includeNonSaveable, boolean forceSerialization) {
        // stop the entity from being saved because its easier than figuring how how the heck to save it properly
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
}
