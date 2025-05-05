package com.goldstone.saboteur_backend.domain;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.enums.GameRoomStatus;
import com.goldstone.saboteur_backend.dtos.gameRoom.request.CreateGameRoomRequestDto;
import com.goldstone.saboteur_backend.dtos.gameRoom.response.CreateGameRoomResponseDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GameRoom {
    private final UUID id;
    private final GameRoomStatus status = GameRoomStatus.READY;
    private final User master;
    private final List<User> players;
    private final GameRoomSetting setting;

    public GameRoom(User master, String title, int maxPlayers, int minPlayers) {
        this.id = UUID.randomUUID();
        this.master = master;
        this.players = new ArrayList<>();
        this.players.add(master);
        this.setting = new GameRoomSetting(this, title, maxPlayers, minPlayers);
    }

    public static GameRoom createGameRoom(SocketIOClient client, CreateGameRoomRequestDto dto) {
        User master = new User(dto.getUserId().toString(), LocalDate.now());

        GameRoom gameRoom = new GameRoom(master, "겁나 쩌는 게임", 10, 3);

        CreateGameRoomResponseDto responseDto = CreateGameRoomResponseDto.of(gameRoom);

        client.joinRoom(gameRoom.getId().toString());
        client.sendEvent("gameRoomCreated", responseDto);

        return gameRoom;
    }
}
