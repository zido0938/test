package com.goldstone.saboteur_backend.socketIo;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.goldstone.saboteur_backend.domain.GameRoom;
import com.goldstone.saboteur_backend.domain.User;
import com.goldstone.saboteur_backend.dtos.gameRoom.request.CreateGameRoomRequestDto;
import com.goldstone.saboteur_backend.service.gameRoom.GameRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SocketIoController {
    private final SocketIOServer server;
    private final GameRoomService gameRoomService;

    /** 소켓 이벤트 리스너 등록 */
    public SocketIoController(SocketIOServer server, GameRoomService gameRoomService) {
        this.server = server;
        this.gameRoomService = gameRoomService;

        // 소켓 이벤트 리스너 등록
        server.addConnectListener(listenConnected());
        server.addDisconnectListener(listenDisconnected());

        server.addEventListener(
                "createGameRoom",
                CreateGameRoomRequestDto.class,
                (client, data, ackSender) -> GameRoom.createGameRoom(client, data));

        server.addEventListener(
                "joinGameRoom",
                JoinGameRoomRequest.class,
                onJoinGameRoom());

        server.addEventListener(
                "startGame",
                StartGameRequest.class,
                onStartGame());
    }

    public ConnectListener listenConnected() {
        return (client) -> {
            Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
            log.info("connect:" + params.toString());
        };
    }

    public DisconnectListener listenDisconnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            log.info("disconnect: " + sessionId);
            client.disconnect();
        };
    }

    private DataListener<JoinGameRoomRequest> onJoinGameRoom() {
        return (client, data, ackRequest) -> {
            try {
                GameRoom gameRoom = GameRoom.getGameRoom(data.getRoomId());
                User user = new User(data.getUserName());
                gameRoomService.joinUser(client, gameRoom, user);
            } catch (Exception e) {
                log.error("게임룸 참가 실패", e);
                client.sendEvent("error", e.getMessage());
            }
        };
    }

    private DataListener<StartGameRequest> onStartGame() {
        return (client, data, ackRequest) -> {
            try {
                GameRoom gameRoom = GameRoom.getGameRoom(data.getRoomId());
                if (gameRoom != null) {
                    gameRoom.startGame();
                } else {
                    throw new Exception("게임룸을 찾을 수 없습니다.");
                }
            } catch (Exception e) {
                log.error("게임 시작 실패", e);
                client.sendEvent("error", e.getMessage());
            }
        };
    }

    // 요청 클래스들
    public static class JoinGameRoomRequest {
        private String roomId;
        private String userName;

        public String getRoomId() { return roomId; }
        public void setRoomId(String roomId) { this.roomId = roomId; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
    }

    public static class StartGameRequest {
        private String roomId;

        public String getRoomId() { return roomId; }
        public void setRoomId(String roomId) { this.roomId = roomId; }
    }
}
