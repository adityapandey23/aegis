package com.example.kafka_starter;

public class KafkaMessagePublishException extends RuntimeException {
    public KafkaMessagePublishException(String message, Throwable cause) {
        super(message, cause);
    }
}
