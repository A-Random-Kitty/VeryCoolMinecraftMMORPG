package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityDefaultDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityRareDrop;
import me.randomkitty.verycoolminecraftmmorpg.util.LootUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueOutput;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomSheep extends Sheep {

    protected String baseName;
    public boolean shearable;

    protected List<CustomEntityDefaultDrop> defaultDrops = new ArrayList<>();
    protected List<CustomEntityRareDrop> rareDrops = new ArrayList<>();

    public CustomSheep(Location location) {
        super(EntityType.SHEEP, ((CraftWorld) location.getWorld()).getHandle());
        this.shearable = false;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        // override to prevent error because there is no eat grass goal
    }

    @Override
    public void shear(ServerLevel level, SoundSource source, ItemStack shears, List<ItemStack> drops) {
        if (shearable) {
            super.shear(level, source, shears, drops);
        }
    }

    public void updateDisplayName() {
        this.setCustomName(Component.literal(baseName + ChatColor.RED + " " + String.format("%.1f", this.getHealth()) + "/" + String.format("%.1f", this.getMaxHealth())));
    }

    public void spawn(Location location) {
        this.setPos(location.getX(), location.getY(), location.getZ());
        this.level().addFreshEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    protected EntityDeathEvent dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {

        if (this.getLastHurtByPlayer() != null) {
            Player player = (Player) this.getLastHurtByPlayer().getBukkitEntity();

            LootUtil.givePlayerKillRewards(player, rareDrops, defaultDrops, this);
        }

        // Don't pass drops to EntityDeathEvent because we want to drop items in a custom way
        EntityDeathEvent deathEvent = CraftEventFactory.callEntityDeathEvent(this, damageSource, this.drops, () -> {
            LivingEntity killer = this.getKillCredit();
            if (killer != null) {
                killer.awardKillScore(this, damageSource);
            }
        });

        this.drops = new ArrayList<>();
        return deathEvent;
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