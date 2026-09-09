package com.example.alert_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AlertServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertServiceApplication.class, args);
    }
}
