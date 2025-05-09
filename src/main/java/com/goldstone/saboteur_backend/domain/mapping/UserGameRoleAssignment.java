package com.goldstone.saboteur_backend.domain.mapping;

import com.goldstone.saboteur_backend.domain.GameRoleAssignment;
import com.goldstone.saboteur_backend.domain.User;
import com.goldstone.saboteur_backend.domain.enums.GameRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserGameRoleAssignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "game_role_id")
	private GameRoleAssignment gameRoleAssignment;
}
