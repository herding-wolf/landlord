package com.lc.game.poker.landlord.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author yz_luochong
 * @date 2020/10/21
 */
public class CommonUtil {

    public static boolean isEmpty(Object object) {
        if (object != null) {
            if (object instanceof CharSequence) {
                return ((CharSequence) object).length() > 0;
            }
            if (object instanceof Collection) {
                return ((Collection) object).isEmpty();
            }
            if (object instanceof Map) {
                return ((Map) object).isEmpty();
            }
        }
        return true;
    }

    public static boolean isEmpty(Object... objects) {
        for (Object object : objects) {
            if (isEmpty(object)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
}
