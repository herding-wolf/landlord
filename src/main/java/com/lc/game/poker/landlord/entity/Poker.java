package com.lc.game.poker.landlord.entity;

import com.lc.game.poker.landlord.enums.PokerNumer;
import com.lc.game.poker.landlord.enums.PokerType;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 扑克牌
 *
 * @author HerdingWolf
 * @date 2020/10/20
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Poker implements Comparable<Poker> {

    public Poker(PokerNumer pokerNumer) {
        this.pokerNumer = pokerNumer;
    }

    /**
     * 数字
     */
    private PokerNumer pokerNumer;

    /**
     * 花色
     */
    private PokerType pokerType;

    @Override
    public int compareTo(Poker o) {
        return pokerNumer.compareTo(o.getPokerNumer());
    }
}
