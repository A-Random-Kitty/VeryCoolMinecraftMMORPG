package me.randomkitty.verycoolminecraftmmorpg.skills.combat;

import me.randomkitty.verycoolminecraftmmorpg.skills.Skill;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatSkill extends Skill {

    private static final Map<UUID, CombatSkill> playerCombatMap = new HashMap<>();

    @Override
    public String getKey() {
        return "combat";
    }

    @Override
    protected void unload(UUID uuid) {
        playerCombatMap.remove(uuid);
    }
}
