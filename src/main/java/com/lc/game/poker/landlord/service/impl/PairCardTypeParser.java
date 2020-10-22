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
    public boolean hasCardType(CombinationCard combinationCard) {
        Map<PokerNumer, Long> pokerNumberMap = combinationCard.getPokerNumberMap();
        Set<Map.Entry<PokerNumer, Long>> entrySet = pokerNumberMap.entrySet();
        for (Map.Entry<PokerNumer, Long> entry : entrySet) {
            if (Objects.equals(2L, entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SingleCardType> getSingleCardType(CombinationCard combinationCard) {
        Map<PokerNumer, List<Poker>> pokerMap = combinationCard.getPokerMap();
        List<SingleCardType> result = combinationCard.getPokerNumberMap().entrySet().stream()
                .filter(entry -> entry.getValue() > 2)
                .map(entry -> {
                    List<SingleCardType> list = new ArrayList<>();
                    List<Poker> pokerList = pokerMap.get(entry.getKey());
                    for (int i = 0; i < pokerList.size() - 1; i++) {
                        for (int j = i + 1; j < pokerList.size(); j++) {
                            List<Poker> pokers = Lists.newArrayList(pokerList.get(i), pokerList.get(j));
                            list.add(new SingleCardType().setPokerList(pokers));
                        }
                    }
                    return list;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        return o1.getPokerList().get(0).getPokerNumer().getSizeOrder() - o2.getPokerList().get(0).getPokerNumer().getSizeOrder();
    }
}
