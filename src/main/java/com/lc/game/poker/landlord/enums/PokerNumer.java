package com.lc.game.poker.landlord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扑克牌数字枚举，顺序不能变
 *
 * @author HerdingWolf
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum PokerNumer {

    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    Jack(11, "J"),
    Queen(12, "Q"),
    King(13, "K"),
    ACE(1, "A"),
    TWO(2, "2"),
    BLACK_JOKER(14, "小王"),
    RED_JOKER(15, "大王"),
    ;

    /**
     * 扑克牌对应数字
     */
    private int number;

    /**
     * 对应的牌说明
     */
    private String desc;

}
