package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomSheep extends Sheep implements CustomCreature {

    private final Map<Player, Double> damages = new HashMap<>();
    private final Map<Player, Integer> damageTicks = new HashMap<>();

    public boolean shearable;
    public boolean dyeable;

    public CustomSheep(World world) {
        super(EntityType.SHEEP, ((CraftWorld) world).getHandle());
        this.shearable = false;
        this.dyeable = false;

        this.setCustomNameVisible(true);
        this.persist = false;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        // override to prevent error because there is no eat grass goal
    }

    @Override
    public void shear(ServerLevel level, SoundSource source, ItemStack shears, List<ItemStack> drops) {
        if (shearable) {
            super.shear(level, source, shears, drops);
        }
    }

    public void spawn(Location location) {
        this.spawn(location, this);
    }

    public void updateDisplayName() {
        updateDisplayName(this);
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

        damageTicks.put(player, tickCount);

        DamageSource source = player.damageSources().playerAttack(player);

        {
            double kbMulti = (player.isSprinting() ? 2.0 : 1.0);
            this.knockback(kbMulti * 0.5F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)), player, io.papermc.paper.event.entity.EntityKnockbackEvent.Cause.ENTITY_ATTACK);

            this.getBukkitLivingEntity().playHurtAnimation(player.getBukkitYaw());
            if (crit) {
                bukkitPlayer.getWorld().playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
            } else {
                bukkitPlayer.getWorld().playSound(bukkitPlayer.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
            }

            handleDamageEvent(source);
        }

        {
            if (damages.containsKey(player)) {
                damages.put(player, damages.get(player) + damage);
            } else {
                damages.put(player, damage);
            }
        }

        this.setHealth(getHealth() - (float) damage);

        if (this.isDeadOrDying() && !this.dead) {
            this.die(source);
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