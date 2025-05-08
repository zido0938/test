package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.enums.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // UUID는 JPA가 자동으로 생성해주지 않음

    private LocalDate birthDate;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVATED;

    public User(String nickname, LocalDate birthDate) {
        this.nickname = nickname;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", nickname='" + nickname + "'}";
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public void activateUser() {
        this.status = UserStatus.ACTIVATED;
    }

    public void deactivateUser() {
        this.status = UserStatus.DEACTIVATED;
    }

    public void deleteUser() {
        this.status = UserStatus.DELETED;
    }
}
