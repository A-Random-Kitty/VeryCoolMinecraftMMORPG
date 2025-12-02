package me.randomkitty.verycoolminecraftmmorpg.skills.gathering;

import org.bukkit.Location;

public class ResourceNode {

    private int maxResources;
    private int regenTime;

    private final Location[] locations;

    public ResourceNode(Location[] locations) {
        this.locations = locations;
    }
}
