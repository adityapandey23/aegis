package com.example.alert_service.configuration;

import com.example.alert_service.adapter.in.messaging.mapper.*;
import com.example.alert_service.adapter.out.messaging.KafkaPublishOutboxAlert;
import com.example.alert_service.adapter.out.persistance.JdbcOutboxEventRepository;
import com.example.alert_service.adapter.out.persistance.JdbcAlertRepository;
import com.example.alert_service.application.port.in.ProcessRawAlert;
import com.example.alert_service.application.port.in.TransformRawAlert;
import com.example.alert_service.application.port.out.OutboxEventRepository;
import com.example.alert_service.application.port.out.AlertRepository;
import com.example.alert_service.application.port.out.PublishOutboxAlert;
import com.example.alert_service.application.service.ProcessAlertService;
import com.example.alert_service.application.service.TransformAlertService;
import com.example.kafka_starter.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Configuration
@Profile("processor")
@EnableScheduling
public class AlertProcessorConfiguration {
    //----------------Alert Mapper----------------
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
    AlertMapperFactory alertMapperFactory(
            List<SourceAlertMapper> mappers
    ) {
        return new AlertMapperFactory(mappers);
    }

    //----------------Repositories----------------
    @Bean
    AlertRepository alertRepository(
            JdbcTemplate jdbcTemplate
    ) {
        return new JdbcAlertRepository(jdbcTemplate);
    }

    @Bean
    OutboxEventRepository alertEventOutbox(
            ObjectMapper objectMapper,
            JdbcTemplate jdbcTemplate
    ) {
        return new JdbcOutboxEventRepository(
                objectMapper,
                jdbcTemplate
        );
    }

    //----------------Services----------------
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
            OutboxEventRepository outboxEventRepository,
            TransformRawAlert transformRawAlert
    ) {
        return new ProcessAlertService(
                alertRepository,
                outboxEventRepository,
                transformRawAlert
        );
    }

    @Bean
    PublishOutboxAlert publishOutboxAlert(
            OutboxEventRepository outboxEventRepository,
            KafkaMessagePublisher kafkaMessagePublisher,
            @Value("${alerts.kafka.outbox-event-topic}") String topic
    ) {
        return new KafkaPublishOutboxAlert(
                outboxEventRepository,
                kafkaMessagePublisher,
                topic
        );
    }
}
