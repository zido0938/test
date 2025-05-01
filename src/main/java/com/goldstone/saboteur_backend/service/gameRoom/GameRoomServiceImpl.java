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
        User master = new User(dto.getUserId().toString(), LocalDate.now());

        GameRoom gameRoom = new GameRoom(master, "겁나 쩌는 게임", 10, 3);

        GameRoomResponseDto.CreateGameRoomResponseDto responseDto =
                GameRoomResponseDto.CreateGameRoomResponseDto.of(gameRoom);

        client.joinRoom(gameRoom.getId().toString());
        client.sendEvent("gameRoomCreated", responseDto);

        return gameRoom;
    }
}
