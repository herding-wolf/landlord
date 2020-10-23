package com.lc.game.poker.landlord.utils;

import com.lc.game.poker.landlord.entity.CombinationCard;
import com.lc.game.poker.landlord.entity.Poker;
import com.lc.game.poker.landlord.entity.SingleCardType;
import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PokerNumer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HerdingWolf
 * @date 2020/10/20
 */
public class CardTypeUtil {

    /**
     * 获取连续的牌，如顺子，连队，飞机
     *
     * @param combinationCard
     * @param cardType
     * @return
     */
    public static List<SingleCardType> getSerialCards(CombinationCard combinationCard, CardType cardType) {
        // 连续张数
        int serialNum;
        // 重复张数
        int repeatNum;
        switch (cardType) {
            case STRAIGHT:
                serialNum = 5;
                repeatNum = 1;
                break;
            case SERIAL_PAIR:
                serialNum = 3;
                repeatNum = 2;
                break;
            case PLANE_WITH:
                serialNum = 2;
                repeatNum = 3;
                break;
            default:
                throw new RuntimeException("牌型错误");
        }

        if (combinationCard.getPokerList().size() < serialNum) {
            return Collections.emptyList();
        }
        // 获取最长的连续牌
        List<PokerNumer> pokerNumerList = new ArrayList<>();
        List<List<PokerNumer>> pokerNumerLists = new ArrayList<>();
        Map<PokerNumer, Long> pokerNumberMap = combinationCard.getPokerNumberMap();
        Arrays.stream(PokerNumer.values())
                .filter(t -> t.compareTo(PokerNumer.ACE) <= 0)
                .forEach(t -> {
                    Long num = pokerNumberMap.get(t);
                    if (num < repeatNum) {
                        if (pokerNumerList.size() > serialNum) {
                            pokerNumerLists.add(pokerNumerList);
                        }
                        pokerNumerList.clear();
                    } else {
                        pokerNumerList.add(t);
                    }
                });
        pokerNumerList.clear();

        // 判断可否分为多个连续牌，如长顺拆分两条顺子
        pokerNumerLists.forEach(list -> list.forEach(t -> {
            Long num = pokerNumberMap.get(t);
            if (num < repeatNum * 2) {
                if (pokerNumerList.size() > serialNum) {
                    pokerNumerLists.add(pokerNumerList);
                }
                PokerNumer minCom = t;
                PokerNumer maxCom = t;
                if (CommonUtil.isNotEmpty(pokerNumerList)) {
                    minCom = pokerNumerList.get(0);
                    maxCom = pokerNumerList.get(pokerNumerList.size() - 1);
                }
                PokerNumer firstNumber = list.get(list.size() - 1);
                PokerNumer endNumber = list.get(0);
                if (firstNumber.compareTo(minCom) > serialNum && maxCom.compareTo(endNumber) > serialNum) {
                    pokerNumerLists.add(list.subList(0, maxCom.compareTo(endNumber)));
                    pokerNumerLists.add(list.subList(firstNumber.compareTo(minCom), list.size() - 1));
                }
                pokerNumerList.clear();
            } else {
                pokerNumerList.add(t);
            }
        }));

        return pokerNumerLists.stream().map(t -> {
            List<Poker> pokers = t.stream().map(Poker::new).collect(Collectors.toList());
            return new SingleCardType(pokers, cardType);
        }).collect(Collectors.toList());
    }
}
