package com.goldstone.saboteur_backend.domain.board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new Board();
    }

    @AfterEach
    void tearDown() {
    }

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
    void isValidPosition() {
    }

    @Test
    void placeCard() {
    }

    @Test
    void getCell() {
    }

    @Test
    void getOrCreateCell() {
    }

    @Test
    void getGoals() {
    }

    @Test
    void isConnected() {
    }
}