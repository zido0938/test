package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GameResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalPlayTime;

    @OneToMany
    @JoinColumn(name = "game_result_id")
    private List<UserRank> userRank;

    // saveResult (repository 계층..?)

    // getFinalRanks() 는 getUserRank() 와 다른 메서드..?
}
