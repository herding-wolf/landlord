package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.service.CardTypeParser;
import com.lc.game.poker.landlord.utils.Assert;
import com.lc.game.poker.landlord.utils.CardTypeUtil;

import java.util.List;
import java.util.Objects;

/**
 * 飞机牌型解析器
 *
 * @author HerdingWolf
 * @date 2020/10/24
 */
public class PlaneWithCardTypeParser implements CardTypeParser {

    @Override
    public CardType getCardType() {
        return CardType.PLANE_WITH;
    }

    @Override
    public boolean hasCardType(CombinationCard combinationCard, PokerNumer pokerNumer) {
        return !getSingleCardTypes(combinationCard).isEmpty();
    }

    @Override
    public List<SingleCardType> getSingleCardTypes(CombinationCard combinationCard) {
        return CardTypeUtil.getSerialCards(combinationCard, getCardType());
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        Assert.isTure(Objects.equals(o1.getSize(), o2.getSize()), getCardType().getDesc() + "长度不一致，不比较");
        return o1.getPokerList().get(o1.getSize() - 1).compareTo(o2.getPokerList().get(o2.getSize() - 1));
    }
}
