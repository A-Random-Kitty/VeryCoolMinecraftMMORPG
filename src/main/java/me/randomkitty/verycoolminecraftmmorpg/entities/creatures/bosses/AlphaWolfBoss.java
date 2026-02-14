package me.randomkitty.verycoolminecraftmmorpg.entities.creatures.bosses;

import me.randomkitty.verycoolminecraftmmorpg.entities.CustomCreatureType;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.RareBookDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomBoss;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomWolf;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.BetterMeleeAttackGoal;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifiers;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerAccomplishments;
import net.kyori.adventure.text.TextComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AlphaWolfBoss extends CustomWolf implements CustomBoss {

    private static final List<DefaultLootDrop> defaultDrops = new ArrayList<>();
    private static final List<RareLootDrop> rareDrops = new ArrayList<>();

    private static final DefaultLootDrop alphaWolfPeltDrop = new DefaultLootDrop(1, 2, CustomItems.ALPHA_WOLF_PELT);
    private static final RareLootDrop ancientWolfFangDrop = new RareLootDrop(0.02f, CustomItems.ANCIENT_WOLF_FANG, true);
    private static final RareBookDrop sharpnessBookDrop = new RareBookDrop(0.02f, ItemModifiers.SHARPNESS_ENCHANT, 1);

    static {
        defaultDrops.add(alphaWolfPeltDrop);
        rareDrops.add(ancientWolfFangDrop);
        rareDrops.add(sharpnessBookDrop);
    }

    private StayCloseToOrginGoal stayCloseToOrginGoal;
    private BetterMeleeAttackGoal meleeAttackGoal;

    private final ServerBossEvent bossBar;

    private double lastHealth;

    public AlphaWolfBoss(World world) {
        super(world);

        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(25);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(750);
        this.setHealth(750);
        lastHealth = 750;

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

        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    @Override
    public boolean customAttackByPlayer(ServerPlayer player, double damage, boolean crit) {
        boolean temp = super.customAttackByPlayer(player, damage, crit);
        this.setTarget(player, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
        if (temp && lastHealth > 500 && this.getHealth() <= 500 && this.isAlive()) {
            CustomCreatureType.PACK_WOLF.spawnNewCreature(this.getBukkitEntity().getLocation().subtract(1, 0, 1));
            CustomCreatureType.PACK_WOLF.spawnNewCreature(this.getBukkitEntity().getLocation().add(1, 0, 1));
        }
        lastHealth = this.getHealth();
        return temp;
    }

    @Override
    public void givePlayerBossLoot(PathfinderMob mob, org.bukkit.entity.Player player, double damage, TextComponent bossDownComponent) {
        if (damage >= 100) {
            CustomBoss.super.givePlayerBossLoot(mob, player, damage, bossDownComponent);
            PlayerAccomplishments.grantAccomplishment(player, "alpha_wolf_slain");
        }
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
        return defaultDrops;
    }

    @Override
    public List<RareLootDrop> getRareDrops() {
        return rareDrops;
    }

    @Override
    public String getBaseName() {
        return ChatColor.DARK_RED + "Alpha Wolf";
    }
}
