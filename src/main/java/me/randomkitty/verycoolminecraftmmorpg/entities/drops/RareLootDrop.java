package me.randomkitty.verycoolminecraftmmorpg.entities.drops;

import me.randomkitty.verycoolminecraftmmorpg.item.CustomItem;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RareLootDrop {

    private static final Random random = new Random();

    private final float chance;
    private final CustomItem item;
    private final boolean tellPlayer;

    public RareLootDrop(float chance, CustomItem item) {
        this(chance, item, false);
    }

    public RareLootDrop(float chance, CustomItem item, boolean tellPlayer) {
        this.chance = chance;
        this.item = item;
        this.tellPlayer = tellPlayer;
    }

    public ItemStack getItem() {
        return item.newItemStack();
    }

    public TextComponent getMessage() {

            return Component.text(item.getRarity().getText() + " DROP! ", item.getRarity().getColor(), TextDecoration.BOLD).append(item.getColoredName().append(Component.text(" [" + StringUtil.formatedDouble(chance * 100) + "%]", NamedTextColor.AQUA)).decoration(TextDecoration.BOLD, false));
    }

    public boolean shouldTellPlayer() {
        return tellPlayer;
    }


    public boolean shouldDrop() {
        return Math.abs(random.nextFloat()) <= chance;
    }

    public boolean shouldDrop(float weight) {
        return Math.abs(random.nextFloat() * weight) <= chance;
    }

}
