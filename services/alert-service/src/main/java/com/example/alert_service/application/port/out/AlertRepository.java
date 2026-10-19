package com.example.alert_service.application.port.out;

import com.example.alert_service.domain.model.Alert;

public interface AlertRepository {
    boolean insertIfAbsent(Alert alert);
}
