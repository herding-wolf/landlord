package com.lc.game.poker.landlord.entity;

import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PlayerType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Map;

/**
 * 游戏玩家
 *
 * @author HerdingWolf
 * @date 2020/11/8
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@EqualsAndHashCode
public class Player {

    /**
     * 游戏玩家类型
     */
    private PlayerType playerType;

    /**
     * 未出牌
     */
    private CombinationCard notPlayCard;

    /**
     * 剩下的牌数
     */
    private Integer remain;

    /**
     * 玩家所有牌型
     */
    private Map<PokerNumer, Map<CardType, Boolean>> allCardType;

    public Player() {
        this.notPlayCard = new CombinationCard(new ArrayList<>());
    }

    public Player(PlayerType playerType) {
        this();
        this.playerType = playerType;
        this.remain = playerType.getNum();
    }

    public void removePokerNumerCardType(PokerNumer pokerNumer, CardType... cardType) {
        // TODO 删除指定扑克牌牌型
    }
}
