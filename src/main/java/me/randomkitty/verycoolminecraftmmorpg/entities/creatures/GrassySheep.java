package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityDefaultDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityRareDrop;
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
import org.bukkit.entity.Entity;

public class GrassySheep extends CustomSheep {

    private static final CustomEntityDefaultDrop muttonDrop = new CustomEntityDefaultDrop(1, 3, CustomItems.MUTTON);
    private static final CustomEntityRareDrop bladeOfGrassDrop = new CustomEntityRareDrop(0.08f, CustomItems.BLADE_OF_GRASS);

    static {
        defaultDrops.add(muttonDrop);
        rareDrops.add(bladeOfGrassDrop);
    }

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    public GrassySheep(Location location) {
        super(location);

        setColor(DyeColor.LIME);
        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8);
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(25);
        setHealth(25);

        updateDisplayName();
    }

    @Override
    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 15);
        super.goalSelector.addGoal(2, new MeleeAttackGoal(this, (double)2.0F, false));
        goalSelector.addGoal(3, stayCloseToOrginGoal);
        goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));


        targetSelector.addGoal(0, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    @Override
    public double getBaseCoinDrop() { return 5; }

    @Override
    public double getBaseXpDrop() { return 2; }

    @Override
    public String getBaseName() { return ChatColor.GREEN + "Grassy Sheep"; }
}
