package me.randomkitty.verycoolminecraftmmorpg.entities.creatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures.CustomSheep;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.item.DyeColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class GrassySheep extends CustomSheep {

    public GrassySheep(Location location) {
        super(location);

        setColor(DyeColor.LIME);
        baseName = ChatColor.GREEN + "Grassy Sheep";

        getAttribute(Attributes.MAX_HEALTH).setBaseValue(25);
        setHealth(25);

        updateDisplayName();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1F));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }
}
