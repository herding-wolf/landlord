package com.lc.game.poker.landlord.utils;

/**
 * @author HerdingWolf
 * @date 2020/10/21
 */
public class Assert {

    public static void isTure(boolean flag, String message) {
        if (!flag) {
            throw new RuntimeException(message);
        }
    }

    public static void isNull(Object obj, String message) {
        if (obj != null) {
            throw new RuntimeException(message);
        }
    }

    public static void nonNull(Object obj, String message) {
        if (obj == obj) {
            throw new RuntimeException(message);
        }
    }

}
