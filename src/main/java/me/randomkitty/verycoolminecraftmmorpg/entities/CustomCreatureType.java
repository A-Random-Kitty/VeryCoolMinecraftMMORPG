package me.randomkitty.verycoolminecraftmmorpg.entities;

import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomCreature;
import me.randomkitty.verycoolminecraftmmorpg.entities.creatures.GrassyRam;
import me.randomkitty.verycoolminecraftmmorpg.entities.creatures.GrassySheep;
import me.randomkitty.verycoolminecraftmmorpg.entities.creatures.PackWolf;
import me.randomkitty.verycoolminecraftmmorpg.entities.creatures.ZombieCreature;
import me.randomkitty.verycoolminecraftmmorpg.entities.creatures.bosses.AlphaWolfBoss;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;

public class CustomCreatureType<T extends PathfinderMob & CustomCreature> {

    public static final CustomCreatureType<GrassySheep> GRASSY_SHEEP = new CustomCreatureType<>(GrassySheep::new);
    public static final CustomCreatureType<GrassyRam> GRASSY_RAM = new CustomCreatureType<>(GrassyRam::new);
    public static final CustomCreatureType<PackWolf> PACK_WOLF = new CustomCreatureType<>(PackWolf::new);
    public static final CustomCreatureType<AlphaWolfBoss> ALPHA_WOLF = new CustomCreatureType<>(AlphaWolfBoss::new);

    private static final Map<String, CustomCreatureType<?>> stringToType = Map.ofEntries(
            Map.entry("grassy_sheep", GRASSY_SHEEP),
            Map.entry("grassy_ram", GRASSY_RAM),
            Map.entry("pack_wolf", PACK_WOLF),
            Map.entry("alpha_wolf", ALPHA_WOLF)
    );

    public static CustomCreatureType<?> fromString(String s) {
        return stringToType.get(s);
    }

    private final CreatureFactory<T> factory;

    public CustomCreatureType(CreatureFactory<T> factory) {
        this.factory = factory;
    }

    public T newCreature(World world) {
        return factory.newCreature(world);
    }

    public T spawnNewCreature(Location location) {
        T newCreature = factory.newCreature(location.getWorld());
        newCreature.spawn(location, newCreature);
        return newCreature;
    }

    @FunctionalInterface
    public interface CreatureFactory<T extends PathfinderMob & CustomCreature> {
        T newCreature(World world);
    }

    // Old registry stuff that I don't feel like dealing with

    /*
    public static void registerAllEntities() {
        ResourceLocation key = ResourceLocation.fromNamespaceAndPath(VeryCoolMinecraftMMORPG.NAMESPACE, "test_path");

        Registry<EntityType<?>> registry = ((CraftServer) Bukkit.getServer()).getServer().registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);

        // Attempt to unfreeze registry (idk what I'm doing)
        try {
            Field frozen =  MappedRegistry.class.getDeclaredField("frozen");
            frozen.setAccessible(true);
            frozen.setBoolean(registry,false);

            Field unregisteredIntrusiveHolders = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
            unregisteredIntrusiveHolders.setAccessible(true);
            unregisteredIntrusiveHolders.set(registry, new IdentityHashMap<>());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            VeryCoolMinecraftMMORPG.LOGGER.severe("Failed to unfreeze registry");
            throw new RuntimeException(e);
        }

        EntityType<?> builder = EntityType.Builder.of(SheepCreature::new, EntityType.SHEEP.getCategory()).build(ResourceKey.create(Registries.ENTITY_TYPE, key));

        registry.createIntrusiveHolder(builder);
        SHEEP_CREATURE = (EntityType<SheepCreature>) Registry.register(registry, "test_path", builder);

        // Freeze the registry again, might have to do something with intrusive holders, but it seems fine for now
        try {
            Field frozen = MappedRegistry.class.getDeclaredField("frozen");
            frozen.setAccessible(true);
            frozen.setBoolean(registry,false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

     */
}
