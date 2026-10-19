package com.example.alert_service.adapter.out.persistance;

import com.example.alert_service.application.port.out.AlertRepository;
import com.example.alert_service.domain.model.Alert;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;

public class JdbcAlertRepository implements AlertRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAlertRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insertIfAbsent(Alert alert) {
        return jdbcTemplate.update("""
                INSERT INTO alerts (
                    id, source, source_event_id,
                    resource, severity, occurred_at
                )
                VALUES (?, ?, ?, ?, ?, ?)
                ON CONFLICT (source, source_event_id) DO NOTHING
                """,
                alert.id(),
                alert.source(),
                alert.sourceEventId(),
                alert.resource(),
                alert.severity().name(),
                Timestamp.from(alert.occurredAt())
        ) == 1;
    }
}
