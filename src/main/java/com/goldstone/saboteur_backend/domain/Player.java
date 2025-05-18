package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.card.Card;
import com.goldstone.saboteur_backend.domain.enums.GameRole;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Player {
    @Id
    @Column(length = 255)
    private String id;
    private String name;

    @Enumerated(EnumType.STRING)
    private GameRole role;

    @Transient
    private List<Card> hand;

    private boolean pickBroken;
    private boolean lampBroken;
    private boolean cartBroken;

    @ManyToOne
    @JoinColumn(name = "game_room_id", referencedColumnName = "id", columnDefinition = "varchar(255)")
    private GameRoom gameRoom;

    public Player() { this.hand = new ArrayList<>(); }
    public Player(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.hand = new ArrayList<>();
        this.pickBroken = false;
        this.lampBroken = false;
        this.cartBroken = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public GameRole getRole() { return role; }
    public void setRole(GameRole role) { this.role = role; }
    public List<Card> getHand() { return hand; }
    public void addCardToHand(Card card) { if (card != null) hand.add(card); }
    public void removeCardFromHand(Card card) { hand.remove(card); }
    public boolean isPickBroken() { return pickBroken; }
    public boolean isLampBroken() { return lampBroken; }
    public boolean isCartBroken() { return cartBroken; }
    public boolean isHandicapped() { return pickBroken || lampBroken || cartBroken; }
    public void breakTool(String tool) {
        if ("PICK".equals(tool)) pickBroken = true;
        else if ("LAMP".equals(tool)) lampBroken = true;
        else if ("CART".equals(tool)) cartBroken = true;
    }
    public void fixTool(String tool) {
        if ("PICK".equals(tool)) pickBroken = false;
        else if ("LAMP".equals(tool)) lampBroken = false;
        else if ("CART".equals(tool)) cartBroken = false;
    }
    public boolean canPlayCards() { return !isHandicapped() && !hand.isEmpty(); }
    public void setGameRoom(GameRoom gameRoom) { this.gameRoom = gameRoom; }
    public GameRoom getGameRoom() { return gameRoom; }
}
