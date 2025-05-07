package com.goldstone.saboteur_backend.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.goldstone.saboteur_backend.domain.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GameLog extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long gameId;

	@OneToMany
	private List<User> users;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@OneToMany(mappedBy = "gameLog")
	private List<GameRoundLog> roundLogs;

	public String createWholeRawLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("Game ID: ").append(gameId).append("\n");
		sb.append("Start Date: ").append(startDate).append("\n");
		sb.append("End Date: ").append(endDate).append("\n");
		sb.append("Users: ").append(users).append("\n");
		sb.append("Round Logs: ").append(roundLogs).append("\n");
		return sb.toString();
	}

	public String createRawLog() { //간단한 버전?
		StringBuilder sb = new StringBuilder();
		sb.append("Game ID: ").append(gameId).append("\n");
		sb.append("Start Date: ").append(startDate).append("\n");
		sb.append("End Date: ").append(endDate).append("\n");
		return sb.toString();
	}
}
