package com.lc.game.poker.landlord;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.Poker;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.utils.Lists;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<Poker> pokers = Lists.newArrayList(new Poker(PokerNumer.SIX), new Poker(PokerNumer.SEVEN), new Poker(PokerNumer.FIVE));
        CombinationCard combinationCard = new CombinationCard(pokers);
        for (PokerNumer value : PokerNumer.values()) {
            System.out.println(value);
        }
        System.out.println(combinationCard.getPokerNumberMap());
    }


    public static void test1() {
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
