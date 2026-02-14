package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.creaking.CreakingAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import javax.annotation.Nullable;
import java.util.List;

public abstract class CoolCustomCreaking extends CustomCreaking {

    private LivingEntity target;

    public CoolCustomCreaking(World world) {
        super(world);
        this.lookControl = new LookControl(this);
        this.moveControl = new MoveControl(this);
        this.jumpControl = new JumpControl(this);
    }

    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }

    public boolean checkCanMove() {
        return true;
    }

    public boolean canMove() {
        return true;
    }

    @Nullable
    public LivingEntity getTarget() {
        return this.target;
    }

    public boolean setTarget(@Nullable LivingEntity target, @Nullable EntityTargetEvent.TargetReason reason) {
        if (this.getTarget() == target) {
            return false;
        } else {
            if (reason != null) {
                if (reason == EntityTargetEvent.TargetReason.UNKNOWN && this.getTarget() != null && target == null) {
                    reason = this.getTarget().isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
                }

                if (reason == EntityTargetEvent.TargetReason.UNKNOWN) {
                    this.level().getCraftServer().getLogger().log(java.util.logging.Level.WARNING, "Unknown target reason, please report on the issue tracker", new Exception());
                }

                CraftLivingEntity ctarget = null;
                if (target != null) {
                    ctarget = (CraftLivingEntity)target.getBukkitEntity();
                }

                EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent(this.getBukkitEntity(), ctarget, reason);
                if (!event.callEvent()) {
                    return false;
                }

                if (event.getTarget() != null) {
                    target = ((CraftLivingEntity)event.getTarget()).getHandle();
                } else {
                    target = null;
                }
            }

            this.target = target;
            return true;
        }
    }
}
