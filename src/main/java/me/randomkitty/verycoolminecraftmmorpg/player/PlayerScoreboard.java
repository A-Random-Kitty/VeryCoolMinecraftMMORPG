package me.randomkitty.verycoolminecraftmmorpg.player;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import me.randomkitty.verycoolminecraftmmorpg.VeryCoolMinecraftMMORPG;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerDataValue;
import me.randomkitty.verycoolminecraftmmorpg.skills.Skill;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class PlayerScoreboard {

    public static void addPlayerColorToName(Player player, NamedTextColor color) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();

            if (board.getTeam("player_" + player.getName()) instanceof Team t) {
                t.addEntry(player.getName());
                t.color(color);
            } else {
                Team t = board.registerNewTeam("player_" + player.getName());
                t.addEntry(player.getName());
                t.color(color);
            }


        }
    }

    public static void applyPlayerScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("kittyisle", Criteria.DUMMY, Component.text("Kitty Isle").color(NamedTextColor.DARK_AQUA));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blank1 = obj.getScore(" ");
        blank1.setScore(5);
        blank1.numberFormat(NumberFormat.blank());

        Team coinsteam = board.registerNewTeam("coinsteam");
        Score coinsscore = obj.getScore("Coins: ");
        coinsteam.addEntry("Coins: ");
        coinsteam.setSuffix(ChatColor.GOLD + StringUtil.formatedDouble(PlayerCurrency.getCoins(player.getUniqueId())));
        coinsscore.setScore(4);
        coinsscore.numberFormat(NumberFormat.blank());

        Team skillLevelTeam =  board.registerNewTeam("skilllevelteam");
        Score skillLevelScore = obj.getScore("Skill: ");
        skillLevelTeam.addEntry("Skill: ");
        skillLevelTeam.suffix(Component.text(" "));
        skillLevelScore.setScore(3);
        skillLevelScore.numberFormat(NumberFormat.blank());

        Team skillXpTeam = board.registerNewTeam("skillxpteam");
        Score skillXpScore = obj.getScore("Xp: ");
        skillXpTeam.addEntry("Xp: ");
        skillXpTeam.suffix(Component.text(" "));
        skillXpScore.setScore(2);
        skillXpScore.numberFormat(NumberFormat.blank());

        Score blank2 = obj.getScore("  ");
        blank2.setScore(1);
        blank2.numberFormat(NumberFormat.blank());

        Score ipscore = obj.getScore(ChatColor.AQUA + "kittyisle.minekeep.gg");
        ipscore.setScore(0);
        ipscore.numberFormat(NumberFormat.blank());

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (board.getTeam("player_" + p.getName()) == null) {
                Team t = board.registerNewTeam("player_" + p.getName());
                t.addEntry(p.getName());

                String prefix = VeryCoolMinecraftMMORPG.RANK_PROVIDER.getMetaData(p).getPrefix();

                if (prefix != null) {
                    TextColor color = LegacyComponentSerializer.legacyAmpersand().deserialize(prefix).color();
                    if (color != null) {
                        t.color(NamedTextColor.nearestTo(color));
                    }
                }
            } else {
                Team t = board.getTeam("player_" + p.getName());

                assert t != null;

                String prefix = VeryCoolMinecraftMMORPG.RANK_PROVIDER.getMetaData(p).getPrefix();

                if (!t.hasEntry(p.getName())) {
                    t.addEntry(p.getName());
                }

                if (prefix != null) {
                    TextColor color = LegacyComponentSerializer.legacyAmpersand().deserialize(prefix).color();
                    if (color != null) {
                        t.color(NamedTextColor.nearestTo(color));
                    }
                }
            }


        }

        player.setScoreboard(board);
    }

    public static void updateCoins(Player player) {
        Team coinsteam = player.getScoreboard().getTeam("coinsteam");

        if (coinsteam != null) {
            coinsteam.setSuffix(ChatColor.GOLD + StringUtil.formatedDouble(PlayerCurrency.getCoins(player.getUniqueId())));
        }
    }

    public static void updateXp(Player player, Skill skill) {
        Team xpTeam = player.getScoreboard().getTeam("skillxpteam");
        Team levelTeam = player.getScoreboard().getTeam("skilllevelteam");

        if (xpTeam != null) {
            xpTeam.suffix(Component.text(((int) skill.getXpOutOfNextLevel()) + "/" + skill.nextXpReq, NamedTextColor.DARK_AQUA));
        }

        if (levelTeam != null) {
            levelTeam.suffix(Component.text(skill.getName() + " " + skill.getLevel(), NamedTextColor.AQUA));
        }
    }

}
