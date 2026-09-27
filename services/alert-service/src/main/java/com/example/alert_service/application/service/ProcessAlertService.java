package com.example.alert_service.application.service;

import com.example.alert_service.application.port.in.ProcessRawAlert;
import com.example.alert_service.application.port.in.TransformRawAlert;
import com.example.alert_service.domain.model.Alert;

public class ProcessAlertService implements ProcessRawAlert {
    private final TransformRawAlert transformRawAlert;

    public ProcessAlertService(TransformRawAlert transformRawAlert) {
        this.transformRawAlert = transformRawAlert;
    }

    @Override
    public void process(String rawAlert) {
        // 1. Transform
        Alert alert = transformRawAlert.transform(rawAlert);

        if(alert != null) {
            System.out.println("Hey I am not null lol");
            System.out.println(alert.toString());
        }

        // 2. Persistance in DB

        // 3. Sending event to Kafka
    }

}
