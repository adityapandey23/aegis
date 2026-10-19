package com.example.alert_service.application.port.out;

import com.example.alert_service.domain.model.OutboxEvent;
import com.example.alert_service.domain.model.ProcessedEvent;

import java.util.List;

public interface OutboxEventRepository {
    void append(ProcessedEvent event);

    List<OutboxEvent> findUnpublishedEvents(int batchSize);

    void markAsPublished(OutboxEvent event);
}
