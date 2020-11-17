package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.service.CardTypeParser;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * @author HerdingWolf
 * @date 2020/11/17
 */
public abstract class BaseCardTypeParser implements CardTypeParser {

    /**
     * 获取牌型主体最大牌
     */
    public PokerNumer getMainMaxPokerNumber(SingleCardType singleCardType, long mainLength) {
        return singleCardType.getPokerNumberMap().entrySet().stream()
                .filter(entry -> Objects.equals(mainLength, entry.getValue()))
                .map(Map.Entry::getKey)
                .max(Comparator.comparing(PokerNumer::ordinal))
                .get();
    }
}
