package me.randomkitty.verycoolminecraftmmorpg.entities.creatures.bosses;

import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomBoss;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomWolf;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.List;
import java.util.Map;

public class AlphaWolfBoss extends CustomWolf implements CustomBoss {

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    public AlphaWolfBoss(World world) {
        super(world);

        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000);
        this.setHealth(400);

        this.updateDisplayName();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 2.0f));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.750F, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 15);
        goalSelector.addGoal(5, stayCloseToOrginGoal);

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
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
        return ChatColor.RED + "Alpha Wolf";
    }
}
