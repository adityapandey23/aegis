package com.example.alert_service.application.port.in;

import com.example.alert_service.domain.model.Alert;

public interface TransformRawAlert {
    Alert transform(String rawAlert);
}
