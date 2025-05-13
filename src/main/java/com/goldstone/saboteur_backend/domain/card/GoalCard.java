package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.GoalCardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalCard extends Card {
    // 필드 이름을 goalType으로 변경
    private GoalCardType goalType;
    private boolean revealed;

    public GoalCard(String id, String name, GoalCardType goalType) {
        super(id, name, Card.Type.GOAL);
        this.goalType = goalType;
        this.revealed = false;
    }

    public void reveal() {
        this.revealed = true;
    }

    @Override
    public GoalCard copy() {
        GoalCard copy = (GoalCard) super.clone();
        return copy;
    }
}
