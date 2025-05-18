package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.GoalCardType;

public class GoalCard extends Card {
    private GoalCardType goalType;

    public GoalCard(String id, String name, GoalCardType goalType) {
        super(id, name, CardType.GOAL);
        this.goalType = goalType;
    }

    public GoalCardType getGoalType() {
        return goalType;
    }
}
