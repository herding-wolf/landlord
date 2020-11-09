package com.lc.game.poker.landlord.entity;

import com.lc.game.poker.landlord.enums.CardType;
import com.lc.game.poker.landlord.enums.PlayerType;
import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.utils.CardTypeUtil;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<List<PokerNumer>> straight = CardTypeUtil.getMaxSerialCards(combinationCard, CardType.STRAIGHT);
        List<List<PokerNumer>> serialPair = CardTypeUtil.getMaxSerialCards(combinationCard, CardType.SERIAL_PAIR);
        List<List<PokerNumer>> planeWith = CardTypeUtil.getMaxSerialCards(combinationCard, CardType.PLANE_WITH);

        straight.stream().flatMap(List::stream).forEach(t -> result.get(t).put(CardType.STRAIGHT, true));
        serialPair.stream().flatMap(List::stream).forEach(t -> result.get(t).put(CardType.SERIAL_PAIR, true));
        planeWith.stream().flatMap(List::stream).forEach(t -> result.get(t).put(CardType.PLANE_WITH, true));
        return result;
    }

    /**
     * 推算牌
     */
    public void calculation(Record record) {
        recordList.add(record);
        if (this.master == record.getPlayerType()) {
            return;
        }

    }

    public Player getMasterPlayer() {
        switch (master) {
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
