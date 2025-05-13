package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.card.GoalCard;
import com.goldstone.saboteur_backend.domain.card.PathCard;
import com.goldstone.saboteur_backend.domain.enums.GoalCardType;
import lombok.Getter;

import java.util.*;

@Getter
public class Board implements Cloneable {
    private static final int WIDTH = 9;
    private static final int HEIGHT = 5;

    private Cell[][] cells;
    private Position startPosition;
    private List<Position> goalPositions;
    private Map<Position, GoalCard> goalCards;
    private boolean[] revealedGoals;

    public Board() {
        cells = new Cell[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }

        // 시작 위치 설정
        startPosition = new Position(0, HEIGHT / 2);
        cells[startPosition.getY()][startPosition.getX()].openAllSides();

        // 목표 위치 설정
        goalPositions = new ArrayList<>();
        goalPositions.add(new Position(WIDTH - 1, 1));
        goalPositions.add(new Position(WIDTH - 1, HEIGHT / 2));
        goalPositions.add(new Position(WIDTH - 1, HEIGHT - 2));

        // 골드 카드와 돌 카드 배치
        goalCards = new HashMap<>();
        revealedGoals = new boolean[3];

        List<GoalCardType> types = new ArrayList<>();
        types.add(GoalCardType.GOLD);
        types.add(GoalCardType.ROCK);
        types.add(GoalCardType.ROCK);
        Collections.shuffle(types);

        for (int i = 0; i < 3; i++) {
            GoalCard goalCard = new GoalCard("goal_" + i, types.get(i).name(), types.get(i));
            goalCards.put(goalPositions.get(i), goalCard);
            revealedGoals[i] = false;
        }
    }

    public boolean canPlaceCard(PathCard card, Position position) {
        if (position.getX() < 0 || position.getX() >= WIDTH ||
                position.getY() < 0 || position.getY() >= HEIGHT) {
            return false;
        }

        Cell cell = cells[position.getY()][position.getX()];
        if (cell.hasCard()) {
            return false;
        }

        // 목표 카드 위치인지 확인
        for (Position goalPos : goalPositions) {
            if (position.equals(goalPos)) {
                return false;
            }
        }

        // 인접한 카드가 있는지 확인
        boolean hasAdjacentCard = false;

        // 위쪽 셀 확인
        if (position.getY() > 0) {
            Cell topCell = cells[position.getY() - 1][position.getX()];
            if (topCell.hasCard()) {
                hasAdjacentCard = true;
                // 연결 가능한지 확인
                if (topCell.getBottomSide() != Cell.Side.EMPTY &&
                        !isConnectable(topCell.getBottomSide(), card.getSides()[0])) {
                    return false;
                }
            }
        }

        // 오른쪽 셀 확인
        if (position.getX() < WIDTH - 1) {
            Cell rightCell = cells[position.getY()][position.getX() + 1];
            if (rightCell.hasCard()) {
                hasAdjacentCard = true;
                if (rightCell.getLeftSide() != Cell.Side.EMPTY &&
                        !isConnectable(rightCell.getLeftSide(), card.getSides()[1])) {
                    return false;
                }
            }
        }

        // 아래쪽 셀 확인
        if (position.getY() < HEIGHT - 1) {
            Cell bottomCell = cells[position.getY() + 1][position.getX()];
            if (bottomCell.hasCard()) {
                hasAdjacentCard = true;
                if (bottomCell.getTopSide() != Cell.Side.EMPTY &&
                        !isConnectable(bottomCell.getTopSide(), card.getSides()[2])) {
                    return false;
                }
            }
        }

        // 왼쪽 셀 확인
        if (position.getX() > 0) {
            Cell leftCell = cells[position.getY()][position.getX() - 1];
            if (leftCell.hasCard()) {
                hasAdjacentCard = true;
                if (leftCell.getRightSide() != Cell.Side.EMPTY &&
                        !isConnectable(leftCell.getRightSide(), card.getSides()[3])) {
                    return false;
                }
            }
        }

        return hasAdjacentCard;
    }

