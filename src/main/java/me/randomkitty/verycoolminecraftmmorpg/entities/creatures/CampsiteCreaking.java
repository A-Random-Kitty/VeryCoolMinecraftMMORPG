package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CoolCustomCreaking;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomCreaking;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder.StayCloseToOrginGoal;
import me.randomkitty.verycoolminecraftmmorpg.item.CustomItems;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class CampsiteCreaking extends CoolCustomCreaking {

    private static final List<DefaultLootDrop> defaultDrops = new ArrayList<>();
    private static final List<RareLootDrop> rareDrops = new ArrayList<>();

    private static final DefaultLootDrop paleTreeSkinDrop = new DefaultLootDrop(1, 2, CustomItems.PALE_TREE_SKIN);
    private static final RareLootDrop paleTreeArmDrop = new RareLootDrop(1/5f, CustomItems.PALE_TREE_ARM);
    private static final RareLootDrop paleHeartDrop = new RareLootDrop(1/777f, CustomItems.PALE_HEART);

    static {
        defaultDrops.add(paleTreeSkinDrop);
        rareDrops.add(paleTreeArmDrop);
        rareDrops.add(paleHeartDrop);
    }

    private StayCloseToOrginGoal stayCloseToOrginGoal;

    public CampsiteCreaking(World world) {
        super(world);
        this.getAttributes().registerAttribute(Attributes.ATTACK_DAMAGE);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(120);
        this.setHealth(120);

        this.updateDisplayName();
    }

    @Override
    public void onPostSpawn() {
        this.stayCloseToOrginGoal.origin = this.getOnPos();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1f, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.stayCloseToOrginGoal = new StayCloseToOrginGoal(this, 15);
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
    public String getBaseName() {
        return ChatColor.GRAY + "Campsite Creaking";
    }
}
