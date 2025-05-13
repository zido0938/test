package com.goldstone.saboteur_backend.domain.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Card implements Cloneable {
    public enum Type {
        PATHWAY, DEADEND, ACTION, ROLE, GOAL
    }

    private String id;
    private String name;
    private Type type;
    private String imageUrl;

    public Card(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
        // 원본 프로젝트의 이미지 경로 형식으로 수정
        this.imageUrl = "/img/cards/" + id + ".png";
    }

    public abstract Card copy();

    @Override
    public Card clone() {
        try {
            return (Card) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