    private boolean isConnectable(Cell.Side side1, PathCard.Side side2) {
        if (side1 == Cell.Side.PATH && side2 == PathCard.Side.PATH) return true;
        if (side1 == Cell.Side.DEADEND && side2 == PathCard.Side.DEADEND) return true;
        return false;
    }

    public void placeCard(PathCard card, Position position) {
        cells[position.getY()][position.getX()].placePathCard(card);

        // 목표 카드 근처에 카드를 놓았는지 확인하고 공개
        for (int i = 0; i < goalPositions.size(); i++) {
            Position goalPos = goalPositions.get(i);
            if (isAdjacent(position, goalPos) && !revealedGoals[i]) {
                revealedGoals[i] = true;
                goalCards.get(goalPos).reveal();
            }
        }
    }

    private boolean isAdjacent(Position pos1, Position pos2) {
        return (Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY())) == 1;
    }


    public boolean isGoldReached() {
        for (Position goalPos : goalPositions) {
            GoalCard goalCard = goalCards.get(goalPos);
            // getType() 대신 getGoalType() 사용
            if (goalCard.getGoalType() == GoalCardType.GOLD && isConnected(startPosition, goalPos)) {
                return true;
            }
        }
        return false;
    }


    private boolean isConnected(Position start, Position end) {
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            // 목표 위치 바로 앞에 도달했는지 확인
            if (isAdjacent(current, end)) {
                Cell currentCell = cells[current.getY()][current.getX()];

                // 현재 셀에서 목표 셀로 연결되는지 확인
                if (current.getX() < end.getX() && currentCell.getRightSide() == Cell.Side.PATH) {
                    return true;
                }
                if (current.getX() > end.getX() && currentCell.getLeftSide() == Cell.Side.PATH) {
                    return true;
                }
                if (current.getY() < end.getY() && currentCell.getBottomSide() == Cell.Side.PATH) {
                    return true;
                }
                if (current.getY() > end.getY() && currentCell.getTopSide() == Cell.Side.PATH) {
                    return true;
                }
            }

            // 이웃 셀 탐색
            List<Position> neighbors = getConnectedNeighbors(current);
            for (Position neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return false;
    }

    private List<Position> getConnectedNeighbors(Position position) {
        List<Position> neighbors = new ArrayList<>();
        Cell cell = cells[position.getY()][position.getX()];

        // 위쪽 확인
        if (position.getY() > 0 && cell.getTopSide() == Cell.Side.PATH) {
            Cell topCell = cells[position.getY() - 1][position.getX()];
            if (topCell.getBottomSide() == Cell.Side.PATH) {
                neighbors.add(new Position(position.getX(), position.getY() - 1));
            }
        }

        // 오른쪽 확인
        if (position.getX() < WIDTH - 1 && cell.getRightSide() == Cell.Side.PATH) {
            Cell rightCell = cells[position.getY()][position.getX() + 1];
            if (rightCell.getLeftSide() == Cell.Side.PATH) {
                neighbors.add(new Position(position.getX() + 1, position.getY()));
            }
        }

        // 아래쪽 확인
        if (position.getY() < HEIGHT - 1 && cell.getBottomSide() == Cell.Side.PATH) {
            Cell bottomCell = cells[position.getY() + 1][position.getX()];
            if (bottomCell.getTopSide() == Cell.Side.PATH) {
                neighbors.add(new Position(position.getX(), position.getY() + 1));
            }
        }

        // 왼쪽 확인
        if (position.getX() > 0 && cell.getLeftSide() == Cell.Side.PATH) {
            Cell leftCell = cells[position.getY()][position.getX() - 1];
            if (leftCell.getRightSide() == Cell.Side.PATH) {
                neighbors.add(new Position(position.getX() - 1, position.getY()));
            }
        }

        return neighbors;
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            clone.cells = new Cell[HEIGHT][WIDTH];
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    clone.cells[y][x] = this.cells[y][x].copy();
                }
            }
            clone.goalPositions = new ArrayList<>(this.goalPositions);
            clone.goalCards = new HashMap<>(this.goalCards);
            clone.revealedGoals = this.revealedGoals.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
