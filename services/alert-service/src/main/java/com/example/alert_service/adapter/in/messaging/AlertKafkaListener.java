package com.example.alert_service.adapter.in.messaging;

import com.example.alert_service.adapter.in.messaging.mapper.AlertMapperFactory;
import com.example.alert_service.adapter.in.messaging.mapper.SourceAlertMapper;
import com.example.alert_service.application.port.in.ProcessRawAlert;
import com.example.alert_service.domain.model.Alert;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
@Profile("processor")
public class AlertKafkaListener {
    private final ProcessRawAlert processRawAlert;

    public AlertKafkaListener(ProcessRawAlert processRawAlert) {
        this.processRawAlert = processRawAlert;
    }

    @KafkaListener(
            topics = "${alerts.kafka.raw-topic}",
            groupId = "${alerts.kafka.processor-group}"
    )
    public void onEventReceived(String rawAlert) {
        processRawAlert.process(rawAlert);
    }
}
