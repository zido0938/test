package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.enums.UserStatus;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class User {
    private UUID id;
    private String nickname;
    private LocalDate birthDate;
    private UserStatus status = UserStatus.ACTIVATED;

    public User(String nickname, LocalDate birthDate) {
        this.id = UUID.randomUUID();
        this.nickname = nickname;
        this.birthDate = birthDate;
    }
}
