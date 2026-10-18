package com.example.kafka_starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@AutoConfiguration
@EnableConfigurationProperties(KafkaProperties.class)
@ConditionalOnBean(KafkaTemplate.class)
public class KafkaProducerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public KafkaMessagePublisher kafkaMessagePublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            KafkaProperties properties
    ) {
        return new KafkaMessagePublisher(kafkaTemplate, properties);
    }
}
