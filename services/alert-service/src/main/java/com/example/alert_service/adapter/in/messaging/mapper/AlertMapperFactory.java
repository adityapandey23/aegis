package com.example.alert_service.adapter.in.messaging.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AlertMapperFactory {
    private final Map<String, SourceAlertMapper> mapperMap;

    public AlertMapperFactory(List<SourceAlertMapper> mappers) {
        this.mapperMap = mappers.stream().collect(
                Collectors.toUnmodifiableMap(
                        SourceAlertMapper::source,
                        Function.identity()
                )
        );
    }

    public SourceAlertMapper forSource(String source) {
        SourceAlertMapper mapper = mapperMap.get(source);

        if (mapper == null) {
            throw new IllegalArgumentException(
                    "Unsupported alert source: " + source
            );
        }

        return mapper;
    }
}
