package me.randomkitty.verycoolminecraftmmorpg.commands;

import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.player.PlayerAccomplishments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ConditionalTpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length < 6) return true;

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) return true;

        VeryCoolMinecraftMMORPG.LOGGER.info(Arrays.toString(args));

        if (PlayerAccomplishments.hasAccomplishment(player, args[1])) {
            World tpWorld = Bukkit.getWorld(args[2]);

            if (tpWorld == null) return true;

            try {
                Location location = new Location(tpWorld, Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Float.parseFloat(args[6]), Float.parseFloat(args[7]));

                player.teleport(location);

                player.sendMessage(JSONComponentSerializer.json().deserialize(args[8].replace("\\s", " ")));
            } catch (NumberFormatException e) {
                VeryCoolMinecraftMMORPG.LOGGER.info("Failed to parse conditional tp location");
            }
        } else {
            player.sendMessage(JSONComponentSerializer.json().deserialize(args[9].replace("\\s", " ")));
        }

        return true;
    }
}
