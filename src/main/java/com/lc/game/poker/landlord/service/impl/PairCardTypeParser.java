package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.utils.Lists;
import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.Poker;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.service.CardTypeParser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 对子牌型
 *
 * @author HerdingWolf
 * @date 2020/10/21
 */
public class PairCardTypeParser implements CardTypeParser {

    @Override
    public CardType getCardType() {
        return CardType.PAIR;
    }

    @Override
    public boolean hasCardType(CombinationCard combinationCard, PokerNumer pokerNumer) {
        Map<PokerNumer, Long> pokerNumberMap = combinationCard.getPokerNumberMap();
        if (Objects.nonNull(pokerNumer)) {
            return pokerNumberMap.containsKey(pokerNumer) && pokerNumberMap.get(pokerNumer) >= 2;
        }

        Set<Map.Entry<PokerNumer, Long>> entrySet = pokerNumberMap.entrySet();
        for (Map.Entry<PokerNumer, Long> entry : entrySet) {
            if (Objects.equals(2L, entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SingleCardType> getSingleCardTypes(CombinationCard combinationCard) {
        Map<PokerNumer, List<Poker>> pokerMap = combinationCard.getPokerMap();
        return combinationCard.getPokerNumberMap().entrySet().stream()
                .filter(entry -> entry.getValue() > 2)
                .map(entry -> {
                    List<Poker> pokerList = pokerMap.get(entry.getKey()).subList(0, 1);
                    return new SingleCardType(pokerList, getCardType());
                })
                .collect(Collectors.toList());
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        return o1.getPokerList().get(0).compareTo(o2.getPokerList().get(0));
    }
}
