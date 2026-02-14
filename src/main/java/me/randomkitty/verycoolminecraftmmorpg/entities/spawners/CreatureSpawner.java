package me.randomkitty.verycoolminecraftmmorpg.entities.spawners;


import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomCreatureType;
import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomCreature;
import me.randomkitty.verycoolminecraftmmorpg.entities.creatures.PackWolf;
import me.randomkitty.verycoolminecraftmmorpg.util.RandomUtil;
import net.minecraft.server.rcon.thread.RconClient;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreatureSpawner<T extends PathfinderMob & CustomCreature> {

    private final List<T> spawnedCreatures = new ArrayList<>();

    private final CustomCreatureType<T> type;
    private final Location spawnLocation;
    private final int maxMobs;
    private final int mobsPerSpawn;
    private final int spawnInterval;
    private final double launchVelocity;

    private int interval = 0;

    public CreatureSpawner(CustomCreatureType<T> type, Location location, int maxMobs, int mobsPerSpawn, int spawnInterval, double launchVelocity) {
        this.type = type;
        this.spawnLocation = location;
        this.maxMobs = maxMobs;
        this.mobsPerSpawn = mobsPerSpawn;
        this.spawnInterval = spawnInterval;
        this.launchVelocity = launchVelocity;
    }

    public void spawnMobsWithInterval() {
        interval++;

        if (spawnLocation.isChunkLoaded() && interval >= spawnInterval) {
            interval = 0;
            spawnMobs();
        }
    }

    public boolean canAttemptSpawn() {
        return spawnLocation.isChunkLoaded();
    }

    public void spawnMobs() {
        spawnedCreatures.removeIf(mob -> mob == null || !mob.isAlive());

        if (spawnLocation.isChunkLoaded()) {
            int currentMobs = spawnedCreatures.size();
            int spawnedInCycle = 0;

            while (currentMobs < maxMobs && spawnedInCycle < mobsPerSpawn) {
                T creature = type.spawnNewCreature(spawnLocation);
                if (launchVelocity != 0) {
                    creature.setDeltaMovement(RandomUtil.randomDouble(-launchVelocity, launchVelocity), RandomUtil.randomDouble(launchVelocity/2, launchVelocity), RandomUtil.randomDouble(-launchVelocity, launchVelocity));
                }
                spawnedCreatures.add(creature);

                currentMobs++;
                spawnedInCycle++;
            }
        }
    }

    public void removeMobs() {
        for (T mob : spawnedCreatures) {
            if (mob != null) {
                mob.remove(Entity.RemovalReason.DISCARDED);
            }
        }

        spawnedCreatures.clear();
    }


    public static CreatureSpawner<?> fromConfiguration(ConfigurationSection configuration) {
        CustomCreatureType<?> type = CustomCreatureType.fromString(configuration.getString("type"));
        Location location = configuration.getLocation("location");
        int maxMobs = configuration.getInt("max_mobs");
        int mobsPerSpawn = configuration.getInt("mobs_per_spawn");
        int spawnInterval = configuration.getInt("spawn_interval");
        double launchVelocity = configuration.getDouble("launch_velocity");

        if (type != null) {
            if (location != null) {
                return new CreatureSpawner<>(type, location, maxMobs, mobsPerSpawn, spawnInterval, launchVelocity);
            } else {
                VeryCoolMinecraftMMORPG.LOGGER.warning("Failed to load spawner " + configuration.getName() + ": location is null");
                return null;
            }
        } else {
            VeryCoolMinecraftMMORPG.LOGGER.warning("Failed to load spawner " + configuration.getName() + ": type is null");
            return null;
        }
    }
}
