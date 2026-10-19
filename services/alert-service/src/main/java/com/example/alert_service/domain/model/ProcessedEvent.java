package com.example.alert_service.domain.model;

import java.util.UUID;

public record ProcessedEvent(
        UUID eventId,
        Alert alert
) {}
