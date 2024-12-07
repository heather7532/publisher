package org.alpenlogic.tools.examples.publisher.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Getter
@Setter
public class KafkaProperties {
    private String topic;
    private String schemaRegistryUrl;
    private Producer producer;

    @Getter
    @Setter
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
        private String bootstrapServers;
        private int retries;
        private String acks;
        private int retryBackoffMs;
    }
}