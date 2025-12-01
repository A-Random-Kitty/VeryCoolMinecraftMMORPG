package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueOutput;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SheepCreature extends Sheep {

    protected String baseName;
    public boolean shearable;

    public SheepCreature(Location location) {
        super(EntityType.SHEEP, ((CraftWorld) location.getWorld()).getHandle());
        this.baseName = "Sheep";
        this.shearable = false;
    }

    public void updateDisplayName() {
        this.setCustomName(Component.literal(baseName + ChatColor.RED + " " + String.format("%.1f", this.getHealth()) + "/" + String.format("%.1f", this.getMaxHealth())));
    }

    public void spawn(Location location) {
        this.setPos(location.getX(), location.getY(), location.getZ());
        this.level().addFreshEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
    }

    @Override
    public void shear(ServerLevel level, SoundSource source, ItemStack shears, List<ItemStack> drops) {
        if (shearable) {
            super.shear(level, source, shears, drops);
        }
    }

        @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        boolean idk = super.hurtServer(level, damageSource, amount);
        updateDisplayName();
        return idk;
    }

    @Override
    protected abstract void registerGoals();

    @Override
    public void heal(float amount, EntityRegainHealthEvent.RegainReason regainReason, boolean isFastRegen) {
        super.heal(amount, regainReason, isFastRegen);
    }

    @Override
    public void saveWithoutId(ValueOutput output, boolean includeAll, boolean includeNonSaveable, boolean forceSerialization) {
        // stop the entity from being saved because its easier than figuring how how the heck to save it properly
    }
}