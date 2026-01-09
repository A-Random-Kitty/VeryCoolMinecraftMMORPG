package me.randomkitty.verycoolminecraftmmorpg.entities.creatures.bosses;

import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomBoss;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomWolf;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class AlphaWolfBoss extends CustomWolf implements CustomBoss {

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    private final ServerBossEvent bossBar;

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
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.50F, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 15);
        goalSelector.addGoal(5, stayCloseToOrginGoal);

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
    }

    @Override
    public void updateDisplayName() {
        super.updateDisplayName();
        bossBar.setName(this.getDisplayName());
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
