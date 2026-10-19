package com.example.alert_service.application.port.in;

import com.example.alert_service.domain.model.Alert;

import java.util.List;

public interface TransformRawAlert {
    List<Alert> transform(String rawAlert);
}
