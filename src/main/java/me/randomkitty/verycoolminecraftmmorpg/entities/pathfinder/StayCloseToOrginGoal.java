package me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;

public class StayCloseToOrginGoal extends Goal {

    protected final PathfinderMob mob;

    BlockPos orgin;

    public StayCloseToOrginGoal(PathfinderMob mob, BlockPos orgin) {
        this.mob = mob;
        this.orgin = orgin;
    }

    @Override
    public boolean canUse() {

        return Math.sqrt(mob.distanceToSqr(orgin.getX(), orgin.getY(), orgin.getZ())) >= 15;
    }

    @Override
    public boolean canContinueToUse() {
        return Math.sqrt(mob.distanceToSqr(orgin.getX(), orgin.getY(), orgin.getZ())) >= 12;
    }

    @Override
    public void start() {
        this.mob.getNavigation().createPath(orgin, 30);
    }
}
