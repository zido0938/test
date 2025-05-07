package com.goldstone.saboteur_backend.domain.card;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoldCard extends GoalCard {
	private Integer amount;
}
