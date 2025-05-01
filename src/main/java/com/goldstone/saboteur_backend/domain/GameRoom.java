package com.goldstone.saboteur_backend.domain;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.enums.GameRoomStatus;
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

    public void joinUser(SocketIOClient client, User user) throws Exception {
        try {
            if (this.players.size() >= this.setting.getMaxPlayers()) {
                throw new Exception("Max player reached");
            }

            this.players.add(user);
            client.joinRoom(this.id.toString());
        } catch (Exception e) {
            client.sendEvent("error", e.getMessage());
        }
    }
}
