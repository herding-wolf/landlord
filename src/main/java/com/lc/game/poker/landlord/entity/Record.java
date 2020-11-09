package com.lc.game.poker.landlord.entity;

import com.lc.game.poker.landlord.enums.PlayerType;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 出牌记录
 *
 * @author HerdingWolf
 * @date 2020/11/8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Record {

    /**
     * 回合数
     */
    private Integer round;

    /**
     * 游戏玩家类型
     */
    private PlayerType playerType;

    /**
     * 出的组合牌
     */
    private SingleCardType singleCardType;
}
