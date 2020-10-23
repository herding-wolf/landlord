package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.service.CardTypeParser;
import com.lc.game.poker.landlord.utils.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 单牌牌型解析器
 *
 * @author HerdingWolf
 * @date 2020/10/23
 */
public class SingleCardTypeParser implements CardTypeParser {

    @Override
    public CardType getCardType() {
        return CardType.SINGLE;
    }

    @Override
    public boolean hasCardType(CombinationCard combinationCard) {
        return combinationCard.getPokerList().size() >= 1;
    }

    @Override
    public List<SingleCardType> getSingleCardType(CombinationCard combinationCard) {
        return combinationCard.getPokerList().stream()
                .map(t -> new SingleCardType(Lists.newArrayList(t), getCardType()))
                .collect(Collectors.toList());
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        return o1.getPokerList().get(0).compareTo(o2.getPokerList().get(0));
    }
}
