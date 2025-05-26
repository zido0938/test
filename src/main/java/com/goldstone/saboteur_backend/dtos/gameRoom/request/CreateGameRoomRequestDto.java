package com.goldstone.saboteur_backend.dtos.gameRoom.request;

import lombok.Data;

@Data
public class CreateGameRoomRequestDto {
    private String roomName;
    private String userName;
    private int maxPlayers;
}
