package tz.co.nezatech.neighbor.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    public static String randomDigits(int digits) {
        String strMn = "1";
        String strMx = "9";
        for (int i = 0; i < digits - 1; i++) {
            strMn += "0";
            strMx += "9";
        }
        long min = Long.parseLong(strMn);
        long max = Long.parseLong(strMx);
        System.out.println(min+" to "+max);
        long randomNum = ThreadLocalRandom.current().nextLong(min, max + 1);
        return String.valueOf(randomNum);
    }

    public static void main(String[] args) {
        String s = RandomUtil.randomDigits(10);
        System.out.println(s);
    }
}
