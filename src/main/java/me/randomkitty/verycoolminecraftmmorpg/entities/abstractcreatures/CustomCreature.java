package me.randomkitty.verycoolminecraftmmorpg.entities.abstractcreatures;

import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityDefaultDrop;
import me.randomkitty.verycoolminecraftmmorpg.entities.CustomEntityRareDrop;

import java.util.ArrayList;
import java.util.List;

public interface CustomCreature {


    List<CustomEntityDefaultDrop> defaultDrops = new ArrayList<>();
    List<CustomEntityRareDrop> rareDrops = new ArrayList<>();

    double getBaseCoinDrop();
    double getBaseXpDrop();
    String baseName();

}