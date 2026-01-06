package me.randomkitty.verycoolminecraftmmorpg.util;

import java.util.TreeMap;

public class NumberUtil {

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public static String toRoman(int number) {

        if (number == 0) {
            return "0";
        }

        boolean negative = false;

        if (number < 0) {
            negative = true;
            number = -number;
        }

        if (number > 5000) {
            return String.valueOf(number);
        }

        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }

        if (negative) {
            return "-" + map.get(l) + toRoman(number-l);
        }

        return map.get(l) + toRoman(number-l);
    }
}
