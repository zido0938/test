package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.enums.GameRoomStatus;
import java.util.UUID;

public class GameRoom {
    private UUID id;
    private GameRoomStatus status = GameRoomStatus.READY;
    private User master;

    public GameRoom(User master) {
        this.id = UUID.randomUUID();
        this.master = master;
    }

    @Override
    public String toString() {
        return "id: "
                + this.id.toString()
                + "\n"
                + "status: "
                + this.status.toString()
                + "\n"
                + "master: "
                + this.master.toString();
    }
}
