package me.randomkitty.verycoolminecraftmmorpg.entities.visual;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.EntityRemoveEvent;

public class DisappearingTextDisplay extends Display.TextDisplay {

    private int age;
    private final int lifetime;

    public DisappearingTextDisplay(Level level, int lifetime) {
        super(EntityType.TEXT_DISPLAY, level);
        this.persist = false;
        this.age = 0;
        this.lifetime = lifetime;
    }

    public static DisappearingTextDisplay spawn(Location location, String text, int lifetime) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        DisappearingTextDisplay display = new DisappearingTextDisplay(level, lifetime);
        display.setPos(location.getX(), location.getY() + 1, location.getZ());
        display.setText(Component.literal(text));
        display.setBillboardConstraints(BillboardConstraints.CENTER);
        level.addFreshEntity(display);
        return display;
    }

    @Override
    public void inactiveTick() {
        super.inactiveTick();
        age++;
        if (age > lifetime) {
            this.discard(EntityRemoveEvent.Cause.DESPAWN);
        }
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        if (age > lifetime) {
            this.discard(EntityRemoveEvent.Cause.DESPAWN);
        }
    }
}
