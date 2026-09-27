package com.example.alert_service.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Alert(
    UUID id,
    String source,
    String sourceEventId,
    Severity severity,
    Instant occurredAt
) {}
