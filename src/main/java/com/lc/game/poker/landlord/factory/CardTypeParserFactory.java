package com.lc.game.poker.landlord.factory;

import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.service.CardTypeParser;
import com.lc.game.poker.landlord.service.impl.*;
import com.lc.game.poker.landlord.utils.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 牌型解析器工厂
 *
 * @author HerdingWolf
 * @date 2020/10/21
 */
public class CardTypeParserFactory {

    private static final Map<CardType, CardTypeParser> cardTypeParserMap;

    /**
     * 初始化加载所有牌型解析器
     */
    static {
        List<CardTypeParser> cardTypeParserList = Lists.newArrayList(
                new BombCardTypeParser(),
                new FourWithCardTypeParser(),
                new PairCardTypeParser(),
                new PlaneWithCardTypeParser(),
                new SerialPairCardTypeParser(),
                new SingleCardTypeParser(),
                new StrainghtCardTypeParser(),
                new ThreeWithCardTypeParser()
        );
        cardTypeParserMap = cardTypeParserList.stream().collect(Collectors.toMap(CardTypeParser::getCardType, Function.identity()));
    }

    /**
     * 根据牌型找到对应牌型解析器
     */
    public static CardTypeParser getCardTypeParser(CardType cardType) {
        return cardTypeParserMap.get(cardType);
    }
}
