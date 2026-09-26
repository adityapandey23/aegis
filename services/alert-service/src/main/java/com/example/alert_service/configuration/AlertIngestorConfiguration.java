package com.example.alert_service.configuration;

import com.example.alert_service.adapter.out.messaging.KafkaPublishRawAlert;
import com.example.alert_service.application.port.in.ReceiveRawAlert;
import com.example.alert_service.application.port.out.PublishRawAlert;
import com.example.alert_service.application.service.ReceiveAlertService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Profile("ingestor")
public class AlertIngestorConfiguration {
    @Bean
    PublishRawAlert publishRawAlert(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${alerts.kafka.raw-topic}") String topic) {
        return new KafkaPublishRawAlert(kafkaTemplate, topic);
    }

    @Bean
    ReceiveRawAlert receiveRawAlert(PublishRawAlert publisher) {
        return new ReceiveAlertService(publisher);
    }
}
