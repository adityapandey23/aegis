package com.example.alert_service.adapter.out.persistance;

import com.example.alert_service.application.port.out.AlertOutboxEvent;
import com.example.alert_service.domain.model.ProcessedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import tools.jackson.databind.ObjectMapper;

public class JdbcAlertOutboxEvent implements AlertOutboxEvent {

    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;

    public JdbcAlertOutboxEvent(
            ObjectMapper objectMapper,
            JdbcTemplate jdbcTemplate
    ) {
        this.objectMapper = objectMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void append(ProcessedEvent event) {
        jdbcTemplate.update("""
            INSERT INTO alert_outbox (event_id, message_key, payload)
            VALUES (?, ?, CAST(? AS jsonb))
            """,
                event.eventId(),
                event.alert().resource(),
                objectMapper.writeValueAsString(event)
        );
    }
}
