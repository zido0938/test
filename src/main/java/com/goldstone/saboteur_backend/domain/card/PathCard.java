package com.goldstone.saboteur_backend.domain.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PathCard extends Card {
    public enum Side {
        ROCK, PATH, DEADEND
    }

    private Side[] sides; // [top, right, bottom, left]
    private boolean isDeadEnd;

    public PathCard(String id, String name, Side[] sides, boolean isDeadEnd) {
        super(id, name, isDeadEnd ? Type.DEADEND : Type.PATHWAY);
        this.sides = sides;
        this.isDeadEnd = isDeadEnd;
    }

    public PathCard rotate(int times) {
        times = times % 4;
        if (times == 0) return this;

        Side[] newSides = new Side[4];
        for (int i = 0; i < 4; i++) {
            newSides[(i + times) % 4] = sides[i];
        }
        sides = newSides;
        return this;
    }

    @Override
    public PathCard copy() {
        PathCard copy = (PathCard) super.clone();
        copy.sides = this.sides.clone();
        return copy;
    }
}
