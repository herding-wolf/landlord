package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.Poker;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.service.CardTypeParser;
import com.lc.game.poker.landlord.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 炸弹牌型解析
 *
 * @author HerdingWolf
 * @date 2020/10/20
 */
public class BombCardTypeParser implements CardTypeParser {

    @Override
    public CardType getCardType() {
        return CardType.BOMB;
    }

    @Override
    public boolean hasCardType(CombinationCard combinationCard, PokerNumer pokerNumer) {
        Map<PokerNumer, Long> pokerNumberMap = combinationCard.getPokerNumberMap();
        if (Objects.nonNull(pokerNumer)) {
            if (PokerNumer.BLACK_JOKER.compareTo(pokerNumer) <= 0) {
                return hashJokerBomb(combinationCard);
            }
            return pokerNumberMap.containsKey(pokerNumer) && pokerNumberMap.get(pokerNumer) >= 4;
        }

        if (hashJokerBomb(combinationCard)) {
            return true;
        }

        return pokerNumberMap.values().stream().filter(t -> Objects.equals(4L, t)).count() > 0;
    }

    @Override
    public List<SingleCardType> getSingleCardTypes(CombinationCard combinationCard) {
        List<SingleCardType> result = new ArrayList<>();
        if (hashJokerBomb(combinationCard)) {
            List<Poker> pokers = Lists.newArrayList(new Poker(PokerNumer.RED_JOKER), new Poker(PokerNumer.BLACK_JOKER));
            result.add(new SingleCardType().setPokerList(pokers));
        }

        Map<PokerNumer, List<Poker>> pokerMap = combinationCard.getPokerMap();
        List<SingleCardType> list = combinationCard.getPokerNumberMap().entrySet().stream()
                .filter(entry -> Objects.equals(4L, entry.getValue()))
                .map(entry -> new SingleCardType(pokerMap.get(entry.getKey()), getCardType()))
                .collect(Collectors.toList());
        result.addAll(list);
        return result;
    }

    /**
     * 是否有王炸
     */
    private boolean hashJokerBomb(CombinationCard combinationCard) {
        Map<PokerNumer, Long> pokerNumberMap = combinationCard.getPokerNumberMap();
        if (pokerNumberMap.containsKey(PokerNumer.RED_JOKER) &&
                pokerNumberMap.containsKey(PokerNumer.BLACK_JOKER)) {
            return true;
        }
        return false;
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        return o1.getPokerList().get(0).compareTo(o2.getPokerList().get(0));
    }
}
