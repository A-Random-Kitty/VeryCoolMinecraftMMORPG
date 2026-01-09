package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.wolf.Wolf;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.HashMap;
import java.util.Map;


public abstract class CustomWolf extends Wolf implements CustomCreature {

    private final Map<ServerPlayer, Double> damages = new HashMap<>();
    private final Map<ServerPlayer, Integer> damageTicks = new HashMap<>();


    public CustomWolf(World world) {
        super(EntityType.WOLF, ((CraftWorld) world).getHandle());

        this.setCustomNameVisible(true);
        this.persist = false;
    }

    @Override
    public InteractionResult mobInteract(net.minecraft.world.entity.player.Player player, InteractionHand hand) {
        return InteractionResult.PASS;
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
        Player bukkitPlayer = player.getBukkitEntity();
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
    public Map<org.bukkit.entity.Player, Double> getDamagers() {
        Map<org.bukkit.entity.Player, Double> damagers = new HashMap<>();

        for (Map.Entry<ServerPlayer, Double> entry : this.damages.entrySet()) {
            damagers.put(entry.getKey().getBukkitEntity(), entry.getValue());
        }

        return damagers;
    }

    public PathfinderMob getMob() {
        return this;
    }
}
