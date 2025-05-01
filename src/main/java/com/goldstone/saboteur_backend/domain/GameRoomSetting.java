package com.goldstone.saboteur_backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class GameRoomSetting {
    private final GameRoom gameRoom;
    private String title;
    private int maxPlayers;
    private int minPlayers;

    public GameRoomSetting(GameRoom gameRoom, String title, int maxPlayers, int minPlayers) {
        this.gameRoom = gameRoom;
        this.title = title;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }

    public void modifyTitle(String newTitle) {
        this.title = newTitle;
    }

    public void modifyMaxPlayers(int newMaxPlayers) {
        this.maxPlayers = newMaxPlayers;
    }

    public void modifyMinPlayers(int newMinPlayers) {
        this.minPlayers = newMinPlayers;
    }
}
