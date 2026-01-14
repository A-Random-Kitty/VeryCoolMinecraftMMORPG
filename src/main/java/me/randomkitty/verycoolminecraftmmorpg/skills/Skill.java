package me.randomkitty.verycoolminecraftmmorpg.skills;

import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerDataValue;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public abstract class Skill extends PlayerDataValue {

    private static final int BASE_XP_REQ = 100;

    protected int level;
    protected double xp;

    @Override
    protected void load(UUID uuid, YamlConfiguration data) {
        this.xp = data.getDouble("skills." + getKey() + ".xp");
        calculateLevel();
    }

    @Override
    protected void save(UUID uuid, YamlConfiguration data) {
        data.set("skills." + getKey() + ".xp", this.xp);
    }

    protected void calculateLevel() {
        int newLevel = 0;
        int xpReq = BASE_XP_REQ;

        while (this.xp >= xpReq) {
            newLevel++;
            xpReq += 100;
        }

        while (level < newLevel) {
            level++;

        }
    }

    public double getXp() {
        return this.xp;
    }

    public void addXp(double amount) {
        this.xp += amount;
        calculateLevel();
    }

    public double getLevel() {
        return this.level;
    }
}
