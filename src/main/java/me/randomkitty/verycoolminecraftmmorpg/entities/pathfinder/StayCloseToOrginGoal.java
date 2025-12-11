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


        return mob.distanceToSqr(origin.getX(), origin.getY(), origin.getZ()) >= (range * range);
    }

    @Override
    public boolean canContinueToUse() {
        //Bukkit.getLogger().info("checked continue moving back to orgin");
        //Bukkit.getLogger().info( String.valueOf(Math.sqrt(mob.distanceToSqr(origin.getX(), origin.getY(), origin.getZ())) >= 12));

        return mob.distanceToSqr(origin.getX(), origin.getY(), origin.getZ()) >= (range * range);
    }

    @Override
    public void start() {
        Bukkit.getLogger().info("testing move to origin start" + origin.getX() + ", " +  origin.getY() + ", " + origin.getZ());
        this.mob.getNavigation().moveTo(origin.getX(), origin.getY(), origin.getZ(), 1);
    }

    @Override
    public void stop() {
        Bukkit.getLogger().info("testing move to origin stop" + origin.getX() + ", " +  origin.getY() + ", " + origin.getZ());
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.mob.getNavigation().moveTo(origin.getX(), origin.getY(), origin.getZ(), 1);
    }
}
