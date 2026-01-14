package me.randomkitty.verycoolminecraftmmorpg.skills.combat;

import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import me.randomkitty.verycoolminecraftmmorpg.player.data.AttributeModifyingPlayerDataValue;
import me.randomkitty.verycoolminecraftmmorpg.skills.Skill;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatSkill extends Skill implements AttributeModifyingPlayerDataValue {

    private static final Map<UUID, CombatSkill> playerCombatMap = new HashMap<>();

    public static double getPlayerCombatXp(UUID uuid) { return playerCombatMap.get(uuid).getXp(); }
    public static void addPlayerCombatXp(UUID uuid, double amount) { playerCombatMap.get(uuid).addXp(amount); }

    public CombatSkill(Player player) {
        playerCombatMap.put(player.getUniqueId(), this);
    }

    @Override
    public String getKey() {
        return "combat";
    }

    @Override
    protected void unload(UUID uuid) {
        playerCombatMap.remove(uuid);
    }

    @Override
    public void applyAdditiveAttributes(PlayerAttributes attributes) {

    }

    @Override
    public void applyMultiplicativeAttributes(PlayerAttributes attributes) {
        attributes.totalDamage *= 1 + (0.05 * level);
    }
}
