package com.example.alert_service.adapter.in.messaging.mapper;

import com.example.alert_service.domain.model.Alert;
import tools.jackson.databind.JsonNode;

import java.util.List;

public interface SourceAlertMapper {
    String source();

    List<Alert> map(JsonNode payload);
}
