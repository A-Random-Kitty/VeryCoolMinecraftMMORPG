package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.drops.DefaultLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.drops.RareLootDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomPathfinderMob;
import net.minecraft.world.entity.EntityType;
import org.bukkit.World;

import java.util.List;

public class ZombieCreature extends CustomPathfinderMob {

    public ZombieCreature(World world) {
        super(EntityType.ZOMBIE, world);
    }

    protected void registerGoals() {
        //this.goalSelector.removeAllGoals(goal -> true);
    }

    @Override
    public double getBaseCoinDrop() {
        return 8;
    }

    @Override
    public double getBaseXpDrop() {
        return 5;
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
        return "Zombie";
    }
}
