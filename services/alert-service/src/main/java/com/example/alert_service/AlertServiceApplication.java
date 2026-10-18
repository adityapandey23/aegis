package com.example.alert_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class AlertServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(AlertServiceApplication.class, args);
    }
}
