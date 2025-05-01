package com.goldstone.saboteur_backend.dtos.gameRoom;

import com.goldstone.saboteur_backend.domain.GameRoom;
import com.goldstone.saboteur_backend.dtos.user.UserResponseDto;
import lombok.Builder;
import lombok.Getter;

public class GameRoomResponseDto {

    @Builder
    @Getter
    public static class CreateGameRoomResponseDto {
        private String id;
        private UserResponseDto.UserInfoResponseDto masterPlayer;

        public static CreateGameRoomResponseDto of(GameRoom gameRoom) {
            return CreateGameRoomResponseDto.builder()
                    .id(gameRoom.getId().toString())
                    .masterPlayer(UserResponseDto.UserInfoResponseDto.of(gameRoom.getMaster()))
                    .build();
        }
    }
}
