package com.goldstone.saboteur_backend.service.gameRoom;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.GameRoom;
import com.goldstone.saboteur_backend.domain.User;

public interface GameRoomService {
    void joinUser(SocketIOClient client, GameRoom gameRoom, User user) throws Exception;
}
