package com.lc.game.poker.landlord.entity;

import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.LevelEnum;
import com.lc.game.poker.landlord.enums.PlayerType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.factory.CardTypeParserFactory;
import com.lc.game.poker.landlord.utils.Assert;
import com.lc.game.poker.landlord.utils.CardTypeUtil;
import com.lc.game.poker.landlord.utils.Lists;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * 经典斗地主
 *
 * @author HerdingWolf
 * @date 2020/11/8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClassicGame {

    /**
     * 主人
     */
    private PlayerType master;

    /**
     * 上家
     */
    private Player lastPlayer;

    /**
     * 下家
     */
    private Player nextPlayer;

    /**
     * 地主
     */
    private Player landlord;

    /**
     * 底牌
     */
    private CombinationCard lastCard;

    /**
     * 出牌记录
     */
    private List<Record> recordList;

    public ClassicGame(PlayerType master, CombinationCard masterCard, CombinationCard lastCard) {
        this.master = master;
        this.lastCard = lastCard;
        this.lastPlayer = new Player(PlayerType.LAST);
        this.nextPlayer = new Player(PlayerType.NEXT);
        this.landlord = new Player(PlayerType.LANDLORD).setNotPlayCard(lastCard);
        getMasterPlayer().setNotPlayCard(masterCard);
    }

    /**
     * 根据组合牌返回所有牌型
     */
    public Map<PokerNumer, Map<CardType, Boolean>> getAllCardType(CombinationCard combinationCard) {
        Map<PokerNumer, Map<CardType, Boolean>> result = new HashMap<>();
        Arrays.stream(PokerNumer.values()).forEach(pokerNumer -> {
            Map<CardType, Boolean> map = new HashMap<>();
            result.put(pokerNumer, map);
            Arrays.stream(CardType.values()).forEach(cardType -> map.put(cardType, false));
        });

        // 飞机、连队、顺子一起解析牌型
        List<List<PokerNumer>> straight = CardTypeUtil.getMaxSerialCards(combinationCard, CardType.STRAIGHT);
        List<List<PokerNumer>> serialPair = CardTypeUtil.getMaxSerialCards(combinationCard, CardType.SERIAL_PAIR);
        List<List<PokerNumer>> planeWith = CardTypeUtil.getMaxSerialCards(combinationCard, CardType.PLANE_WITH);

        straight.stream().flatMap(List::stream).forEach(t -> result.get(t).put(CardType.STRAIGHT, true));
        serialPair.stream().flatMap(List::stream).forEach(t -> result.get(t).put(CardType.SERIAL_PAIR, true));
        planeWith.stream().flatMap(List::stream).forEach(t -> result.get(t).put(CardType.PLANE_WITH, true));

        // 循环解析炸弹、四带、三带、对子、单牌牌型
        Arrays.stream(PokerNumer.values()).forEach(pokerNumer -> {
            List<CardType> cardTypes = Lists.newArrayList(CardType.BOMB, CardType.FOUR_WITH, CardType.THREE_WITH, CardType.PAIR, CardType.SINGLE);
            cardTypes.forEach(cardType -> {
                boolean flag = CardTypeParserFactory.getCardTypeParser(cardType).hasCardType(combinationCard, pokerNumer);
                result.get(pokerNumer).put(cardType, flag);
            });
        });
        return result;
    }

    public void setPokerCardTypeLevel(PokerNumer pokerNumer, CardType... cardType) {
        // TODO 删除指定扑克牌牌型
    }

    public void setPokerCardTypeLevel(PokerNumer pokerNumer, LevelEnum level, CardType... cardType) {
        // TODO 删除指定扑克牌牌型
    }

    public Map<PokerNumer, Long> getRecordPokerNumberMap() {
        // TODO 获取历史出牌扑克数map
        return null;
    }

    /**
     * 推算牌
     */
    public void calculation(Record record) {
        recordList.add(record);
        // 自己出牌不用推算
        if (this.master == record.getPlayerType()) {
            return;
        }

        // 先按照出牌数量去掉不可能出现的牌型
        Player player = getPlayer(record.getPlayerType());
        SingleCardType singleCardType = record.getSingleCardType();
        Map<PokerNumer, Long> pokerNumberMap = singleCardType.getPokerNumberMap();
        Map<PokerNumer, Long> masterProkerNumberMap = getMasterPlayer().getNotPlayCard().getPokerNumberMap();
        pokerNumberMap.forEach((pokerNumer, number) -> {
            Long masterNumber = Optional.ofNullable(masterProkerNumberMap.get(pokerNumer)).orElse(0L);
            Long recordNumber = Optional.ofNullable(getRecordPokerNumberMap().get(pokerNumberMap)).orElse(0L);
            Long sumNumber = number + masterNumber + recordNumber;

            Assert.isTure(sumNumber < 4, String.format("扑克牌：%s 所剩大于%s张", pokerNumer.getDesc(), 4));
            // 肯定没有炸弹和四带
            setPokerCardTypeLevel(pokerNumer, LevelEnum.L_10, CardType.BOMB, CardType.FOUR_WITH);
            if (PokerNumer.BLACK_JOKER.compareTo(pokerNumer) <= 0) {

            }
            if (sumNumber == 3) {
                // 其他玩家出了一张单牌或者带了一个单牌，或顺子
                // 这个玩家这张牌没有飞机、三带
                if (CardType.STRAIGHT.equals(record.getSingleCardType())) {
                    // TODO 先认为顺子出过的牌不再包含飞机和三带
                    player.setPokerCardTypeLevel(pokerNumer, CardType.PLANE_WITH, CardType.THREE_WITH);
                } else {
                    player.setPokerCardTypeLevel(pokerNumer, CardType.PLANE_WITH, CardType.THREE_WITH);
                    // TODO 先粗略推算这个玩家这张牌没有对子和单牌
                    player.setPokerCardTypeLevel(pokerNumer, LevelEnum.L_7, CardType.PAIR, CardType.SINGLE);
//                    if (record.getIsFirst()) {
//                        // 如果是地主主动出牌，则认为他没有对子和单牌
//                        if (PlayerType.LANDLORD.equals(record.getPlayerType())) {
//                            player.removePokerNumerCardType(pokerNumer, CardType.PAIR, CardType.SINGLE);
//                        }
//
//                        // 农名上家主动出牌
//                        if (PlayerType.LAST.equals(record.getPlayerType())) {
//                            player.removePokerNumerCardType(pokerNumer, CardType.PAIR);
//                        }
//                    }
                }
            }

            else if (sumNumber == 2) {
                setPokerCardTypeLevel(pokerNumer, LevelEnum.L_10, CardType.PLANE_WITH, CardType.THREE_WITH);
                if (PokerNumer.FOUR.compareTo(pokerNumer) >= 0) {
                    Arrays.stream(PokerNumer.values())
                            .filter(t -> t.compareTo(pokerNumer) < 0)
                            .forEach(t -> setPokerCardTypeLevel(t, LevelEnum.L_10, CardType.PLANE_WITH));
                }
                if (PokerNumer.KING.compareTo(pokerNumer) <= 0 && PokerNumer.ACE.compareTo(pokerNumer) >= 0) {
                    Arrays.stream(PokerNumer.values())
                            .filter(t -> t.compareTo(pokerNumer) > 0)
                            .forEach(t -> setPokerCardTypeLevel(t, LevelEnum.L_10, CardType.PLANE_WITH));
                }
                if (number == 2) {
                    player.setPokerCardTypeLevel(pokerNumer, CardType.PAIR, CardType.SINGLE);
                }
            }

            else if (sumNumber == 1) {
                setPokerCardTypeLevel(pokerNumer, LevelEnum.L_10, CardType.PLANE_WITH, CardType.THREE_WITH, CardType.SERIAL_PAIR);
                if (PokerNumer.FIVE.compareTo(pokerNumer) >= 0) {
                    Arrays.stream(PokerNumer.values())
                            .filter(t -> t.compareTo(pokerNumer) < 0)
                            .forEach(t -> setPokerCardTypeLevel(t, LevelEnum.L_10, CardType.PLANE_WITH, CardType.SERIAL_PAIR));
                }
                if (PokerNumer.QUEEN.compareTo(pokerNumer) <= 0 && PokerNumer.ACE.compareTo(pokerNumer) >= 0) {
                    Arrays.stream(PokerNumer.values())
                            .filter(t -> t.compareTo(pokerNumer) > 0)
                            .forEach(t -> setPokerCardTypeLevel(t, LevelEnum.L_10, CardType.PLANE_WITH, CardType.SERIAL_PAIR));
                }
            }

            else if (sumNumber == 0) {
                setPokerCardTypeLevel(pokerNumer, LevelEnum.L_10, CardType.values());
                if (PokerNumer.SEVEN.compareTo(pokerNumer) >= 0) {
                    Arrays.stream(PokerNumer.values())
                            .filter(t -> t.compareTo(pokerNumer) < 0)
                            .forEach(t -> setPokerCardTypeLevel(t, LevelEnum.L_10, CardType.PLANE_WITH, CardType.SERIAL_PAIR, CardType.STRAIGHT));
                }
                if (PokerNumer.TEN.compareTo(pokerNumer) <= 0 && PokerNumer.ACE.compareTo(pokerNumer) >= 0) {
                    Arrays.stream(PokerNumer.values())
                            .filter(t -> t.compareTo(pokerNumer) > 0)
                            .forEach(t -> setPokerCardTypeLevel(t, LevelEnum.L_10, CardType.PLANE_WITH, CardType.SERIAL_PAIR, CardType.STRAIGHT));
                }
            }
        });


    }

    public Player getMasterPlayer() {
        return getPlayer(this.master);
    }

    public Player getPlayer(PlayerType playerType) {
        switch (playerType) {
            case LAST:
                return this.lastPlayer;
            case NEXT:
                return this.nextPlayer;
            case LANDLORD:
                return this.landlord;
            default:
                throw new RuntimeException("获取主人玩家错误");
        }
    }
}
