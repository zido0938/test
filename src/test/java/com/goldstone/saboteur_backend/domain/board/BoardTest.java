package com.goldstone.saboteur_backend.domain.board;

import static org.junit.jupiter.api.Assertions.*;

import com.goldstone.saboteur_backend.domain.card.PathCard;
import com.goldstone.saboteur_backend.domain.enums.PathCardType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드 객체 생성시, 의도된 대로 초기화된다.")
    void initializeBoard() {
        Board board = new Board();

        assertEquals(45, board.getDynamicCells().size());
        assertEquals(45, board.getInitArea().size());

        // start card
        assertFalse(board.getCell(new Position(0, 2)).isEmptyCard());

        // goal card
        assertFalse(board.getCell(new Position(8, 0)).isEmptyCard());
        assertFalse(board.getCell(new Position(8, 2)).isEmptyCard());
        assertFalse(board.getCell(new Position(8, 4)).isEmptyCard());
    }

    @Test
    void isValidPosition() {}

    @Test
    void placeCard() {}

    @Test
    void getCell() {}

    @Test
    void getOrCreateCell() {}

    @Test
    void getGoals() {}

    @Test
    @DisplayName("startCell부터 가로로 이어지는 pathCard를 셋팅했을 때, 모든 길은 이어져있다.")
    void isConnectedHorizontalPathFromStartCell() {
        Board board = new Board();

        PathCard pathCard = new PathCard(PathCardType.CROSSROAD, false);
        Position positions[] = {
            new Position(1, 0),
            new Position(2, 0),
            new Position(3, 0),
            new Position(4, 0),
            new Position(5, 0),
            new Position(6, 0),
            new Position(7, 0),
        };

        for (int i = 0; i < positions.length; i++) {
            board.placeCard(positions[i], pathCard);
        }

        for (int i = 0; i < positions.length; i++) {
            int x = positions[i].getX();
            int y = positions[i].getY();

            assertTrue(board.isConnected(new Position(x - 1, y), positions[i]));
        }
    }

    @Test
    @DisplayName("startCell부터 세로로 이어지는 pathCard를 셋팅했을 때, 모든 길은 이어져있고, 맵이 확장된다.")
    void isConnectedVerticalPathFromStartCellAndExpandedBoard() {
        Board board = new Board();

        assertEquals(45, board.getDynamicCells().size());
        assertEquals(45, board.getInitArea().size());

        PathCard pathCard = new PathCard(PathCardType.CROSSROAD, false);
        Position positions[] = {
            new Position(0, 3),
            new Position(0, 4), // 여기까지가 DEFAULT_HEIGHT에 의해 생성된 높이
            new Position(0, 5),
            new Position(0, 6),
            new Position(0, 7),
            new Position(0, 8),
            new Position(0, 9),
            new Position(0, 10),
        };

        for (int i = 0; i < positions.length; i++) {
            board.placeCard(positions[i], pathCard);
        }

        assertNotEquals(45, board.getDynamicCells().size());
        assertEquals(45 + 6, board.getDynamicCells().size()); // 6개의 Cell이 추가됨

        for (int i = 0; i < positions.length; i++) {
            int x = positions[i].getX();
            int y = positions[i].getY();

            assertTrue(board.isConnected(new Position(x, y - 1), positions[i]));
        }
    }
}
