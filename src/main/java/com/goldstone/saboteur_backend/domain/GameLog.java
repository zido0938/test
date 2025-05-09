package com.goldstone.saboteur_backend.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goldstone.saboteur_backend.domain.common.BaseEntity;
import com.goldstone.saboteur_backend.domain.mapping.GameUserLog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GameLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;

    @OneToMany(mappedBy = "gameLog")
    private List<User> users;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "gameLog")
    private List<GameRoundLog> roundLogs;

    public String createWholeRawLog() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("gameId", gameId);
        logMap.put("startDate", startDate);
        logMap.put("endDate", endDate);
        logMap.put("users", users);
        logMap.put("roundLogs", roundLogs);
        try {
            return mapper.writeValueAsString(logMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert log to JSON", e);
        }
    }

    public String createRawLog() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("gameId", gameId);
        logMap.put("startDate", startDate);
        logMap.put("endDate", endDate);
        try {
            return mapper.writeValueAsString(logMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert raw log to JSON", e);
        }
    }
}
