package com.example.alert_service.adapter.in.messaging.mapper;

import com.example.alert_service.domain.model.Alert;
import com.example.alert_service.domain.model.Severity;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class DatadogAlertMapper implements SourceAlertMapper {
    @Override
    public String source() {
        return "datadog";
    }

    @Override
    public List<Alert> map(JsonNode payload) {
        String sourceEventId = payload
                .required("source_event_id")
                .asText();

        String resource = payload
                .required("resource")
                .asText();

        Severity severity = Severity.valueOf(
                payload.required("severity")
                        .asText()
                        .toUpperCase()
        );

        Instant occurredAt = Instant.ofEpochSecond(
                payload.required("occurred_at")
                        .asLong()
        );

        return List.of(new Alert(
                UUID.randomUUID(),
                source(),
                sourceEventId,
                resource,
                severity,
                occurredAt
        ));
    }
}
