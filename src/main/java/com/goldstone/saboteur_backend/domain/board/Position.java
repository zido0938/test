package com.goldstone.saboteur_backend.domain.board;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Position {
    private int x;
    private int y;
}
