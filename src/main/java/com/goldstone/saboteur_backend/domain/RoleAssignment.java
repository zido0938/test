package com.goldstone.saboteur_backend.domain;

import java.util.ArrayList;
import java.util.List;

import com.goldstone.saboteur_backend.domain.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RoleAssignment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	private List<User> users;

	private Integer maxSabotage;

	private Integer minUsersForSabotage;

	//assignRoles()
}
