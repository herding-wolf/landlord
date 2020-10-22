package com.lc.game.poker.landlord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * 牌型
 *
 * @author HerdingWolf
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum CardType {

    BOMB(new int[]{2, 4}, "炸弹"),
    FOUR_WITH(new int[]{6, 8}, "四带"),
    PLANE_WITH(new int[]{6, 8, 9, 10, 12, 15, 18, 20}, "飞机带"),
    THREE_WITH(new int[]{3, 4, 5}, "三带"),
    STRAIGHT(new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13}, "顺子"),
    SERIAL_PAIR(new int[]{6, 8, 10, 12, 14, 16, 18, 20}, "连队"),
    PAIR(new int[]{2}, "对子"),
    SINGLE(new int[]{1}, "单牌"),
    ;

    /**
     * 牌型可能的长度
     */
    private int[] lengths;

    /**
     * 描述
     */
    private String desc;

    private static Map<Integer, List<CardType>> cardTypeLengthMap = new HashMap<>();

    static {
        for (CardType value : values()) {
            for (int length : value.lengths) {
                List<CardType> cardTypes = cardTypeLengthMap.get(length);
                if (cardTypes == null) {
                    cardTypes = new ArrayList<>();
                    cardTypeLengthMap.put(length, cardTypes);
                }
                cardTypes.add(value);
            }
        }
    }

    /**
     * 根据长度获取牌型类型
     *
     * @param length
     * @return
     */
    public static List<CardType> getCardTypes(int length) {
        if (!cardTypeLengthMap.containsKey(length)) {
            return Collections.emptyList();
        }
        return cardTypeLengthMap.get(length);
    }

}
