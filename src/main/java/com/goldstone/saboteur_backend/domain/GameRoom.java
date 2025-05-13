package com.goldstone.saboteur_backend.domain;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.enums.GameRoomStatus;
import com.goldstone.saboteur_backend.dtos.gameRoom.request.CreateGameRoomRequestDto;
import com.goldstone.saboteur_backend.dtos.gameRoom.response.CreateGameRoomResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Getter
@Setter
public class GameRoom extends BaseEntity {
    private static final Map<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

    @Id
    private String id;

    private String name;

    private int maxPlayers;

    @Enumerated(EnumType.STRING)
    private GameRoomStatus status;

    @OneToMany(mappedBy = "gameRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();

    @Transient
    private Map<String, SocketIOClient> clients = new HashMap<>();

    @Transient
    private Game game;

    public GameRoom() {
        this.id = UUID.randomUUID().toString();
        this.status = GameRoomStatus.WAITING;
        this.maxPlayers = 8;
    }

    public GameRoom(String name) {
        this();
        this.name = name;
    }

    public GameRoom(String name, int maxPlayers) {
        this();
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    public static void createGameRoom(SocketIOClient client, CreateGameRoomRequestDto data) {
        // 게임룸 생성
        GameRoom gameRoom = new GameRoom(data.getRoomName(), data.getMaxPlayers());

        // 플레이어 생성 및 추가
        Player player = new Player(data.getUserName());
        gameRoom.addPlayer(client, player);

        // 게임룸 저장
        gameRooms.put(gameRoom.getId(), gameRoom);

        // 클라이언트에 응답
        client.sendEvent("createGameRoomResponse", CreateGameRoomResponseDto.from(gameRoom));
    }

    public void addPlayer(SocketIOClient client, Player player) {
        players.add(player);
        player.setGameRoom(this);
        clients.put(player.getId(), client);
        client.joinRoom(id);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setGameRoom(null);
        SocketIOClient client = clients.remove(player.getId());
        if (client != null) {
            client.leaveRoom(id);
        }
    }

    public void startGame() {
        if (players.size() < 3) {
            throw new IllegalStateException("게임을 시작하려면 최소 3명의 플레이어가 필요합니다.");
        }

        if (status != GameRoomStatus.WAITING) {
            throw new IllegalStateException("게임이 이미 시작되었거나 종료되었습니다.");
        }

        game = new Game();

        // 게임에 플레이어 추가
        for (Player player : players) {
            game.addPlayer(player);
        }

        // 게임 시작
        game.start();

        // 상태 변경
        status = GameRoomStatus.PLAYING;

        // 모든 클라이언트에게 게임 시작 알림
        for (SocketIOClient client : clients.values()) {
            client.sendEvent("gameStarted", game.getId());
        }
    }

    public void endGame() {
        if (status != GameRoomStatus.PLAYING) {
            throw new IllegalStateException("진행 중인 게임이 없습니다.");
        }

        status = GameRoomStatus.FINISHED;
        game = null;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public static GameRoom getGameRoom(String roomId) {
        return gameRooms.get(roomId);
    }
}
