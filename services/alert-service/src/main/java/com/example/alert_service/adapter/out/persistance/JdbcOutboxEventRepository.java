package com.example.alert_service.adapter.out.persistance;

import com.example.alert_service.application.port.out.OutboxEventRepository;
import com.example.alert_service.domain.model.OutboxEvent;
import com.example.alert_service.domain.model.ProcessedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class JdbcOutboxEventRepository implements OutboxEventRepository {

    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;

    public JdbcOutboxEventRepository(
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

    @Override
    public List<OutboxEvent> findUnpublishedEvents(int batchSize) {
        return jdbcTemplate.query("""
                SELECT event_id, message_key, payload
                FROM alert_outbox
                WHERE published_at IS NULL
                ORDER BY created_at, event_id
                LIMIT ?
                """,
                (resultSet, rowNum) -> new OutboxEvent(
                        resultSet.getObject("event_id", java.util.UUID.class),
                        resultSet.getString("message_key"),
                        resultSet.getString("payload")
                ),
                batchSize
        );
    }

    @Override
    public void markAsPublished(OutboxEvent event) {
        jdbcTemplate.update("""
                UPDATE alert_outbox
                SET published_at = CURRENT_TIMESTAMP
                WHERE event_id = ?
                  AND published_at IS NULL
                """,
                event.eventId()
        );
    }
}
