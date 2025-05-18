package com.goldstone.saboteur_backend.domain.card;

public abstract class Card {
    private String id;
    private String name;
    private CardType type;
    private String imageUrl;

    public enum CardType {
        PATHWAY, ACTION, GOAL, ROLE  // ROLE 추가
    }

    public Card(String id, String name, CardType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        // resources의 카드 이미지 사용
        this.imageUrl = "/img/cards/" + id + ".png";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CardType getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
