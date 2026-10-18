package com.example.alert_service.application.service;

import com.example.alert_service.application.port.in.ProcessRawAlert;
import com.example.alert_service.application.port.in.TransformRawAlert;
import com.example.alert_service.application.port.out.AlertOutboxEvent;
import com.example.alert_service.application.port.out.AlertRepository;
import com.example.alert_service.domain.model.Alert;
import com.example.alert_service.domain.model.ProcessedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class ProcessAlertService implements ProcessRawAlert {
    private final AlertRepository alertRepository;
    private final AlertOutboxEvent alertOutboxEvent;
    private final TransformRawAlert transformRawAlert;

    public ProcessAlertService(
            AlertRepository alertRepository,
            AlertOutboxEvent alertOutboxEvent,
            TransformRawAlert transformRawAlert
    ) {
        this.alertRepository = alertRepository;
        this.alertOutboxEvent = alertOutboxEvent;
        this.transformRawAlert = transformRawAlert;
    }

    @Transactional
    @Override
    public void process(String rawAlert) {
        Alert alert = transformRawAlert.transform(rawAlert);

        if(!alertRepository.insertIfAbsent(alert)) return;

        alertOutboxEvent.append(new ProcessedEvent(
                UUID.randomUUID(),
                alert
        ));

    }
}
