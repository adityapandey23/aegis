package com.example.alert_service.adapter.out.messaging;

import com.example.alert_service.application.port.out.PublishRawAlert;
import com.example.alert_service.domain.exception.AlertDeliveryUnconfirmedException;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class KafkaPublishRawAlert implements PublishRawAlert {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaPublishRawAlert(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(String rawAlert) {
        try {
            kafkaTemplate.send(topic, rawAlert).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new AlertDeliveryUnconfirmedException(exception);
        } catch (ExecutionException | TimeoutException | KafkaException exception) {
            throw new AlertDeliveryUnconfirmedException(exception);
        }
    }
}
