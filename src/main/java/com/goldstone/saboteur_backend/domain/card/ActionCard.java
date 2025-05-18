package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.ActionCardType;

public class ActionCard extends Card {
    private ActionCardType actionType;

    public ActionCard(String id, String name, ActionCardType actionType) {
        super(id, name, Card.CardType.ACTION);  // Card.CardType으로 수정
        this.actionType = actionType;
    }

    // @Override 어노테이션 제거
    public ActionCardType getActionType() {
        return actionType;
    }
}
