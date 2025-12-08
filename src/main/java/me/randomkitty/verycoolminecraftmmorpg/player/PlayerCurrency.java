package me.randomkitty.verycoolminecraftmmorpg.player;

import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerDataValue;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCurrency extends PlayerDataValue {

    private final static Map<UUID, PlayerCurrency> playerCurrencyMap = new HashMap<>();

    public static double getCoins(UUID uuid) { return playerCurrencyMap.get(uuid).coins; }
    public static void addCoins(UUID uuid, double coins) { playerCurrencyMap.get(uuid).coins += coins; }
    public static boolean spendCoins (UUID uuid, double amount) {
        PlayerCurrency currency = playerCurrencyMap.get(uuid);

        if (amount <= currency.coins) {
            currency.coins -= amount;
            return true;
        } else {
            return false;
        }
    }

    private double coins;

    public PlayerCurrency(Player player) {
        super();
        playerCurrencyMap.put(player.getUniqueId(), this);
    }

    @Override
    public String getKey() {
        return "currency";
    }

    @Override
    protected void save(UUID uuid, YamlConfiguration data) {
        data.set(getKey() + ".coins", coins);
    }

    @Override
    protected void load(UUID uuid, YamlConfiguration data) {
        coins = data.getDouble(getKey() + ".coins");
    }

    @Override
    protected void unload(UUID uuid) {
        playerCurrencyMap.remove(uuid);
    }
}
