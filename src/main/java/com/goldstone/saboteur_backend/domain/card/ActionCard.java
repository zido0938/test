package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.ActionCardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionCard extends Card {
    private ActionCardType actionType;

    public ActionCard(String id, String name, ActionCardType actionType) {
        super(id, name, Type.ACTION);
        this.actionType = actionType;
    }

    @Override
    public ActionCard copy() {
        return (ActionCard) super.clone();
    }
}
