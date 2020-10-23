package com.lc.game.poker.landlord.service;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;

import java.util.Comparator;
import java.util.List;

/**
 * 牌型解析器
 *
 * @author HerdingWolf
 * @date 2020/10/20
 */
public interface CardTypeParser extends Comparator<SingleCardType> {

    /**
     * 获取解析器牌型
     */
    CardType getCardType();

    /**
     * 组合牌是否包含对应牌型
     */
    boolean hasCardType(CombinationCard combinationCard);

    /**
     * 获取指定牌型的所有组合牌，不区分花色的组合，如有需要自行替换
     */
    List<SingleCardType> getSingleCardType(CombinationCard combinationCard);
}
