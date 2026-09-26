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

@RestController
@RequestMapping("/v1/webhook")
@Profile("ingestor")
public class AlertWebhookController {
    private final ReceiveRawAlert receiveRawAlert;

    public AlertWebhookController(ReceiveRawAlert receiveRawAlert) {
        this.receiveRawAlert = receiveRawAlert;
    }

    @PostMapping("/alert")
    public ResponseEntity<String> receive(@RequestBody JsonNode payload) {
        // TODO: Add a logger method, also add a global handler
        try {
            receiveRawAlert.receive(payload.toString());
            // Change this to return JSON instead of raw string
            return ResponseEntity
                    .accepted()
                    .body("Alert Accepted");
        } catch (AlertDeliveryUnconfirmedException exception) {
            // Same Here
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Delivery could not be confirmed");
        }
    }
}
