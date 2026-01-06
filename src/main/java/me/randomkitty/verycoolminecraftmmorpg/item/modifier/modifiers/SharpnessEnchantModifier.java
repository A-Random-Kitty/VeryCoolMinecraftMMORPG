package me.randomkitty.verycoolminecraftmmorpg.item.modifier.modifiers;

import me.randomkitty.verycoolminecraftmmorpg.item.modifier.ItemModifier;
import me.randomkitty.verycoolminecraftmmorpg.player.attributes.PlayerAttributes;
import me.randomkitty.verycoolminecraftmmorpg.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;

import java.util.List;

public class SharpnessEnchantModifier extends ItemModifier implements EnchantmentModifier {

    private static final float multi = 0.08F;

    public SharpnessEnchantModifier(String key) {
        super(key);
    }

    @Override
    public void applyAdditiveStatBonuses(PlayerAttributes attributes, int level) {}

    @Override
    public void applyMultiStatBonuses(PlayerAttributes attributes, int level) {
        attributes.totalDamage *= (1 + (multi * level));
        attributes.totalCriticalDamage *= (1 + (multi * level));
    }

    @Override
    public String getName() {
        return "Sharpness"; // ѕʜᴀʀᴘɴᴇѕѕ
    }

    @Override
    public List<TextComponent> getLore(int level) {
        return List.of(
                Component.text("Boosts total melee damage by ", NamedTextColor.GRAY)
                        .append(Component.text(StringUtil.formatedDouble((level * multi * 100)) + "%", NamedTextColor.GREEN))
                        .decoration(TextDecoration.ITALIC, false)
        );
    }
}
