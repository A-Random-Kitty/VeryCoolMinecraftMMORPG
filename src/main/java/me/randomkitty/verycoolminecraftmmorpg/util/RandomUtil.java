package me.randomkitty.verycoolminecraftmmorpg.util;

import java.util.Random;

public class RandomUtil {

    public static final Random RANDOM = new Random();

    public static double randomDouble(double min, double max) {
        return RANDOM.nextDouble(min, max);
    }

    public static boolean percentChance(float chance) {
        return Math.abs(RANDOM.nextFloat()) < chance;
    }
}
