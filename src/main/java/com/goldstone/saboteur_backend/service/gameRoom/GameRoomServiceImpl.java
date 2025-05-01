package com.goldstone.saboteur_backend.service.gameRoom;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.GameRoom;
import com.goldstone.saboteur_backend.domain.User;
import java.time.LocalDate;

public class GameRoomServiceImpl implements GameRoomService {
    public static GameRoom createGameRoom(
            SocketIOClient client, GameRoomRequestDto.CreateGameRoomRequestDto dto) {
        GameRoom gameRoom = new GameRoom(new User(dto.getUserId().toString(), LocalDate.now()));
        System.out.println("client info: " + client.toString());
        //        System.out.println("dto: " + dto.toString());
        System.out.println("game room info: " + gameRoom.toString());

        client.sendEvent("gameRoomCreated", "created game room!!!!");

        return gameRoom;
    }
}
