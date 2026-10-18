package com.example.kafka_starter;

import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class KafkaMessagePublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperties properties;

    public KafkaMessagePublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            KafkaProperties properties
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    public void publish(String topic, String messageKey, String payload) {
        int attempt = 0;

        while (true) {
            try {
                var result = (messageKey != null)
                        ? kafkaTemplate.send(topic, messageKey, payload)
                        : kafkaTemplate.send(topic, payload);

                result.get(
                        properties.getPublishTimeoutMs(),
                        TimeUnit.MILLISECONDS
                );

                return;
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new KafkaMessagePublishException(
                        "Interrupted while publishing to " + topic,
                        exception
                );
            } catch (ExecutionException | TimeoutException exception) {
                if (attempt >= properties.getMaxRetries()) {
                    throw new KafkaMessagePublishException(
                            "Failed to publish to " + topic
                                    + " after " + (attempt + 1) + " attempts",
                            exception
                    );
                }

                attempt++;

                try {
                    TimeUnit.MILLISECONDS.sleep(
                            properties.getRetryIntervalMs()
                    );
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new KafkaMessagePublishException(
                            "Interrupted during retry delay for " + topic,
                            interruptedException
                    );
                }
            } catch (RuntimeException exception) {
                throw new KafkaMessagePublishException(
                        "Could not publish to " + topic,
                        exception
                );
            }
        }

    }
}
