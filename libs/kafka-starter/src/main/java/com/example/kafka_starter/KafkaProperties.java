package com.example.kafka_starter;


public class KafkaProperties {
    private long publishTimeoutMs;

    private long retryIntervalMs;

    private long maxRetries;

    public KafkaProperties() {
        this.publishTimeoutMs = 5000;
        this.retryIntervalMs = 1000;
        this.maxRetries = 3;
    }

    public KafkaProperties(
            long publishTimeoutMs,
            long retryIntervalMs,
            long maxRetries
    ) {
        this.publishTimeoutMs = publishTimeoutMs;
        this.retryIntervalMs = retryIntervalMs;
        this.maxRetries = maxRetries;
    }

    public long getPublishTimeoutMs() {
        return publishTimeoutMs;
    }

    public void setPublishTimeoutMs(long publishTimeoutMs) {
        if (publishTimeoutMs <= 0) {
            throw new IllegalArgumentException("publish-timeout must be positive");
        }
        this.publishTimeoutMs = publishTimeoutMs;
    }

    public long getRetryIntervalMs() {
        return retryIntervalMs;
    }

    public void setRetryIntervalMs(long retryIntervalMs) {
        if (retryIntervalMs < 0) {
            throw new IllegalArgumentException("retry-interval must be positive");
        }
        this.retryIntervalMs = retryIntervalMs;
    }

    public long getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(long maxRetries) {
        if (maxRetries < 0) {
            throw new IllegalArgumentException("max-retries must be positive");
        }
        this.maxRetries = maxRetries;
    }
}
