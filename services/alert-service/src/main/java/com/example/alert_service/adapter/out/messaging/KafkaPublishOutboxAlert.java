package com.example.alert_service.adapter.out.messaging;

import com.example.alert_service.application.port.out.OutboxEventRepository;
import com.example.alert_service.application.port.out.PublishOutboxAlert;
import com.example.alert_service.domain.model.OutboxEvent;
import com.example.kafka_starter.KafkaMessagePublisher;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class KafkaPublishOutboxAlert implements PublishOutboxAlert {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final String topic;

    public KafkaPublishOutboxAlert(
            OutboxEventRepository outboxEventRepository,
            KafkaMessagePublisher kafkaMessagePublisher,
            String topic
    ) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaMessagePublisher = kafkaMessagePublisher;
        this.topic = topic;
    }

    @Override
    @Scheduled(fixedDelayString = "${alerts.outbox.poll-interval-ms}")
    public void publish() {
        List<OutboxEvent> pendingEvents = outboxEventRepository.findUnpublishedEvents(100);
        for (OutboxEvent event : pendingEvents) {
            kafkaMessagePublisher.publish(this.topic, event.messageKey(), event.toString());
            outboxEventRepository.markAsPublished(event);
        }
    }
}
