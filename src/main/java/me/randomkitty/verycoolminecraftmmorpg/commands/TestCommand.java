package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomSheep;
import me.randomkitty.verycoolminecraftmmorpg.entities.creatures.GrassySheep;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(sender instanceof Player player))
            return true;

        ServerLevel level = ((CraftWorld) player.getWorld()).getHandle();

        CustomSheep testCreature = new GrassySheep(player.getLocation());
        testCreature.spawn(player.getLocation());

        return true;
    }
}
