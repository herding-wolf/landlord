package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.Poker;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.enums.PokerType;
import com.lc.game.poker.landlord.service.CardTypeParser;
import com.lc.game.poker.landlord.utils.Lists;

import java.util.*;
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
    public boolean hasCardType(CombinationCard combinationCard) {
        // 是否有王炸
        hashJokerBomb(combinationCard);

        // 是否有普通炸弹
        Map<PokerNumer, Long> pokerNumberMap = combinationCard.getPokerNumberMap();
        Set<Map.Entry<PokerNumer, Long>> entrySet = pokerNumberMap.entrySet();
        for (Map.Entry<PokerNumer, Long> entry : entrySet) {
            if (Objects.equals(4L, entry.getValue())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<SingleCardType> getSingleCardType(CombinationCard combinationCard) {
        List<SingleCardType> result = new ArrayList<>();
        if (hashJokerBomb(combinationCard)) {
            List<Poker> pokers = Lists.newArrayList(new Poker(PokerNumer.RED_JOKER), new Poker(PokerNumer.BLACK_JOKER));
            result.add(new SingleCardType().setPokerList(pokers));
        }

        List<SingleCardType> list = combinationCard.getPokerNumberMap().entrySet().stream()
                .filter(entry -> Objects.equals(4L, entry.getValue()))
                .map(entry -> {
                    List<Poker> pokers = Lists.newArrayList(
                            new Poker(entry.getKey(), PokerType.SPADE),
                            new Poker(entry.getKey(), PokerType.HEART),
                            new Poker(entry.getKey(), PokerType.DIAMOND),
                            new Poker(entry.getKey(), PokerType.CLUB));
                    return new SingleCardType().setPokerList(pokers);
                })
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
        return o1.getPokerList().get(0).getPokerNumer().getSizeOrder() - o2.getPokerList().get(0).getPokerNumer().getSizeOrder();
    }
}
