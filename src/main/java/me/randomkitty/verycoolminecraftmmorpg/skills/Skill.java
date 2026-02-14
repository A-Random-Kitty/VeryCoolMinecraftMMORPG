package me.randomkitty.verycoolminecraftmmorpg.skills;

import me.randomkitty.verycoolminecraftmmorpg.player.PlayerScoreboard;
import me.randomkitty.verycoolminecraftmmorpg.player.data.PlayerDataValue;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public abstract class Skill extends PlayerDataValue {
    private static final int BASE_XP_REQ = 100;

    protected int level;
    protected double xp;
    protected double xpOutOfNextLevel;
    public int nextXpReq;

    public Skill(Player player) {
        super(player);
    }

    @Override
    protected void load(UUID uuid, YamlConfiguration data) {
        this.xp = data.getDouble("skills." + getKey() + ".xp");
        level = 0;
        calculateLevel();
    }

    @Override
    protected void save(UUID uuid, YamlConfiguration data) {
        data.set("skills." + getKey() + ".xp", this.xp);
    }

    protected boolean calculateLevel() {
        int newLevel = 0;
        int xpReq = BASE_XP_REQ;
        xpOutOfNextLevel = xp;

        while (this.xpOutOfNextLevel >= xpReq) {
            newLevel++;
            xpOutOfNextLevel -= xpReq;
            xpReq += BASE_XP_REQ * newLevel;
        }

        nextXpReq = xpReq;

        boolean leveledUp = false;

        while (level < newLevel) {
            level++;
            leveledUp = true;
        }

        return leveledUp;
    }

    public double getXp() {
        return this.xp;
    }
    public double getXpOutOfNextLevel() {
        return xpOutOfNextLevel;
    }

    public void addXp(double amount) {
        this.xp += amount;
        boolean leveledUp = calculateLevel();
        if (leveledUp) {
            onLevelUp();
        }

        PlayerScoreboard.updateXp(this.player, this);
    }

    public int getLevel() {
        return this.level;
    }

    public abstract void onLevelUp();
    public abstract String getName();
}
