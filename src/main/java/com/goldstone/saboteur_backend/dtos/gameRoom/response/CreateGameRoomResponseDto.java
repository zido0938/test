package com.goldstone.saboteur_backend.dtos.gameRoom.response;

import com.goldstone.saboteur_backend.domain.GameRoom;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGameRoomResponseDto {
    private String roomId;
    private String roomName;
    private int maxPlayers;
    private int currentPlayers;

    public static CreateGameRoomResponseDto from(GameRoom gameRoom) {
        return CreateGameRoomResponseDto.builder()
                .roomId(gameRoom.getId())
                .roomName(gameRoom.getName())
                .maxPlayers(gameRoom.getMaxPlayers())
                .currentPlayers(gameRoom.getPlayerCount())
                .build();
    }
}
