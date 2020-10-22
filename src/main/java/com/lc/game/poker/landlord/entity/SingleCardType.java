package com.lc.game.poker.landlord.entity;

import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.factory.CardTypeParserFactory;
import com.lc.game.poker.landlord.utils.Assert;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 单个牌型组合牌
 *
 * @author HerdingWolf
 * @date 2020/10/21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
public class SingleCardType extends CombinationCard implements Comparable<SingleCardType> {

    // 牌型
    private CardType cardType;

    public SingleCardType(List<Poker> pokerList, CardType cardType) {
        super(pokerList);
        this.cardType = cardType;
    }

    /**
     * 获取牌型
     */
    public CardType getCardType() {
        if (cardType == null) {
            List<CardType> cardTypes = CardType.getCardTypes(getPokerList().size());
            for (CardType cardType : cardTypes) {
                if (hasCardType(cardType)) {
                    return this.cardType = cardType;
                }
            }
        }
        Assert.nonNull(cardType, "牌型不能为空：" + getPokerList());
        return cardType;
    }

    @Override
    public SingleCardType setPokerList(List<Poker> pokerList) {
        super.setPokerList(pokerList);
        return this;
    }

    /**
     * 比较牌型大小
     */
    @Override
    public int compareTo(SingleCardType other) {
        if (!getCardType().equals(other.getCardType())) {
            Assert.isTure(CardType.BOMB.equals(getCardType()) || CardType.BOMB.equals(other.getCardType()), "不同牌型不可比较");
            return CardType.BOMB.equals(getCardType()) ? 1 : -1;
        }
        return CardTypeParserFactory.getCardTypeParser(getCardType()).compare(this, other);
    }
}
