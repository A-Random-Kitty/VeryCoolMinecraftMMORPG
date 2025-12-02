package me.randomkitty.verycoolminecraftmmorpg.entities;

import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomSheep;
import net.minecraft.world.entity.EntityType;

public class CustomEntityType {

    public static EntityType<CustomSheep> SHEEP_CREATURE;


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
            Bukkit.getLogger().severe("Failed to unfreeze registry");
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
