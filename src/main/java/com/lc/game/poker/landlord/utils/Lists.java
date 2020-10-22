package com.lc.game.poker.landlord.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author HerdingWolf
 * @date 2020/10/21
 */
public class Lists {

    public static <E> List<E> newArrayList(E... e) {
        List<E> result = new ArrayList<>(e.length);
        Collections.addAll(result, e);
        return result;
    }
}
