package com.goldstone.saboteur_backend.dtos.user;

import com.goldstone.saboteur_backend.domain.User;
import lombok.Builder;
import lombok.Getter;

public class UserResponseDto {

    @Builder
    @Getter
    public static class UserInfoResponseDto {
        private String id;
        private String nickname;
        private String birthDate;

        public static UserInfoResponseDto of(User user) {
            return UserInfoResponseDto.builder()
                    .id(user.getId().toString())
                    .nickname(user.getNickname())
                    .birthDate(user.getBirthDate().toString())
                    .build();
        }
    }
}
