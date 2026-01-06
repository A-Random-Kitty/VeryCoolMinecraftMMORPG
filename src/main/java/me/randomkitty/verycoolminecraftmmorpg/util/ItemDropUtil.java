package me.randomkitty.verycoolminecraftmmorpg.util;

import me.randomkitty.verycoolminecraftmmorpg.entities.visual.FakeItemEntity;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerCurrency;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerScoreboard;
import me.randomkitty.verycoolminecraftmmorpg.skills.combat.CombatSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDropUtil {

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
            ItemEntity itemEntity = new ItemEntity(level, dropLocation.getX(), dropLocation.getY(), dropLocation.getZ(), nmsStack);
            level.addFreshEntity(itemEntity);
            return false;
        } else {
            // Drop a cool looking fake item on the ground
            nmsStack.setCount(count);
            FakeItemEntity fakeItemEntity = new FakeItemEntity(level, dropLocation.getX(), dropLocation.getY(), dropLocation.getZ(), nmsStack);
            level.addFreshEntity(fakeItemEntity);
            return true;
        }
    }

    public static void givePlayerCoinsAndDrop(Player player, double coins, Entity e) {
        PlayerCurrency.addCoins(player.getUniqueId(), coins);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
        PlayerScoreboard.updateCoins(player);

        ItemStack item = new ItemStack(Material.SUNFLOWER);

        FakeItemEntity entity = new FakeItemEntity(e.level(), e.getX(), e.getY(), e.getZ(), CraftItemStack.asNMSCopy(item));
        entity.setCustomNameVisible(true);
        entity.setCustomName(Component.literal(ChatColor.GOLD +  StringUtil.formatedDouble(coins) + " ⓒ"));
        e.level().addFreshEntity(entity);
    }

    public static void givePlayerCoinsAndDrop(Player player, double coins, Location dropLocation) {
        PlayerCurrency.addCoins(player.getUniqueId(), coins);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
        PlayerScoreboard.updateCoins(player);

        ItemStack item = new ItemStack(Material.SUNFLOWER);
        ServerLevel level = ((CraftWorld)dropLocation.getWorld()).getHandle();

        FakeItemEntity entity = new FakeItemEntity(level, dropLocation.getX(), dropLocation.getY(), dropLocation.getZ(), CraftItemStack.asNMSCopy(item));
        entity.setCustomNameVisible(true);
        entity.setCustomName(Component.literal(ChatColor.GOLD + StringUtil.formatedDouble(coins) + " ⓒ"));
        level.addFreshEntity(entity);
    }

    public static void givePlayerCombatXpAndDrop(Player player, double xp, Entity e) {
        CombatSkill.addPlayerCombatXp(player.getUniqueId(), xp);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS);

        FakeItemEntity entity = new FakeItemEntity(e.level(), e.getX(), e.getY(), e.getZ(), CraftItemStack.asNMSCopy(item));
        entity.setCustomNameVisible(true);
        entity.setCustomName(Component.literal(ChatColor.AQUA +  StringUtil.formatedDouble(xp) + " ※"));
        e.level().addFreshEntity(entity);
    }

    public static void givePlayerCombatXpAndDrop(Player player, double xp, Location dropLocation) {
        CombatSkill.addPlayerCombatXp(player.getUniqueId(), xp);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);

        ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS);
        ServerLevel level = ((CraftWorld)dropLocation.getWorld()).getHandle();

        FakeItemEntity entity = new FakeItemEntity(level, dropLocation.getX(), dropLocation.getY(), dropLocation.getZ(), CraftItemStack.asNMSCopy(item));
        entity.setCustomNameVisible(true);
        entity.setCustomName(Component.literal(ChatColor.AQUA + StringUtil.formatedDouble(xp) + " ※"));
        level.addFreshEntity(entity);
    }
}
