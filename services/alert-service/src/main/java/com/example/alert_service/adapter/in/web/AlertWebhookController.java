package com.example.alert_service.adapter.in.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/v1/webhook")
@Profile("ingestor")
public class AlertWebhookController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public AlertWebhookController(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${alerts.kafka.raw-topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @PostMapping("/alert")
    public ResponseEntity<String> receive(@RequestBody JsonNode payload) {
        // This needs to get out of here, and we should use an PORT
        try {
            kafkaTemplate.send(topic, payload.toString()).get(5, TimeUnit.SECONDS);
            return ResponseEntity
                    .accepted()
                    .body("Alert Accepted");
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Delivery could not be confirmed");
        }
        catch (ExecutionException | TimeoutException | KafkaException exception) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Delivery could not be confirmed");
        }
    }
}
