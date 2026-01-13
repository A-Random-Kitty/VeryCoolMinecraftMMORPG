package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomWolf;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class PackWolf extends CustomWolf {

    private static final List<DefaultLootDrop> defaultDrops = new ArrayList<>();
    private static final List<RareLootDrop> rareDrops = new ArrayList<>();

    private static final DefaultLootDrop wolfPeltDrop = new DefaultLootDrop(1, 2, CustomItems.WOLF_PELT);
    private static final RareLootDrop wolfToothDrop = new RareLootDrop(0.2f, CustomItems.RAM_HORN_FRAGMENT);

    static {
        defaultDrops.add(wolfPeltDrop);
        rareDrops.add(wolfToothDrop);
    }

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    public PackWolf(World world) {
        super(world);

        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(125);
        this.setHealth(50);

        this.updateDisplayName();
    }

    @Override
    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25f, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 10);
        goalSelector.addGoal(5, stayCloseToOrginGoal);

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public double getBaseCoinDrop() { return 20; }

    @Override
    public double getBaseXpDrop() { return 8; }

    @Override
    public List<DefaultLootDrop> getDefaultDrops() { return defaultDrops; }

    @Override
    public List<RareLootDrop> getRareDrops() { return rareDrops; }

    @Override
    public String getBaseName() { return ChatColor.GRAY + "Pack Wolf"; }

}
