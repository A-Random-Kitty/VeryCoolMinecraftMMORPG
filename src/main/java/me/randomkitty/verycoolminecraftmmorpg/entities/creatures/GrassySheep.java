package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomSheep;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.DyeColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GrassySheep extends CustomSheep {

    private static final List<DefaultLootDrop> defaultDrops = new ArrayList<>();
    private static final List<RareLootDrop> rareDrops = new ArrayList<>();

    private static final DefaultLootDrop muttonDrop = new DefaultLootDrop(1, 3, CustomItems.MUTTON);
    private static final DefaultLootDrop grassyWoolDrop = new DefaultLootDrop(1, 1, CustomItems.GRASSY_WOOL);
    private static final RareLootDrop bladeOfGrassDrop = new RareLootDrop(0.04f, CustomItems.BLADE_OF_GRASS);

    static {
        defaultDrops.add(muttonDrop);
        defaultDrops.add(grassyWoolDrop);
        rareDrops.add(bladeOfGrassDrop);
    }

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    public GrassySheep(World world) {
        super(world);

        this.setColor(DyeColor.LIME);
        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25);
        this.setHealth(25);

        this.updateDisplayName();
    }

    @Override
    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
    }



    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.5f));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 15);
        goalSelector.addGoal(5, stayCloseToOrginGoal);
    }

    @Override
    public double getBaseCoinDrop() { return 5; }

    @Override
    public double getBaseXpDrop() { return 2; }

    @Override
    public List<DefaultLootDrop> getDefaultDrops() { return defaultDrops; }

    @Override
    public List<RareLootDrop> getRareDrops() { return rareDrops; }

    @Override
    public String getBaseName() { return ChatColor.GREEN + "Grassy Sheep"; }

    @Override
    public Map<Player, Double> getDamagers() {
        return Map.of();
    }
}
