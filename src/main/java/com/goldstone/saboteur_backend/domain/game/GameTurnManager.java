package com.goldstone.saboteur_backend.domain.game;

import com.goldstone.saboteur_backend.domain.mapping.UserGameRoom;
import com.goldstone.saboteur_backend.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class GameTurnManager {
    private List<UserGameRoom> userGameRooms;
    private int currentTurnIndex = 0;

    public GameTurnManager(List<UserGameRoom> userGameRooms) {
        this.userGameRooms = userGameRooms;
    }

    public User getCurrentTurnUser() {
        return userGameRooms.get(currentTurnIndex).getUser();
    }

    public User nextTurn() {
        currentTurnIndex = (currentTurnIndex + 1) % userGameRooms.size();
        return getCurrentTurnUser();
    }
}
