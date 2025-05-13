package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.ActionCardType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class CardDeck {
    private List<Card> cards;
    private int currentIndex;

    public CardDeck() {
        cards = new ArrayList<>();
        initializeCards();
        shuffle();
        currentIndex = 0;
    }

    private void initializeCards() {
        // 경로 카드 추가
        addPathCards();

        // 액션 카드 추가
        addActionCards();
    }

    private void addPathCards() {
        // 원본 프로젝트의 카드 ID 형식으로 수정
        // 직선 경로
        for (int i = 0; i < 4; i++) {
            cards.add(new PathCard("path_straight_" + i, "Straight Path",
                    new PathCard.Side[]{PathCard.Side.PATH, PathCard.Side.ROCK, PathCard.Side.PATH, PathCard.Side.ROCK}, false));
        }

        // 교차로 경로
        for (int i = 0; i < 5; i++) {
            cards.add(new PathCard("path_cross_" + i, "Cross Path",
                    new PathCard.Side[]{PathCard.Side.PATH, PathCard.Side.PATH, PathCard.Side.PATH, PathCard.Side.PATH}, false));
        }

        // T자 경로
        for (int i = 0; i < 5; i++) {
            cards.add(new PathCard("path_t_" + i, "T Path",
                    new PathCard.Side[]{PathCard.Side.PATH, PathCard.Side.PATH, PathCard.Side.PATH, PathCard.Side.ROCK}, false));
        }

        // 코너 경로
        for (int i = 0; i < 4; i++) {
            cards.add(new PathCard("path_corner_" + i, "Corner Path",
                    new PathCard.Side[]{PathCard.Side.PATH, PathCard.Side.PATH, PathCard.Side.ROCK, PathCard.Side.ROCK}, false));
        }

        // 막다른 길
        for (int i = 0; i < 3; i++) {
            cards.add(new PathCard("path_deadend_" + i, "Dead End",
                    new PathCard.Side[]{PathCard.Side.DEADEND, PathCard.Side.DEADEND, PathCard.Side.DEADEND, PathCard.Side.DEADEND}, true));
        }
    }

    private void addActionCards() {
        // 원본 프로젝트의 카드 ID 형식으로 수정
        // 도구 파괴 카드
        for (int i = 0; i < 3; i++) {
            cards.add(new ActionCard("action_break_pick_" + i, "Break Pick", ActionCardType.BREAK_PICK));
        }

        for (int i = 0; i < 3; i++) {
            cards.add(new ActionCard("action_break_lamp_" + i, "Break Lamp", ActionCardType.BREAK_LAMP));
        }

        for (int i = 0; i < 3; i++) {
            cards.add(new ActionCard("action_break_cart_" + i, "Break Cart", ActionCardType.BREAK_CART));
        }

        // 도구 수리 카드
        for (int i = 0; i < 2; i++) {
            cards.add(new ActionCard("action_fix_pick_" + i, "Fix Pick", ActionCardType.FIX_PICK));
        }

        for (int i = 0; i < 2; i++) {
            cards.add(new ActionCard("action_fix_lamp_" + i, "Fix Lamp", ActionCardType.FIX_LAMP));
        }

        for (int i = 0; i < 2; i++) {
            cards.add(new ActionCard("action_fix_cart_" + i, "Fix Cart", ActionCardType.FIX_CART));
        }

        // 다용도 수리 카드
        for (int i = 0; i < 2; i++) {
            cards.add(new ActionCard("action_fix_pick_lamp_" + i, "Fix Pick/Lamp", ActionCardType.FIX_PICK_LAMP));
        }

        for (int i = 0; i < 2; i++) {
            cards.add(new ActionCard("action_fix_pick_cart_" + i, "Fix Pick/Cart", ActionCardType.FIX_PICK_CART));
        }

        for (int i = 0; i < 2; i++) {
            cards.add(new ActionCard("action_fix_lamp_cart_" + i, "Fix Lamp/Cart", ActionCardType.FIX_LAMP_CART));
        }

        // 맵 보기 카드
        for (int i = 0; i < 6; i++) {
            cards.add(new ActionCard("action_map_" + i, "Map", ActionCardType.MAP));
        }

        // 무너뜨리기 카드
        for (int i = 0; i < 3; i++) {
            cards.add(new ActionCard("action_collapse_" + i, "Collapse", ActionCardType.COLLAPSE));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    public Card drawCard() {
        if (currentIndex >= cards.size()) {
            return null; // 카드가 없음
        }
        return cards.get(currentIndex++);
    }

    public boolean isEmpty() {
        return currentIndex >= cards.size();
    }
}
