package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.GoalCardType;
import com.goldstone.saboteur_backend.domain.enums.PathCardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoalCard extends PathCard {
    private GoalCardType type;

    public GoalCard(GoalCardType type, PathCardType pathCardType) {
        super(pathCardType, false);
        this.type = type;
    }

    @Override
    public void use() {}

    @Override
    public boolean availableUse() {
        return false;
    }
}
