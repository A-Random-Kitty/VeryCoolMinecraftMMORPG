package me.randomkitty.verycoolminecraftmmorpg.player;

import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerDataValue;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerAccomplishments extends PlayerDataValue {

    private static Map<UUID, PlayerAccomplishments> playerAccomplishmentsMap = new HashMap<>();

    public static PlayerAccomplishments getAccomplishments(Player player) {
        return playerAccomplishmentsMap.get(player.getUniqueId());
    }

    public static boolean hasAccomplishment(Player player, String accomplishment) {
        PlayerAccomplishments playerAccomplishments = playerAccomplishmentsMap.get(player.getUniqueId());

        if (playerAccomplishments == null) return false;

        for (String s : playerAccomplishments.accomplishments) {
            if (accomplishment.equals(s)) return true;
        }

        return false;
    }

    public static boolean grantAccomplishment(Player player, String accomplishment) {
        PlayerAccomplishments playerAccomplishments = playerAccomplishmentsMap.get(player.getUniqueId());

        if (playerAccomplishments == null) return false;

        for (String s : playerAccomplishments.accomplishments) {
            if (accomplishment.equals(s)) return false;
        }

        playerAccomplishments.accomplishments.add(accomplishment);
        return true;
    }

    private List<String> accomplishments;

    public PlayerAccomplishments(Player player) {
        super(player);
        playerAccomplishmentsMap.put(player.getUniqueId(), this);
    }

    @Override
    public String getKey() {
        return "accomplishments";
    }

    @Override
    protected void save(UUID uuid, YamlConfiguration data) {
        data.set("accomplishments", accomplishments);
    }

    @Override
    protected void load(UUID uuid, YamlConfiguration data) {
        accomplishments = data.getStringList("accomplishments");
    }

    @Override
    protected void unload(UUID uuid) {

    }
}
