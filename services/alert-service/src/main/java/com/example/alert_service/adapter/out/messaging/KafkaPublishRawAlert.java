package com.example.alert_service.adapter.out.messaging;

import com.example.alert_service.application.port.out.PublishRawAlert;
import com.example.alert_service.domain.exception.AlertDeliveryUnconfirmedException;
import com.example.kafka_starter.KafkaMessagePublisher;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class KafkaPublishRawAlert implements PublishRawAlert {
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final String topic;

    public KafkaPublishRawAlert(
            KafkaMessagePublisher kafkaMessagePublisher,
            String topic
    ) {
        this.kafkaMessagePublisher = kafkaMessagePublisher;
        this.topic = topic;
    }

    @Override
    public void publish(String rawAlert) {
        kafkaMessagePublisher.publish(topic, null, rawAlert);
    }
}
