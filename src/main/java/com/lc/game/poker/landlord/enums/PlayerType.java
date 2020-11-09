package com.lc.game.poker.landlord.enums;

import lombok.Getter;

/**
 * 游戏玩家类型
 *
 * @author HerdingWolf
 * @date 2020/11/8
 */
public enum PlayerType {

    /**
     * 上家
     */
    LAST(17),

    /**
     * 下家
     */
    NEXT(17),

    /**
     * 地主
     */
    LANDLORD(20);

    /**
     * 一共牌的张数
     */
    @Getter
    private int num;

    PlayerType(int num) {
        this.num = num;
    }
}
