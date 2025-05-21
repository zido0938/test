package com.goldstone.saboteur_backend.domain.board;

import com.goldstone.saboteur_backend.domain.card.GoalCard;
import com.goldstone.saboteur_backend.domain.card.PathCard;
import com.goldstone.saboteur_backend.domain.card.StartCard;
import com.goldstone.saboteur_backend.domain.enums.PathType;
import java.util.*;
import lombok.Getter;

@Getter
public class Board {
    public static final int WIDTH = 9;
    public static final int HEIGHT = 5;

    private final Map<Position, Cell> dynamicCells = new HashMap<>(); // 5*9 범위 밖에도 카드를 연결하기 위한 해시맵
    private final Set<Position> initArea = new HashSet<>();

    private GoalCells goalCells;
    private final Position startPos;

    public Board() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Position pos = new Position(x, y);
                initArea.add(pos);
                dynamicCells.put(pos, new Cell(pos.getX(), pos.getY()));
            }
        }

        // 시작 카드 배치
        this.startPos = new Position(0, HEIGHT / 2);
        getCell(startPos).setCard(new StartCard());

        // 골 카드 배치
        this.goalCells = new GoalCells();
        List<GoalCard> goals = goalCells.shuffleGoals();

        Position[] goalPositions = {
            new Position(WIDTH - 1, 0), new Position(WIDTH - 1, 2), new Position(WIDTH - 1, 4)
        };

        for (int i = 0; i < 3; i++) {
            getCell(goalPositions[i]).setCard(goals.get(i));
        }
    }

    public boolean isValidPosition(Position position) {
        // return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 &&
        // position.getY() < height;
        return position != null;
    }

    public boolean placeCard(Position position, PathCard card) {
        if (position == null || card == null) return false;

        Cell cell = getOrCreateCell(position);
        if (cell.canPlacePathCard(card)) {
            cell.setCard(card);

            expandBoard(card, position);

            return true;
        }

        return false;
    }

    // 배치된 길카드의 열린 방향(PATH) 부분에 셀 샐성 <- 초기 5*9 보드에서 확장
    private void expandBoard(PathCard pathCard, Position position) {
        for (int i = 0; i < 4; i++) {
            if (pathCard.getSides()[i] == PathType.PATH) {
                Position neighbor = getNeighbor(position, i);
                dynamicCells.putIfAbsent(neighbor, new Cell(neighbor.getX(), neighbor.getY()));
            }
        }
    }

    // 현재 cell의 인접 좌표 반환
    private Position getNeighbor(Position position, int direction) {
        int x = position.getX();
        int y = position.getY();

        return switch (direction) {
            case 0 -> new Position(x, y - 1); // 위
            case 1 -> new Position(x + 1, y); // 오른쪽
            case 2 -> new Position(x, y + 1); // 아래
            case 3 -> new Position(x - 1, y); // 왼쪽
            default -> position;
        };
    }

    public Cell getCell(Position position) {
        if (!isValidPosition(position)) return null;

        return dynamicCells.get(position);
    }

    public Cell getOrCreateCell(Position position) {
        return dynamicCells.computeIfAbsent(position, p -> new Cell(p.getX(), p.getY()));
    }

    public GoalCells getGoals() {
        return goalCells;
    }

    // 두 카드 간 길이 이어지는지 확인하는 메서드
    public boolean isConnected(Position fromPos, Position toPos) {
        if (!isValidPosition(fromPos) || !isValidPosition(toPos)) return false;

        Cell from = getCell(fromPos);
        Cell to = getCell(toPos);

        if (from == null || to == null) return false;

        int dx = toPos.getX() - fromPos.getX();
        int dy = toPos.getY() - fromPos.getY();

        int direction = -1;
        if (dx == 0 && dy == -1) direction = 0; // 위
        else if (dx == 1 && dy == 0) direction = 1; // 오른쪽
        else if (dx == 0 && dy == 1) direction = 2; // 아래
        else if (dx == -1 && dy == 0) direction = 3; // 왼쪽

        if (direction == -1) return false; // 인접하지 않은 셀

        return PathValidator.isConnected(from, to, direction);
    }
}
