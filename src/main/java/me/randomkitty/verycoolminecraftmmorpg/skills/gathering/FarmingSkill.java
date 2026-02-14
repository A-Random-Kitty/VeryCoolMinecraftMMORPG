package me.randomkitty.verycoolminecraftmmorpg.skills.gathering;

import me.randomkitty.verycoolminecraftmmorpg.skills.Skill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FarmingSkill extends Skill {

    private static final Map<UUID, FarmingSkill> playerFarmingMap = new HashMap<>();

    public static double getPlayerCombatXp(UUID uuid) { return playerFarmingMap.get(uuid).getXp(); }
    public static void addPlayerCombatXp(UUID uuid, double amount) { playerFarmingMap.get(uuid).addXp(amount); }

    public FarmingSkill(Player player) {
        super(player);
        playerFarmingMap.put(player.getUniqueId(), this);
    }

    @Override
    protected void unload(UUID uuid) {
        playerFarmingMap.remove(player.getUniqueId());
    }

    @Override
    public void onLevelUp() {
        this.player.sendMessage(Component.text("LEVEL UP! ", NamedTextColor.AQUA, TextDecoration.BOLD).append(Component.text("Farming " + level, NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false)));

    }

    @Override
    public String getName() {
        return "Farming";
    }

    @Override
    public String getKey() {
        return "farming";
    }
}
