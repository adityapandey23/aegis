package com.example.alert_service.adapter.in.messaging.mapper;

import com.example.alert_service.domain.model.Alert;
import tools.jackson.databind.JsonNode;

public interface SourceAlertMapper {
    String source();

    Alert map(JsonNode payload);
}
