package com.example.alert_service.configuration;

import com.example.alert_service.adapter.in.messaging.mapper.*;
import com.example.alert_service.adapter.out.persistance.JdbcAlertOutboxEvent;
import com.example.alert_service.adapter.out.persistance.JdbcAlertRepository;
import com.example.alert_service.application.port.in.ProcessRawAlert;
import com.example.alert_service.application.port.in.TransformRawAlert;
import com.example.alert_service.application.port.out.AlertOutboxEvent;
import com.example.alert_service.application.port.out.AlertRepository;
import com.example.alert_service.application.service.ProcessAlertService;
import com.example.alert_service.application.service.TransformAlertService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Configuration
@Profile("processor")
public class AlertProcessorConfiguration {
    @Bean("datadog")
    SourceAlertMapper datadogAlertMapper() {
        return new DatadogAlertMapper();
    }

    @Bean("new-relic")
    SourceAlertMapper newRelicAlertMapper() {
        return new NewRelicAlertMapper();
    }

    @Bean("prometheus")
    SourceAlertMapper prometheusAlertMapper() {
        return new PrometheusAlertMapper();
    }

    @Bean
    AlertRepository alertRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcAlertRepository(jdbcTemplate);
    }

    AlertOutboxEvent alertEventOutbox(
            ObjectMapper objectMapper,
            JdbcTemplate jdbcTemplate
    ) {
        return new JdbcAlertOutboxEvent(objectMapper, jdbcTemplate);
    }

    @Bean
    AlertMapperFactory alertMapperFactory(
            List<SourceAlertMapper> mappers
    ) {
        return new AlertMapperFactory(mappers);
    }

    @Bean
    TransformRawAlert transformRawAlert(
            ObjectMapper objectMapper,
            AlertMapperFactory alertMapperFactory
    ) {
        return new TransformAlertService(
                objectMapper,
                alertMapperFactory
        );
    }

    @Bean
    ProcessRawAlert processRawAlert(
            AlertRepository alertRepository,
            AlertOutboxEvent alertOutboxEvent,
            TransformRawAlert transformRawAlert
    ) {
        return new ProcessAlertService(
                alertRepository,
                alertOutboxEvent,
                transformRawAlert
        );
    }
}
