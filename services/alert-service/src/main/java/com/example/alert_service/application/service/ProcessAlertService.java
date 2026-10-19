package com.example.alert_service.application.service;

import com.example.alert_service.application.port.in.ProcessRawAlert;
import com.example.alert_service.application.port.in.TransformRawAlert;
import com.example.alert_service.application.port.out.OutboxEventRepository;
import com.example.alert_service.application.port.out.AlertRepository;
import com.example.alert_service.domain.model.Alert;
import com.example.alert_service.domain.model.ProcessedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class ProcessAlertService implements ProcessRawAlert {
    private final AlertRepository alertRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final TransformRawAlert transformRawAlert;

    public ProcessAlertService(
            AlertRepository alertRepository,
            OutboxEventRepository outboxEventRepository,
            TransformRawAlert transformRawAlert
    ) {
        this.alertRepository = alertRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.transformRawAlert = transformRawAlert;
    }

    @Transactional
    @Override
    public void process(String rawAlert) {
        List<Alert> alerts = transformRawAlert.transform(rawAlert);

        for (Alert alert : alerts) {
            if(!alertRepository.insertIfAbsent(alert)) continue;

            outboxEventRepository.append(new ProcessedEvent(
                    UUID.randomUUID(),
                    alert
            ));
        }
    }
}
