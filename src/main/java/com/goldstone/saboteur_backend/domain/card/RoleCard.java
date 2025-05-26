package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.GameRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleCard extends Card {
    private GameRole role;

    public RoleCard(String id, String name, GameRole role) {
        super(id, name, Type.ROLE);
        this.role = role;
    }

    @Override
    public RoleCard copy() {
        return (RoleCard) super.clone();
    }
}
