package com.goldstone.saboteur_backend.domain.board;

import com.goldstone.saboteur_backend.domain.card.GoalCard;
import com.goldstone.saboteur_backend.domain.card.PathCard;
import com.goldstone.saboteur_backend.domain.card.StartCard;

public class Board {
    public static final int DEFAULT_WIDTH = 9;
    public static final int DEFAULT_HEIGHT = 5;

    private int width;
    private int height;
    private Cell[][] cells;
    private Goals goals;

    public Board() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.cells = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }

        // 시작 카드 배치
        int startX = 0;
        int startY = height / 2;
        cells[startY][startX].setCard(new StartCard());

        // 골 카드 배치
        this.goals = new Goals();
        this.goals.assignToBoard(cells);
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean placeCard(int x, int y, PathCard card) {
        if (isValidPosition(x, y) && cells[y][x].canPlacePathCard(card)) {
            cells[y][x].setCard(card);
            return true;
        }
        return false;
    }

    public Cell getCell(int x, int y) {
        if (!isValidPosition(x, y)) return null;
        return cells[y][x];
    }

    public Goals getGoals() {
        return goals;
    }

}
