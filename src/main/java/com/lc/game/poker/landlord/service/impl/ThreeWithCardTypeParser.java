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
public class ThreeWithCardTypeParser extends BaseCardTypeParser implements CardTypeParser {

    @Override
    public CardType getCardType() {
        return CardType.THREE_WITH;
    }

    @Override
    public boolean hasCardType(CombinationCard combinationCard, PokerNumer pokerNumer) {
        Map<PokerNumer, Long> pokerNumberMap = combinationCard.getPokerNumberMap();
        if (Objects.nonNull(pokerNumer)) {
            return pokerNumberMap.containsKey(pokerNumer) && pokerNumberMap.get(pokerNumer) >= 3;
        }
        return pokerNumberMap.values().stream().filter(t -> Objects.equals(3L, t)).count() > 0;
    }

    @Override
    public List<SingleCardType> getSingleCardTypes(CombinationCard combinationCard) {
        Map<PokerNumer, List<Poker>> pokerMap = combinationCard.getPokerMap();
        return combinationCard.getPokerNumberMap().entrySet().stream()
                .filter(entry -> Objects.equals(3L, entry.getValue()))
                .map(entry -> new SingleCardType(pokerMap.get(entry.getKey()), getCardType()))
                .collect(Collectors.toList());
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        return getMainMaxPokerNumber(o1, 3L).compareTo(getMainMaxPokerNumber(o2, 3L));
    }
}
