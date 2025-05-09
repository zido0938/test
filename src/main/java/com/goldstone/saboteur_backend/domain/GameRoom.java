package com.goldstone.saboteur_backend.domain;

import com.corundumstudio.socketio.SocketIOClient;
import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.enums.GameRoomStatus;
import com.goldstone.saboteur_backend.dtos.gameRoom.request.CreateGameRoomRequestDto;
import com.goldstone.saboteur_backend.dtos.gameRoom.response.CreateGameRoomResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GameRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private GameRoomStatus status = GameRoomStatus.READY;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private User master;

    @OneToMany(mappedBy = "gameRoom")
    private List<User> players;

    private Integer round = 1;

    @OneToOne(mappedBy = "gameRoom")
    private GameSetting setting;

    public GameRoom(User master, String title, int maxPlayers, int minPlayers) {
        this.master = master;
        this.players = new ArrayList<>();
        this.players.add(master);
        this.setting = new GameSetting(this, title, maxPlayers, minPlayers);
    }

    public static GameRoom createGameRoom(SocketIOClient client, CreateGameRoomRequestDto dto) {
        User master = new User(dto.getUserId().toString(), LocalDate.now());

        GameRoom gameRoom = new GameRoom(master, "겁나 쩌는 게임", 10, 3);

        CreateGameRoomResponseDto responseDto = CreateGameRoomResponseDto.from(gameRoom);

        client.joinRoom(gameRoom.getId().toString());
        client.sendEvent("gameRoomCreated", responseDto);

        return gameRoom;
    }

    public boolean canStartGame() {
        return this.players.size() >= this.setting.getMinPlayers();
        // 추가?
    }

    public void endGame() {
        this.status = GameRoomStatus.END;
        this.players.clear();
        this.round = 1;
    }

    private void changeStatus(GameRoomStatus status) {
        this.status = status;
    }
}
