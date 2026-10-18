package com.example.alert_service.adapter.out.messaging;

import com.example.alert_service.application.port.out.PublishOutboxAlert;
import com.example.kafka_starter.KafkaMessagePublisher;

public class KafkaPublishOutboxAlert implements PublishOutboxAlert {

    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final String topic;

    public KafkaPublishOutboxAlert(KafkaMessagePublisher kafkaMessagePublisher, String topic) {
        this.kafkaMessagePublisher = kafkaMessagePublisher;
        this.topic = topic;
    }

    @Override
    public void publish() {
        // TODO: Implement
    }
}
