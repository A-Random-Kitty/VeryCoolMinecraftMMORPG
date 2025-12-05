package me.randomkitty.verycoolminecraftmmorpg.entities.visual;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.entity.EntityRemoveEvent;

public class FakeItemEntity extends ItemEntity {

    public FakeItemEntity(Location location, org.bukkit.inventory.ItemStack itemStack) {
        this(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), ((CraftItemStack) itemStack).handle);
    }

    public FakeItemEntity(Level level, double posX, double posY, double posZ, ItemStack stack) {
        super(level, posX, posY, posZ, stack);
        // 3 seconds (60 ticks) before despawn
        this.age = 5965;
        this.setNeverPickUp();

    }



    @Override
    public void playerTouch(Player entity) {
        // Will never do anything so override for performance
    }


}
