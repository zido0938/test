package com.goldstone.saboteur_backend.service.gameRoom;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.GameRoom;
import com.goldstone.saboteur_backend.domain.User;
import com.goldstone.saboteur_backend.dtos.gameRoom.GameRoomRequestDto;
import com.goldstone.saboteur_backend.dtos.gameRoom.GameRoomResponseDto;
import java.time.LocalDate;

public class GameRoomServiceImpl implements GameRoomService {
    public static GameRoom createGameRoom(
            SocketIOClient client, GameRoomRequestDto.CreateGameRoomRequestDto dto) {
        GameRoom gameRoom = new GameRoom(new User(dto.getUserId().toString(), LocalDate.now()));

        GameRoomResponseDto.CreateGameRoomResponseDto responseDto =
                GameRoomResponseDto.CreateGameRoomResponseDto.of(gameRoom);

        client.sendEvent("gameRoomCreated", responseDto);

        return gameRoom;
    }
}
