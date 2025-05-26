package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.card.Card;
import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.enums.GameRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Player extends BaseEntity {
    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GameRole role;

    private boolean isPickBroken;
    private boolean isLampBroken;
    private boolean isCartBroken;

    @Transient
    private List<Card> hand;

    @ManyToOne
    @JoinColumn(name = "game_room_id")
    private GameRoom gameRoom;

    public Player() {
        this.id = UUID.randomUUID().toString();
        this.hand = new ArrayList<>();
        this.isPickBroken = false;
        this.isLampBroken = false;
        this.isCartBroken = false;
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void removeCardFromHand(Card card) {
        hand.removeIf(c -> c.getId().equals(card.getId()));
    }

    public Card getCard(String cardId) {
        return hand.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElse(null);
    }

    public boolean isHandicapped() {
        return isPickBroken || isLampBroken || isCartBroken;
    }

    public void fixTool(String tool) {
        switch (tool) {
            case "PICK":
                isPickBroken = false;
                break;
            case "LAMP":
                isLampBroken = false;
                break;
            case "CART":
                isCartBroken = false;
                break;
        }
    }

    public void breakTool(String tool) {
        switch (tool) {
            case "PICK":
                isPickBroken = true;
                break;
            case "LAMP":
                isLampBroken = true;
                break;
            case "CART":
                isCartBroken = true;
                break;
        }
    }
}
