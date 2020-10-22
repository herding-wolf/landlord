package com.lc.test;

import com.lc.game.poker.landlord.entity.Poker;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;

import java.util.ArrayList;
import java.util.List;

/**
 * 牌型解析测试
 * @author HerdingWolf
 * @date 2020/10/21
 */
public class CardTypeParseTest {

//    @Test
    public void test() {
        // 识别牌型
        // 王炸
        List<Poker> pokers = new ArrayList<>();
        pokers.add(new Poker().setPokerNumer(PokerNumer.RED_JOKER));
        pokers.add(new Poker().setPokerNumer(PokerNumer.BLACK_JOKER));
        CardType cardType = new SingleCardType().setPokerList(pokers).getCardType();
        System.out.println(cardType);

        // 炸弹
        List<Poker> pokers1 = new ArrayList<>();
        pokers1.add(new Poker().setPokerNumer(PokerNumer.ACE));
        pokers1.add(new Poker().setPokerNumer(PokerNumer.ACE));
        pokers1.add(new Poker().setPokerNumer(PokerNumer.ACE));
        pokers1.add(new Poker().setPokerNumer(PokerNumer.ACE));
        CardType cardType1 = new SingleCardType().setPokerList(pokers1).getCardType();
        System.out.println(cardType1);
    }
}
