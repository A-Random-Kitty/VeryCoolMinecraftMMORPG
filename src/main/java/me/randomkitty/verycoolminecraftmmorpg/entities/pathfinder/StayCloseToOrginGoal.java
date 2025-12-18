package me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Bukkit;

public class StayCloseToOrginGoal extends Goal {

    protected final PathfinderMob mob;
    private final double range;

    public BlockPos origin;

    public StayCloseToOrginGoal(PathfinderMob mob, double range) {
        this.mob = mob;
        this.range = range;
    }

    @Override
    public boolean canUse() {


        return mob.distanceToSqr(origin.getX(), origin.getY(), origin.getZ()) >= (range * range) && !mob.getNavigation().isDone();
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone();
        //return mob.distanceToSqr(origin.getX(), origin.getY(), origin.getZ()) >= (range * range) - 2;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(origin.getX(), origin.getY(), origin.getZ(), 1);
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        //this.mob.getNavigation().moveTo(origin.getX(), origin.getY(), origin.getZ(), 1);
    }
}
