package com.example.alert_service.domain.exception;

public class AlertDeliveryUnconfirmedException extends RuntimeException {
    public AlertDeliveryUnconfirmedException(Throwable cause) {
        super("Delivery could not be confirmed", cause);
    }
}
