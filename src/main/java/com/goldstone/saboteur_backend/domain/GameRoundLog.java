package com.goldstone.saboteur_backend.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.enums.GameRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GameRoundLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long gameId;
	private Long roundId;

	private LocalDateTime roundStartDate;
	private LocalDateTime roundEndDate;

	@Enumerated(EnumType.STRING)
	private GameRole winnerRole;

	@OneToMany(mappedBy = "roundLog")
	private List<GameRoundUserLog> userLogs;

	@ManyToOne
	private GameLog gameLog;

	public String createRawLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("Game ID: ").append(gameId).append("\n");
		sb.append("Round ID: ").append(roundId).append("\n");
		sb.append("Round Start Date: ").append(roundStartDate).append("\n");
		sb.append("Round End Date: ").append(roundEndDate).append("\n");
		sb.append("Winner Role: ").append(winnerRole).append("\n");
		sb.append("User Logs: ").append(userLogs).append("\n");
		return sb.toString();
	}
}
