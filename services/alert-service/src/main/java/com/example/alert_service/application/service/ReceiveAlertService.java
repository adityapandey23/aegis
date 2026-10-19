package com.example.alert_service.application.service;

import com.example.alert_service.application.port.in.ReceiveRawAlert;
import com.example.alert_service.application.port.out.PublishRawAlert;

public class ReceiveAlertService implements ReceiveRawAlert {
    private final PublishRawAlert publishRawAlert;

    public ReceiveAlertService(PublishRawAlert publishRawAlert) {
        this.publishRawAlert = publishRawAlert;
    }

    @Override
    public void receive(String rawAlert) {
        publishRawAlert.publish(rawAlert);
    }
}
