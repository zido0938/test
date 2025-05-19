package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.enums.GoalCardType;
import com.goldstone.saboteur_backend.domain.enums.PathCardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class GoalCard extends PathCard {
    private GoalCardType type;

    public GoalCard() {
        super(PathCardType.CROSSROAD, false);
    }

    @Override
    public void use() {}

    @Override
    public boolean availableUse() {
        return false;
    }
}
