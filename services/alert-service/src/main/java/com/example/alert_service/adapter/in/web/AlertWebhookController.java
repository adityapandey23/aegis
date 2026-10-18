package com.example.alert_service.adapter.in.web;


import com.example.alert_service.application.port.in.ReceiveRawAlert;
import com.example.alert_service.application.service.ReceiveAlertService;
import com.example.alert_service.domain.exception.AlertDeliveryUnconfirmedException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

import java.util.Map;

@RestController
@RequestMapping("/v1/webhook")
@Profile("ingestor")
public class AlertWebhookController {
    private final ReceiveRawAlert receiveRawAlert;

    public AlertWebhookController(ReceiveRawAlert receiveRawAlert) {
        this.receiveRawAlert = receiveRawAlert;
    }

    @PostMapping("/alert")
    public ResponseEntity<Map<String, String>> receive(@RequestBody JsonNode payload) {
        try {
            receiveRawAlert.receive(payload.toString());
            return ResponseEntity
                    .accepted()
                    .body(Map.of("message", "alert accepted"));
        } catch (AlertDeliveryUnconfirmedException exception) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("error", "delivery could not be confirmed"));
        }
    }
}
