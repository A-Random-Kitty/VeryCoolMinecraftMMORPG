package me.randomkitty.verycoolminecraftmmorpg.spells;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import net.minecraft.world.entity.projectile.Projectile;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public abstract class ProjectileSpell {

    private final Entity shooter;
    private Location location;
    private Vector velocity;
    private Particle particle;
    private final double range;

    protected boolean canHitMultipleEntities = false;
    protected boolean throughWalls = false;
    protected boolean bounce = false;

    public boolean isAlive;
    private double distanceTraveled;

    public ProjectileSpell(Entity shooter, Location location, Vector velocity, Particle particle, double range) {
        this.shooter = shooter;
        this.location = location;
        this.velocity = velocity;
        this.particle = particle;
        this.range = range;
    }

    public void tick() {

        BoundingBox box = new BoundingBox(location.getX(), location.getY(), location.getZ(), location.getX(), location.getY(), location.getZ());
        RayTraceResult result = box.rayTrace(location.toVector(), velocity, velocity.length());

        if (result != null) {

            Vector hitPosition = result.getHitPosition();
            Block hitBlock = result.getHitBlock();
            Entity hitEntity = result.getHitEntity();

            if (hitBlock == null) {
                onHitWall(result);
            }

            if (hitEntity != null && !canHitMultipleEntities) {
                onHitEntity(hitEntity);
            }


        } else {
            VeryCoolMinecraftMMORPG.LOGGER.warning("Failed raytrace for projectile spell");
        }

        box.expand(velocity);


        location.add(velocity);
        distanceTraveled += velocity.length();

        location.getWorld().spawnParticle(particle, location, 1);

        location.getWorld().getNearbyEntities(box);
    }

    abstract void onHitWall(RayTraceResult result);
    abstract void onHitEntity(Entity entity);

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
