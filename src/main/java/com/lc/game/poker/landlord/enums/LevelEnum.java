package com.lc.game.poker.landlord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存在可能性级别
 *
 * @author HerdingWolf
 * @date 2020/12/19
 */
@Getter
@AllArgsConstructor
public enum LevelEnum {

    L_0(0, "肯定存在"),

    L_1(1, "绝大可能存在"),

    L_3(3, "很可能存在"),

    L_5(5, "有可能存在，有可能不存在"),

    L_7(7, "很可能不存在"),

    L_9(9, "绝大可能不存在"),

    L_10(10, "肯定不存在"),
    ;

    /**
     * 级别
     */
    private int level;

    private String desc;
}
