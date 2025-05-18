package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.GameRole;

public class RoleCard extends Card {
    private GameRole role;

    public RoleCard(String id, String name, GameRole role) {
        super(id, name, Card.CardType.ROLE);  // Card.CardType으로 수정
        this.role = role;
    }

    // @Override 어노테이션 제거
    public GameRole getRole() {
        return role;
    }
}
