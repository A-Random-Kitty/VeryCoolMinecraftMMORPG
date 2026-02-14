package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.drops.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomCow;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ThickGrasslandsBull extends CustomCow {
    private static final List<DefaultLootDrop> defaultDrops = new ArrayList<>();
    private static final List<RareLootDrop> rareDrops = new ArrayList<>();

    private static final DefaultLootDrop cowHideDrop = new DefaultLootDrop(2, 3, CustomItems.COW_HIDE);

    static {
        defaultDrops.add(cowHideDrop);
    }

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    public ThickGrasslandsBull(World world) {
        super(world);

        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(25);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(500);
        this.setHealth(500);

        this.updateDisplayName();
    }

    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.750F, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 20);
        goalSelector.addGoal(5, stayCloseToOrginGoal);

        targetSelector.addGoal(0, new HurtByTargetGoal(this, Player.class));
    }

    @Override
    public double getBaseCoinDrop() {
        return 30;
    }

    @Override
    public double getBaseXpDrop() {
        return 30;
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
        return ChatColor.DARK_GREEN + "Thick Grasslands Bull";
    }
}
