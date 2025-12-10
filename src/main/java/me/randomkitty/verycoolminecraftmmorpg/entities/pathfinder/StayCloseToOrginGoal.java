package me.randomkitty.verycoolminecraftmmorpg.entities.pathfinder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;

public class StayCloseToOrginGoal extends Goal {

    BlockPos orgin;

    @Override
    public boolean canUse() {
        return false;
    }
}
