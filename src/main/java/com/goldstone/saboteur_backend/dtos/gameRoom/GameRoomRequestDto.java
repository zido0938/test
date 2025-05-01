package com.goldstone.saboteur_backend.dtos.gameRoom;

import java.util.UUID;
import lombok.*;

public class GameRoomRequestDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CreateGameRoomRequestDto {
        private UUID userId;
    }
}
