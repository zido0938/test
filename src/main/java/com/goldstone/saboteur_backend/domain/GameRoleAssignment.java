package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.mapping.UserGameRoleAssignment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GameRoleAssignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "gameRoleAssignment")
    private List<UserGameRoleAssignment> userGameRoleAssignments;

    private Integer maxSabotage;

    private Integer minUsersForSabotage;

    // assignRoles()
}
