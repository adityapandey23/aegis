package com.example.alert_service.application.service;

import com.example.alert_service.adapter.in.messaging.mapper.AlertMapperFactory;
import com.example.alert_service.application.port.in.TransformRawAlert;
import com.example.alert_service.domain.model.Alert;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class TransformAlertService implements TransformRawAlert {
    private final ObjectMapper objectMapper;
    private final AlertMapperFactory alertMapperFactory;

    public TransformAlertService(
            ObjectMapper objectMapper,
            AlertMapperFactory alertMapperFactory
    ) {
        this.objectMapper = objectMapper;
        this.alertMapperFactory = alertMapperFactory;
    }

    @Override
    public Alert transform(String rawAlert) {
        JsonNode envelope = objectMapper.readTree(rawAlert);

        // Here we have to somewhat find a way to figure out it's from which source
        // or some condition, by which we can identify the system, maybe some regex or something else ?
        String source = envelope.required("source").asText();
        JsonNode payload = envelope.required("payload");

        return alertMapperFactory.forSource(source).map(payload);
    }
}
