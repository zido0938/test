package com.goldstone.saboteur_backend.domain.card;

import com.goldstone.saboteur_backend.domain.GameCardAssignment;
import com.goldstone.saboteur_backend.domain.GameCardPool;
import com.goldstone.saboteur_backend.domain.UserCardDeck;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public abstract class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_card_assignment_id")
    private GameCardAssignment gameCardAssignment;

    @ManyToOne
    @JoinColumn(name = "game_card_pool_id")
    private GameCardPool gameCardPool;

    @ManyToOne
    @JoinColumn(name = "user_card_deck_id")
    private UserCardDeck userCardDeck;
}
