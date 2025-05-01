package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.enums.GameRoomStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GameRoom {
    private UUID id;
    private GameRoomStatus status = GameRoomStatus.READY;
    private User master;

    public GameRoom(User master) {
        this.id = UUID.randomUUID();
        this.master = master;
    }
}
