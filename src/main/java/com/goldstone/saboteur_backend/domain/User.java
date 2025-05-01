package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.enums.UserStatus;
import java.time.LocalDate;
import java.util.UUID;

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

    @Override
    public String toString() {
        return "id: "
                + this.id.toString()
                + "\n"
                + "nickname: "
                + this.nickname
                + "\n"
                + "birthDate: "
                + this.birthDate
                + "\n"
                + "status: "
                + this.status
                + "\n";
    }
}
