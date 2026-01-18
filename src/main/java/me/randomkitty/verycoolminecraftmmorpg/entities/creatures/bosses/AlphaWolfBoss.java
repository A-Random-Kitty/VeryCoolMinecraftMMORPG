package me.randomkitty.verycoolminecraftmmorpg.entities.creatures.bosses;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomCreatureType;
import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomBoss;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomWolf;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.BetterMeleeAttackGoal;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariants;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class AlphaWolfBoss extends CustomWolf implements CustomBoss {

    private StayCloseToOrginGoal stayCloseToOrginGoal;
    private BetterMeleeAttackGoal meleeAttackGoal;

    private final ServerBossEvent bossBar;

    private double lastHealth;

    public AlphaWolfBoss(World world) {
        super(world);

        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000);
        this.setHealth(1000);

        this.bossBar = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        this.updateDisplayName();

    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        meleeAttackGoal = new BetterMeleeAttackGoal(this, 1.50F, true);
        goalSelector.addGoal(1, meleeAttackGoal);
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 15);
        goalSelector.addGoal(4, stayCloseToOrginGoal);

        targetSelector.addGoal(1, new HurtByTargetGoal(this, Player.class));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    public boolean customAttackByPlayer(ServerPlayer player, double damage, boolean crit) {
        boolean temp = super.customAttackByPlayer(player, damage, crit);
        if (temp && lastHealth > 500 && this.getHealth() <= 500 && this.isAlive()) {
            CustomCreatureType.PACK_WOLF.spawnNewCreature(this.getBukkitEntity().getLocation().subtract(1, 0, 1));
            CustomCreatureType.PACK_WOLF.spawnNewCreature(this.getBukkitEntity().getLocation().add(1, 0, 1));
        }
        return temp;
    }


    @Override
    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
        this.getBukkitLivingEntity().getWorld().playSound(this.getBukkitLivingEntity().getLocation(), Sound.ENTITY_WOLF_ANGRY_GROWL, 1.0f, 1.0f);
    }

    @Override
    public void updateDisplayName() {
        super.updateDisplayName();
        this.bossBar.setName(this.getDisplayName());
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void customServerAiStep(@NotNull ServerLevel level) {
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public double getBaseCoinDrop() {
        return 500;
    }

    @Override
    public double getBaseXpDrop() {
        return 300;
    }

    @Override
    public List<DefaultLootDrop> getDefaultDrops() {
        return List.of();
    }

    @Override
    public List<RareLootDrop> getRareDrops() {
        return List.of();
    }

    @Override
    public String getBaseName() {
        return ChatColor.DARK_RED + "Alpha Wolf";
    }
}
