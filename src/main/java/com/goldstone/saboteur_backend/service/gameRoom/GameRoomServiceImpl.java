package com.goldstone.saboteur_backend.service.gameRoom;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.GameRoom;
import com.goldstone.saboteur_backend.domain.Player;
import com.goldstone.saboteur_backend.domain.User;
import org.springframework.stereotype.Service;

@Service
public class GameRoomServiceImpl implements GameRoomService {

    @Override
    public void joinUser(SocketIOClient client, GameRoom gameRoom, User user) throws Exception {
        if (gameRoom == null) {
            throw new Exception("게임룸을 찾을 수 없습니다.");
        }

        if (gameRoom.isFull()) {
            throw new Exception("게임룸이 가득 찼습니다.");
        }

        Player player = new Player(user.getName());
        gameRoom.addPlayer(client, player);

        // 모든 클라이언트에게 유저 참가 알림
        client.getNamespace().getRoomOperations(gameRoom.getId()).sendEvent("userJoined", user.getName());
    }
}
