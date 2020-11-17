package com.lc.game.poker.landlord.service.impl;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.factory.CardTypeParserFactory;
import com.lc.game.poker.landlord.service.CardTypeParser;
import com.lc.game.poker.landlord.utils.CommonUtil;

import java.util.List;

/**
 * 四带牌型解析
 *
 * @author HerdingWolf
 * @date 2020/11/17
 */
public class FourWithCardTypeParser extends BaseCardTypeParser implements CardTypeParser {

    @Override
    public CardType getCardType() {
        return CardType.FOUR_WITH;
    }

    @Override
    public boolean hasCardType(CombinationCard combinationCard, PokerNumer pokerNumer) {
        List<SingleCardType> singleCardTypes = CardTypeParserFactory.getCardTypeParser(CardType.BOMB).getSingleCardTypes(combinationCard);
        if (CommonUtil.isNotEmpty(singleCardTypes) && singleCardTypes.get(0).getPokerList().get(0).equals(pokerNumer)) {
            // TODO
        }
        return false;
    }

    @Override
    public List<SingleCardType> getSingleCardTypes(CombinationCard combinationCard) {
        return null;
    }

    @Override
    public int compare(SingleCardType o1, SingleCardType o2) {
        return getMainMaxPokerNumber(o1, 4L).compareTo(getMainMaxPokerNumber(o2, 4L));
    }
}
