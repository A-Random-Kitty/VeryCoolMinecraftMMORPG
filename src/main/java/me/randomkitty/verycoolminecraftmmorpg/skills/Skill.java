package me.randomkitty.verycoolminecraftmmorpg.skills;

import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerDataValue;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class Skill extends PlayerDataValue {

    private double xp;

    @Override
    protected void load(UUID uuid, YamlConfiguration data) {
        this.xp = data.getDouble("skills." + getKey() + ".xp");

    }

    @Override
    protected void save(UUID uuid, YamlConfiguration data) {
        data.set("skills." + getKey() + ".xp", this.xp);
    }

    public double getXp() {
        return this.xp;
    }

    public void addXp(double amount) {
        this.xp += amount;
    }
}
