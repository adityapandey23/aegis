package com.example.alert_service.adapter.in.messaging.mapper;

import com.example.alert_service.domain.model.Alert;
import com.example.alert_service.domain.model.Severity;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrometheusAlertMapper implements SourceAlertMapper {
    @Override
    public String source() {
        return "prometheus";
    }

    @Override
    public List<Alert> map(JsonNode payload) {
        JsonNode alerts = payload.required("alerts");

        if (!alerts.isArray()) {
            throw new IllegalArgumentException(
                    "Prometheus field 'alerts' must be an array"
            );
        }

        List<Alert> result = new ArrayList<>();

        for (JsonNode alert : alerts) {
            JsonNode labels = alert.required("labels");

            Severity severity = switch (
                    labels.path("severity")
                            .asText("INFO")
                            .toUpperCase()
                    ) {
                case "CRITICAL" -> Severity.CRITICAL;
                case "WARNING", "WARN" -> Severity.WARNING;
                default -> Severity.INFO;
            };

            String resource = labels
                    .path("instance")
                    .asText(
                            labels.path("job")
                                    .asText("unknown")
                    );

            result.add(new Alert(
                    UUID.randomUUID(),
                    source(),
                    alert.required("fingerprint").asText(),
                    resource,
                    severity,
                    Instant.parse(
                            alert.required("startsAt").asText()
                    )
            ));
        }

        return result;
    }
}
