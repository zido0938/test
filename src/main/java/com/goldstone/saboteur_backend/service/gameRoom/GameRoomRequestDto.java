package com.goldstone.saboteur_backend.service.gameRoom;

import lombok.*;

import java.util.UUID;

public class GameRoomRequestDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class CreateGameRoomRequestDto {
        private UUID userId;
    }
}
