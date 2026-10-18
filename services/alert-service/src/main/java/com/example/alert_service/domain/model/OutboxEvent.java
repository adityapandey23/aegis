package com.example.alert_service.domain.model;

import java.util.UUID;

public record OutboxEvent(
        UUID eventId,
        String messageKey,
        String payload
) {}
