package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.card.Card;
import com.goldstone.saboteur_backend.domain.card.PathCard;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Cell implements Cloneable {
    public enum Side {
        ROCK(-2), EMPTY(-1), DEADEND(0), PATH(1);
        private final int val;

        Side(int val) { this.val = val; }

        public int val() { return val; }
    }

    private final int x;
    private final int y;
    private Card card;
    private Side[] sides;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.sides = new Side[]{Side.EMPTY, Side.EMPTY, Side.EMPTY, Side.EMPTY};
    }

    private Cell(Cell cell) {
        this(cell.x, cell.y);
        this.sides = cell.sides.clone();
        this.card = cell.card;
    }

    public void placePathCard(PathCard card) {
        PathCard cardCopy = (PathCard) card.copy();
        List<Side> newSides;
        if (cardCopy.getType() == Card.Type.PATHWAY) {
            newSides = Arrays.stream(cardCopy.getSides())
                    .map(val -> val == PathCard.Side.PATH ? Side.PATH : Side.ROCK)
                    .collect(Collectors.toList());
        } else { // DEADEND
            newSides = Arrays.stream(cardCopy.getSides())
                    .map(val -> val == PathCard.Side.DEADEND ? Side.DEADEND : Side.ROCK)
                    .collect(Collectors.toList());
        }
        this.sides = newSides.toArray(new Side[0]);
        this.card = cardCopy;
    }

    public void openAllSides() {
        this.sides = new Side[]{Side.PATH, Side.PATH, Side.PATH, Side.PATH};
    }

    public void removeCard() {
        this.card = null;
        this.sides = new Side[]{Side.EMPTY, Side.EMPTY, Side.EMPTY, Side.EMPTY};
    }

    public boolean hasCard() {
        return this.sides[0] != Side.EMPTY
                && this.sides[1] != Side.EMPTY
                && this.sides[2] != Side.EMPTY
                && this.sides[3] != Side.EMPTY;
    }

    public Side[] getSides() { return this.sides.clone(); }
    public Side getTopSide() { return sides[0]; }
    public Side getRightSide() { return sides[1]; }
    public Side getBottomSide() { return sides[2]; }
    public Side getLeftSide() { return sides[3]; }

    public Cell copy() {
        return new Cell(this);
    }
}
