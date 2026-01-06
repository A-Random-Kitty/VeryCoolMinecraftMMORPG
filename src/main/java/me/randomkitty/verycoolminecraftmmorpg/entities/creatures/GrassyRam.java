package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomSheep;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GrassyRam extends CustomSheep {

    private static final List<DefaultLootDrop> defaultDrops = new ArrayList<>();
    private static final List<RareLootDrop> rareDrops = new ArrayList<>();

    private static final DefaultLootDrop muttonDrop = new DefaultLootDrop(1, 3, CustomItems.MUTTON);
    private static final DefaultLootDrop grassyWoolDrop = new DefaultLootDrop(1, 2, CustomItems.GRASSY_WOOL);
    private static final RareLootDrop ramHornFragDrop = new RareLootDrop(0.05f, CustomItems.RAM_HORN_FRAGMENT);

    static {
        defaultDrops.add(muttonDrop);
        defaultDrops.add(grassyWoolDrop);
        rareDrops.add(ramHornFragDrop);
    }

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    public GrassyRam(World world) {
        super(world);

        this.setColor(DyeColor.GREEN);
        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50);
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
        goalSelector.addGoal(1, new PanicGoal(this, 2.0f));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.750F, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 10);
        goalSelector.addGoal(5, stayCloseToOrginGoal);

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public double getBaseCoinDrop() { return 15; }

    @Override
    public double getBaseXpDrop() { return 5; }

    @Override
    public List<DefaultLootDrop> getDefaultDrops() { return defaultDrops; }

    @Override
    public List<RareLootDrop> getRareDrops() { return rareDrops; }

    @Override
    public String getBaseName() { return ChatColor.DARK_GREEN + "Grassy Ram"; }
}
