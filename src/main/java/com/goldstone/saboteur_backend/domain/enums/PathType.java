package com.goldstone.saboteur_backend.domain.enums;

import lombok.Getter;

@Getter
public enum PathType {
    PATH(1), // 통로
    ROCK(0), // 카드의 4면 중 막힌 부분
    EMPTY(-1),
    DEADEND(-2); // 미끼카드(1~9)

    private final int value;

    PathType(int value) {
        this.value = value;
    }
}
