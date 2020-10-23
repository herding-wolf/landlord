package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.Poker;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.service.CardTypeParser;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 三带牌型解析器
 *
 * @author HerdingWolf
 * @date 2020/10/23
 */
public class ThreeWithCardTypeParser implements CardTypeParser {

    @Override
    public CardType getCardType() {
        return CardType.THREE_WITH;
    }

    @Override
    public boolean hasCardType(CombinationCard combinationCard) {
        return combinationCard.getPokerNumberMap().values().stream().filter(t -> Objects.equals(3L, t)).count() > 0;
    }

    @Override
    public List<SingleCardType> getSingleCardType(CombinationCard combinationCard) {
        Map<PokerNumer, List<Poker>> pokerMap = combinationCard.getPokerMap();
        return combinationCard.getPokerNumberMap().entrySet().stream()
                .filter(entry -> Objects.equals(3L, entry.getValue()))
                .map(entry -> new SingleCardType(pokerMap.get(entry.getKey()), getCardType()))
                .collect(Collectors.toList());
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        return getMaxPokerNumber(o1).compareTo(getMaxPokerNumber(o2));
    }

    private PokerNumer getMaxPokerNumber(SingleCardType singleCardType) {
        return singleCardType.getPokerNumberMap().entrySet().stream()
                .filter(entry -> Objects.equals(3L, entry.getValue()))
                .map(Map.Entry::getKey)
                .max(Comparator.comparing(PokerNumer::ordinal))
                .get();
    }
}
