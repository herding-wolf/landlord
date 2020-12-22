package com.lc.game.poker.landlord.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 游戏玩家类型
 *
 * @author HerdingWolf
 * @date 2020/11/8
 */
@Getter
@AllArgsConstructor
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
    private int num;
}
