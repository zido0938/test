package com.goldstone.saboteur_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String name;

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public User(String name) {
        this();
        this.name = name;
    }
}
