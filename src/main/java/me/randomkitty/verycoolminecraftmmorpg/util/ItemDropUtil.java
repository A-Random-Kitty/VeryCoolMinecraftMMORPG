package me.randomkitty.verycoolminecraftmmorpg.util;

import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityDefaultDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityRareDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.visual.FakeItemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemDropUtil {

    public static void givePlayerKillRewards(Player player, List<CustomEntityRareDrop> rareDrops, List<CustomEntityDefaultDrop> defaultDrops, net.minecraft.world.entity.Entity entity) {
        for (CustomEntityRareDrop rareDrop : rareDrops) {
            if (rareDrop.shouldDrop()) {
                givePlayerLootOrDrop(player, rareDrop.getItem(), new Location(entity.level().getWorld(), entity.getX(), entity.getY(), entity.getZ()));
            }
        }

        for (CustomEntityDefaultDrop defaultDrop : defaultDrops) {
            givePlayerLootOrDrop(player, defaultDrop.getDrop(), new Location(entity.level().getWorld(), entity.getX(), entity.getY(), entity.getZ()));
        }
    }

    public static boolean givePlayerLootOrDrop(Player player, ItemStack item) {
        return givePlayerLootOrDrop(player, item, player.getEyeLocation());
    }

    public static boolean givePlayerLootOrDrop(Player player, ItemStack item, Location dropLocation) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        ServerLevel level = ((CraftWorld)dropLocation.getWorld()).getHandle();

        int count = nmsStack.getCount();
        boolean added = nmsPlayer.getInventory().add(nmsStack);


        if (!added || !nmsStack.isEmpty()) {
            Bukkit.getLogger().info("test123123");
            ItemEntity itemEntity = new ItemEntity(level, dropLocation.getX(), dropLocation.getY(), dropLocation.getZ(), nmsStack);
            level.addFreshEntity(itemEntity);
            return false;
        } else {
            Bukkit.getLogger().info("test456456");
            // Drop a cool looking fake item on the ground
            nmsStack.setCount(count);
            FakeItemEntity fakeItemEntity = new FakeItemEntity(level, dropLocation.getX(), dropLocation.getY(), dropLocation.getZ(), nmsStack);
            level.addFreshEntity(fakeItemEntity);
            return true;
        }
    }
}
