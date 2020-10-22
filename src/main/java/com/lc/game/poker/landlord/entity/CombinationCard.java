package com.lc.game.poker.landlord.entity;

import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.factory.CardTypeParserFactory;
import com.lc.game.poker.landlord.utils.CommonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 组合牌，扑克牌合集
 *
 * @author HerdingWolf
 * @date 2020/10/20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
public class CombinationCard {

    private List<Poker> pokerList;

    private Map<PokerNumer, List<Poker>> pokerMap;

    private Map<PokerNumer, Long> pokerNumberMap;

    public CombinationCard(List<Poker> pokerList) {
        this.pokerList = pokerList;
    }

    /**
     * 组合牌是否包含指定牌型
     */
    public boolean hasCardType(CardType cardType) {
        return CardTypeParserFactory.getCardTypeParser(cardType).hasCardType(this);
    }

    public Map<PokerNumer, List<Poker>> getPokerMap() {
        if (CommonUtil.isEmpty(pokerList, pokerMap)) {
            pokerMap = pokerList.stream().collect(Collectors.groupingBy(Poker::getPokerNumer));
        }
        return pokerMap;
    }

    public Map<PokerNumer, Long> getPokerNumberMap() {
        if (CommonUtil.isEmpty(pokerList, pokerNumberMap)) {
            pokerNumberMap = pokerList.stream().collect(Collectors.groupingBy(Poker::getPokerNumer, Collectors.counting()));
        }
        return pokerNumberMap;
    }
}
