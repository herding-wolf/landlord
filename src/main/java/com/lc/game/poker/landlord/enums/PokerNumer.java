package com.lc.game.poker.landlord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扑克牌数字枚举
 *
 * @author HerdingWolf
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum PokerNumer {

    ACE(12, "A"),
    TWO(13, "2"),
    THREE(1, "3"),
    FOUR(2, "4"),
    FIVE(3, "5"),
    SIX(4, "6"),
    SEVEN(5, "7"),
    EIGHT(6, "8"),
    NINE(7, "9"),
    TEN(8, "10"),
    Jack(9, "J"),
    Queen(10, "Q"),
    King(11, "K"),
    BLACK_JOKER(14, "小王"),
    RED_JOKER(15, "大王"),
    ;

    /**
     * 大小顺序
     */
    private int sizeOrder;

    /**
     * 对应的牌
     */
    private String desc;

}
