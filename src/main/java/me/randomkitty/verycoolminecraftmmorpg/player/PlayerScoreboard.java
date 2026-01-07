package me.randomkitty.verycoolminecraftmmorpg.player;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerScoreboard {

    public static void applyPlayerScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("kittyisle", Criteria.DUMMY, Component.text("Kitty Isle").color(NamedTextColor.DARK_AQUA));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team coinsteam = board.registerNewTeam("coinsteam");
        Score coinsscore = obj.getScore("Coins: ");
        coinsteam.addEntry("Coins: ");
        coinsteam.setSuffix(ChatColor.GOLD + StringUtil.formatedDouble(PlayerCurrency.getCoins(player.getUniqueId())));
        coinsscore.setScore(2);
        coinsscore.numberFormat(NumberFormat.blank());

        Score blank1 = obj.getScore(" ");
        blank1.setScore(1);
        blank1.numberFormat(NumberFormat.blank());

        Score ipscore = obj.getScore(ChatColor.AQUA + "kittyisle.minekeep.gg");
        ipscore.setScore(0);
        ipscore.numberFormat(NumberFormat.blank());

        player.setScoreboard(board);
    }

    public static void updateCoins(Player player) {
        Team coinsteam =  player.getScoreboard().getTeam("coinsteam");

        if (coinsteam != null) {
            coinsteam.setSuffix(ChatColor.GOLD + StringUtil.formatedDouble(PlayerCurrency.getCoins(player.getUniqueId())));
        }
    }

}
