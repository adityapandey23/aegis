package com.example.alert_service.application.port.out;

import com.example.alert_service.domain.model.ProcessedEvent;

public interface AlertOutboxEvent {
    void append(ProcessedEvent event);
}
