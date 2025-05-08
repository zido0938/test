package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.enums.GameRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GameRoundUserLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private GameRoundLog roundLog;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private GameRole role;

    private Integer goldCount;

    public String createRawLog() {
        StringBuilder sb = new StringBuilder();
        sb.append("User: ").append(user.getNickname()).append(", ");
        sb.append("Role: ").append(role).append(", ");
        sb.append("Gold Count: ").append(goldCount);
        return sb.toString();
    }
}
