package me.randomkitty.verycoolminecraftmmorpg.skills.combat;

import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import me.randomkitty.verycoolminecraftmmorpg.player.data.AttributeModifyingPlayerDataValue;
import me.randomkitty.verycoolminecraftmmorpg.skills.Skill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatSkill extends Skill implements AttributeModifyingPlayerDataValue {

    private static final int damageBoostPercent = 3;

    private static final Map<UUID, CombatSkill> playerCombatMap = new HashMap<>();

    public static double getPlayerCombatXp(UUID uuid) { return playerCombatMap.get(uuid).getXp(); }
    public static void addPlayerCombatXp(UUID uuid, double amount) { playerCombatMap.get(uuid).addXp(amount); }
    public static CombatSkill getPlayerCombatSkill(UUID uuid) { return playerCombatMap.get(uuid); }

    public CombatSkill(Player player) {
        super(player);
        playerCombatMap.put(player.getUniqueId(), this);
    }

    @Override
    public void onLevelUp() {
        this.player.sendMessage(Component.text("LEVEL UP! ", NamedTextColor.AQUA, TextDecoration.BOLD).append(Component.text("Combat " + level, NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false)));
        this.player.sendMessage(Component.text(" - ", NamedTextColor.GRAY).append(Component.text("+" + (level - 1) * damageBoostPercent + "% damage", NamedTextColor.GRAY, TextDecoration.STRIKETHROUGH).append(Component.text(" -> ").append(Component.text("+" + level * damageBoostPercent + "% damage", NamedTextColor.GREEN)).decoration(TextDecoration.STRIKETHROUGH, false))));
    }

    @Override
    public String getName() {
        return "Combat";
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
        attributes.totalDamage *= 1 + (0.01 * damageBoostPercent * level);
        attributes.totalCriticalDamage *= 1 + (0.01 * damageBoostPercent * level);
    }
}
