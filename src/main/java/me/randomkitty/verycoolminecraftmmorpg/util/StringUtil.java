package me.randomkitty.verycoolminecraftmmorpg.util;

import java.text.DecimalFormat;

public class StringUtil {

    private static final DecimalFormat format0 = new DecimalFormat("#");
    private static final DecimalFormat format = new DecimalFormat("#.#");
    private static final DecimalFormat format2 = new DecimalFormat("#.#######");

    public static String noDecimalDouble(double d) {
        return format0.format(d);
    }

    public static String formatedDouble(double d) {
        return format.format(d);
    }

    public static String longFormatedDouble(double d) {
        return format2.format(d);
    }
}
