package com.example.alert_service.adapter.in.messaging.mapper;

import com.example.alert_service.domain.model.Alert;
import com.example.alert_service.domain.model.Severity;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.UUID;

// TODO: Implement

public class DatadogAlertMapper implements SourceAlertMapper {
    @Override
    public String source() {
        return "datadog";
    }

    @Override
    public Alert map(JsonNode payload) {
        return new Alert(
                UUID.randomUUID(),
                this.source(),
                "test-source-event-id",
                Severity.WARNING,
                Instant.now()
        );
    }
}
